# ICICI Lombard Home Insurance MCP Server
## Production-Ready Implementation Plan (Spring Boot + Spring AI)

## 1) Vision and End-State

Build a Java MCP server that MCP-compatible LLM clients (ChatGPT, Claude Desktop, etc.) can call to:

1. Answer policy and coverage questions using curated static/dynamic MCP resources.
2. Collect user inputs conversationally and generate a quote.
3. Create a proposal with selected tenure and optional covers.
4. Generate payment link.
5. Ask payment confirmation.
6. Fetch policy details using transaction ID and return policy summary.

This plan is designed to be simple in v1 but extensible for enterprise scale.

---

## 2) Technology Decisions

### 2.1 Java Version
- **Java 21 LTS** (recommended)
  - Strong long-term support.
  - Modern language/runtime improvements.
  - Best fit for current Spring Boot ecosystem.

### 2.2 Spring Boot Version
- **Spring Boot 3.3.x** (or latest stable 3.x compatible with your Spring AI release)
  - Native support for modern Jakarta APIs.
  - Mature observability and security stack.

### 2.3 Spring AI + MCP Dependencies
- Use Spring AI BOM and MCP modules:
  - `spring-ai-bom`
  - `spring-ai-starter-mcp-server-webmvc` (or WebFlux variant if fully reactive)
  - `spring-ai-starter-model-openai` (optional, only if server itself needs model calls)
  - `spring-boot-starter-web`
  - `spring-boot-starter-validation`
  - `spring-boot-starter-actuator`
  - `spring-boot-starter-security`
  - `spring-boot-starter-cache`
  - `spring-boot-starter-data-redis` (recommended for distributed token/session cache)

> Note: Keep MCP server concerns separate from model inference concerns. If only exposing tools/resources/prompts, model starter may be optional.

### 2.4 Communication Protocol
- **Primary: SSE (Server-Sent Events) over HTTP**
  - MCP commonly uses HTTP transport and SSE for server-to-client streaming events.
  - Easy compatibility with desktop MCP clients.
  - Simpler operationally vs WebSocket for request/response + event style tool invocations.

- **Alternative options**
  - WebSocket (when bi-directional low-latency full duplex needed).
  - Plain HTTP polling (fallback, not preferred for rich interaction).

### 2.5 Programming Model
- **Annotations-first (idiomatic Spring)**
  - Use Spring AI MCP annotations for tools/resources/prompts where possible.
  - Use `@ConfigurationProperties`, `@Service`, `@Component`, `@RestClient` style beans.
  - Keep manual wiring only for advanced interceptors, auth chains, custom transport, or registry customization.

### 2.6 Sync vs Async Design
- **Hybrid recommendation**
  - MCP tool methods can be presented as synchronous from caller perspective.
  - Internally use async/non-blocking where it gives value (token refresh, outbound API calls, retries).
  - For v1 keep implementation simple (sync service signatures), with async-ready interfaces.

**Why hybrid is best here**
- External insurer APIs are network I/O bound.
- Conversation flow is stepwise and stateful (quote -> proposal -> payment -> fetch policy).
- Sync tool contracts are easier for deterministic LLM behavior.
- Internal async can improve throughput later without changing MCP contract.

---

## 3) Target Architecture

## 3.1 High-Level Components

1. **MCP Adapter Layer**
   - Exposes tools/resources/prompts to MCP clients.
2. **Conversation Orchestrator**
   - Enforces flow stage and missing-input prompts.
3. **Domain Services**
   - QuoteService, ProposalService, PaymentService, PolicyService, TokenService.
4. **Session State Store**
   - Persists cross-tool context per user/session.
5. **External API Client Layer**
   - Typed clients for ILTC endpoints.
6. **Policy Knowledge Resource Layer**
   - Static + dynamic resources for clauses/limits/FAQs.
7. **Security Layer**
   - MCP client auth + outbound insurer token management.
8. **Observability Layer**
   - Logs, metrics, traces, audit IDs.

## 3.2 Core Flow State Machine

`START -> QUOTE_CREATED -> PROPOSAL_CREATED -> PAYMENT_LINK_CREATED -> PAYMENT_CONFIRMED -> POLICY_FETCHED`

- Tools should validate allowed transitions.
- If user jumps steps, assistant asks for required action/input.

---

## 4) Scalable Project Structure

```text
mcp-server-home-insurance/
  pom.xml
  README.md
  docker/
    Dockerfile
    docker-compose.yml
  src/
    main/
      java/com/icicilombard/mcp/
        McpServerApplication.java
        config/
          AppConfig.java
          HttpClientConfig.java
          CacheConfig.java
          SecurityConfig.java
          McpServerConfig.java
          JacksonConfig.java
        mcp/
          tools/
            QuoteTools.java
            ProposalTools.java
            PaymentTools.java
            PolicyTools.java
          resources/
            PolicyResourceProvider.java
            DynamicResourceProvider.java
          prompts/
            HomeInsurancePromptProvider.java
        orchestration/
          ConversationFlowService.java
          SessionStateService.java
          ValidationService.java
        domain/
          model/
            QuoteContext.java
            ProposalContext.java
            PaymentContext.java
            PolicyContext.java
            SessionState.java
          enums/
            FlowStage.java
            HomeType.java
            CoverageType.java
        integration/
          iltc/
            IltcApiClient.java
            dto/
              request/
                GenerateQuoteRequest.java
                CreateProposalRequest.java
                CreatePaymentLinkRequest.java
                FetchPolicyDetailsRequest.java
              response/
                GenerateQuoteResponse.java
                CreateProposalResponse.java
                CreateTokenResponse.java
                CreatePaymentLinkResponse.java
                FetchPolicyDetailsResponse.java
            mapper/
              IltcRequestMapper.java
              IltcResponseMapper.java
            auth/
              AuthTokenService.java
              TokenCacheEntry.java
        security/
          ApiKeyAuthFilter.java
          AuthPrincipal.java
        exception/
          GlobalExceptionHandler.java
          DomainException.java
          ExternalServiceException.java
        util/
          DateUtils.java
          MoneyUtils.java
      resources/
        application.yml
        application-local.yml
        application-prod.yml
        prompts/
          system-home-insurance.md
          tool-usage-rules.md
        mcp-static/
          policy/
            section-1-definitions.md
            section-2-insured-events.md
            ...
          reference/
            coverage-limits-quick-ref.json
            policy-faq.md
          metadata/
            document-version.json
            source-index.json
    test/
      java/com/icicilombard/mcp/
        mcp/tools/
          QuoteToolsTest.java
          ProposalToolsTest.java
          PaymentToolsTest.java
          PolicyToolsTest.java
        orchestration/
          ConversationFlowServiceTest.java
        integration/iltc/
          IltcApiClientTest.java
        contract/
          McpContractTest.java
```

### Separation of Concerns
- `mcp/tools`: only MCP-exposed tool contracts + minimal validation.
- `orchestration`: state, flow, missing fields, auto-resolution.
- `integration`: all insurer API wiring, DTOs, token logic.
- `mcp/resources`: static + dynamic context providers.
- `mcp/prompts`: prompt templates and policy behavior instructions.
- `security`: inbound client auth.
- `config`: transport, serialization, security, caching.

---

## 5) Step-by-Step Implementation Guide

## 5.1 Maven Setup (BOM + dependencies)

Use dependency management to avoid version drift:

```xml
<project>
  <modelVersion>4.0.0</modelVersion>
  <parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.3.4</version>
    <relativePath/>
  </parent>

  <properties>
    <java.version>21</java.version>
    <spring-ai.version>1.0.0</spring-ai.version>
  </properties>

  <dependencyManagement>
    <dependencies>
      <dependency>
        <groupId>org.springframework.ai</groupId>
        <artifactId>spring-ai-bom</artifactId>
        <version>${spring-ai.version}</version>
        <type>pom</type>
        <scope>import</scope>
      </dependency>
    </dependencies>
  </dependencyManagement>

  <dependencies>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-cache</artifactId>
    </dependency>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-data-redis</artifactId>
    </dependency>

    <dependency>
      <groupId>org.springframework.ai</groupId>
      <artifactId>spring-ai-starter-mcp-server-webmvc</artifactId>
    </dependency>

    <dependency>
      <groupId>org.projectlombok</groupId>
      <artifactId>lombok</artifactId>
      <optional>true</optional>
    </dependency>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-test</artifactId>
      <scope>test</scope>
    </dependency>
  </dependencies>

  <build>
    <plugins>
      <plugin>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-maven-plugin</artifactId>
      </plugin>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-compiler-plugin</artifactId>
        <configuration>
          <release>${java.version}</release>
        </configuration>
      </plugin>
    </plugins>
  </build>
</project>
```

## 5.2 Main Application Bootstrap

```java
@SpringBootApplication
@EnableConfigurationProperties({IltcApiProperties.class, SecurityProperties.class})
public class McpServerApplication {
  public static void main(String[] args) {
    SpringApplication.run(McpServerApplication.class, args);
  }
}
```

## 5.3 Configuration (`application.yml`)

```yaml
server:
  port: 8080

spring:
  application:
    name: icici-lombard-home-mcp-server
  cache:
    type: redis
  data:
    redis:
      host: localhost
      port: 6379

management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,prometheus

mcp:
  transport:
    type: sse
  resources:
    static-base-path: classpath:/mcp-static

app:
  auth:
    inbound-api-key-header: X-MCP-API-KEY
    inbound-api-key: ${MCP_INBOUND_API_KEY}
  iltc:
    base-url: https://api-iltc.insurancearticlez.com/dps-agorax/api/v1/pay
    create-token:
      username: ${ILTC_USERNAME:ILTakeCare}
      password: ${ILTC_PASSWORD}
    timeouts:
      connect-ms: 3000
      read-ms: 8000
    retry:
      max-attempts: 3
      backoff-ms: 500
```

## 5.4 MCP Tool Implementation Pattern

Implement 5 tools exactly as your design:

1. `generateQuote`
2. `createProposal`
3. `createAuthToken` (internal helper, not user-facing)
4. `createPaymentLink`
5. `fetchPolicyDetails`

### Example: `generateQuote` tool

```java
@Component
@RequiredArgsConstructor
public class QuoteTools {

  private final ConversationFlowService flowService;
  private final QuoteService quoteService;

  @Tool(name = "generateQuote", description = "Generate home insurance quote")
  public QuoteToolResponse generateQuote(@Valid QuoteToolRequest req, ToolContext ctx) {
    flowService.ensureStageAllowed(ctx.sessionId(), FlowStage.START);
    QuoteToolResponse response = quoteService.generateQuote(req, ctx.sessionId());
    flowService.moveTo(ctx.sessionId(), FlowStage.QUOTE_CREATED);
    return response;
  }
}
```

### Example request model validation

```java
public record QuoteToolRequest(
  @NotNull HomeType homeType,
  @Pattern(regexp = "^[6-9][0-9]{9}$") String mobileNumber,
  @Email String emailId,
  @Pattern(regexp = "^[1-9][0-9]{5}$") String pincode,
  @NotNull CoverageType coverageType,
  @Positive Integer carpetArea,
  @Positive Integer costOfConstruction
) {}
```

### Example: `createProposal` auto-resolution logic
- Input from user:
  - tenure
  - additionalCovers[]
  - customerDOB
  - customerAddress
- Auto-resolve from session:
  - `quoteId` from quote response
  - `selectedTenurePremium` from quote premium map by chosen tenure

### Example: `createPaymentLink` mapping rules
- User inputs: `name`, `email`, `mobileNumber`
- Auto from proposal response:
  - `TotalAmount`, `DealId`, `CustomerId`, `ProposalNumber`, `ProposalAmount`
- Hardcoded:
  - `PayerType=C`, `Product=HOME_1`, `JourneyType=POLICY`, `Core=PF`, `TemplateID=126`, `ProposalStatus=NCCN`, `is_mobile_user=false`, `IntermediaryCode=null`, `SendPaymentLink=""`
- Computed:
  - `ProposalStartDate = LocalDate.now().plusDays(1)`

### Example: `fetchPolicyDetails`
- No user body required in ideal flow.
- Pull `transactionId` from session created at payment-link step.
- If missing, ask user to regenerate payment link or provide transaction ID.

## 5.5 External API Client Implementation

- Use a typed client (RestClient/WebClient) with:
  - request/response DTOs
  - timeout config
  - retry for transient failures
  - exception translation
  - correlation ID propagation

### Token handling (`createAuthToken` internal)
- Cache token with expiry.
- Refresh proactively if expiry < N minutes.
- Synchronize refresh to avoid thundering herd.
- Never expose credentials/token in logs.

## 5.6 Resource Exposure

### A) Dynamic resources (programmatic)
Examples:
- Current quote summary.
- Session-selected covers.
- Last payment status.
- Policy issuance summary.

Use MCP resource provider components that build JSON/Markdown based on session state.

### B) Static resources (files consumed by LLM)
Load curated policy and reference files from `src/main/resources/mcp-static/**`.
Expose each as MCP resource with clear URI conventions:
- `policy://section/definitions`
- `policy://section/exclusions`
- `policy://reference/coverage-limits`
- `policy://reference/faq`

## 5.7 Prompt Templates

Use structured prompts:

1. **System policy prompt**
   - domain boundaries
   - no hallucination rules
   - tool invocation policy
2. **Flow control prompt**
   - collect only missing fields
   - never ask user for auto-resolved values
3. **Payment confirmation prompt**
   - ask exactly: "Have you completed your payment?"
   - on yes, call `fetchPolicyDetails`
4. **Compliance prompt**
   - never alter insurer response facts
   - always disclose if status is pending/failed

---

## 6) Static Resource Design (Detailed)

## 6.1 What to Store

Based on your provided resource strategy:
- Full policy split by semantic topic:
  - definitions
  - insured events
  - section-wise coverage
  - exclusions
  - claims process
  - conditions and endorsements
- Numeric quick-reference JSON:
  - all monetary limits
  - percentage caps
  - waiting periods / time windows
- Practical FAQ for ambiguous user queries.
- Metadata files:
  - source version
  - effective date
  - checksum
  - provenance

## 6.2 Where to Store

`src/main/resources/mcp-static/`
- `policy/` (topic files)
- `reference/` (quick refs and FAQ)
- `metadata/` (versioning and index)

## 6.3 How to Expose to MCP Clients

- Register all static files at startup and map to stable MCP URIs.
- Include short description and tags per resource (e.g., `coverage`, `exclusion`, `claim`, `limit`).
- Enable filtered retrieval by topic in resource provider so LLM fetches only relevant chunks.
- Keep resource payloads concise and chunked to avoid token waste.

---

## 7) Authentication and Security Layers

## 7.1 Inbound MCP Client Auth (v1)
- API key auth via header `X-MCP-API-KEY`.
- Validate in Spring Security filter.
- Allowlist trusted client IDs if needed.

## 7.2 Outbound Insurer Auth
- Internal token service calls `CreateToken`.
- Token cached with expiry.
- Auto-refresh before expiration.

## 7.3 Security Baselines
- TLS everywhere.
- Secret injection via env vars or vault.
- PII log masking for mobile/email/address.
- Rate limiting per client and per tool.
- Idempotency key support for payment-link generation.

---

## 8) Session State Strategy (Critical for Tool Chaining)

Persist per MCP conversation/session:

- From `generateQuote`:
  - `quoteId`
  - `premiums` map (`1/3/5`)
  - `sumInsured`
- From `createProposal`:
  - `proposalNumber`, `dealId`, `customerId`, `totalAmount`
- From `createPaymentLink`:
  - `paymentUrl`, `transactionId`
- From `fetchPolicyDetails`:
  - `policyNumber`, `paymentStatus`, `pgStatus`

Storage choices:
- v1 local cache (Caffeine) for quick POC.
- production Redis with TTL and encryption at rest.

---

## 9) Conversation Contract for LLM Behavior

## 9.1 Required User Interaction Sequence

1. User asks for home policy info / purchase.
2. Assistant answers with resources and asks quote inputs.
3. Call `generateQuote`.
4. Present 1/3/5 year premiums.
5. Ask tenure + add-on covers + DOB/address.
6. Call `createProposal`.
7. Ask payer details (name/email/mobile).
8. Call `createPaymentLink`.
9. Return URL and ask: **"Have you completed your payment?"**
10. If user says yes -> call `fetchPolicyDetails`.
11. Return policy number, payment status, transaction ID, amount, and "policy copy will be shared on email".

## 9.2 Non-Negotiable Guardrails
- Never ask user for `quoteId`, `proposalNumber`, `dealId`, `customerId`, `transactionId` in normal flow.
- Never re-ask fields already in session.
- If tool call fails, give user actionable retry guidance.

---

## 10) Testing and Execution

## 10.1 Build and Run

```bash
mvn clean verify
mvn spring-boot:run
```

## 10.2 Unit Tests
- Tool input validation tests.
- Session auto-resolution tests.
- Token caching expiry tests.
- Date generation (`today + 1`) tests.

## 10.3 Integration Tests
- Mock ILTC endpoints with WireMock.
- Validate payload mappings for all 5 tools.
- Validate flow transitions and refusal on invalid stage.

## 10.4 Contract Tests
- MCP tool schema/metadata correctness.
- Resource URI discoverability and content retrieval.

## 10.5 Example Validation Scenarios

1. **Happy path**
   - quote -> proposal -> payment link -> user confirms -> policy fetch.
2. **Token expired**
   - payment link call triggers token refresh and succeeds.
3. **Payment not done**
   - user says no; assistant does not fetch policy yet.
4. **Invalid pincode/mobile**
   - validation error returned with user-friendly message.

---

## 11) Example Tool Payload Mapping Summary

## 11.1 `generateQuote`
- User provides: homeType, mobile, email, pincode, coverageType, carpetArea, costOfConstruction.
- Save: quoteId + premium matrix + sumInsured.

## 11.2 `createProposal`
- User provides: tenure, additionalCovers[], DOB, address.
- Auto: quoteId + selectedTenurePremium.
- Save: proposalNumber/dealId/customerId/totalAmount.

## 11.3 `createPaymentLink`
- User provides: Name, Email, Mobile.
- Auto: proposal-derived fields and total amount.
- Hardcoded constants exactly as specified.
- Save: payment URL + transactionID.

## 11.4 `fetchPolicyDetails`
- Input: none from user in normal flow.
- Auto: transactionID from session.
- Return: policy_number, payment_status, total_amount, transaction_id, payment_id.

---

## 12) MCP Client Integration

## 12.1 Claude Desktop / MCP-Compatible Client
- Register server endpoint in client MCP config.
- Set auth header/API key as required.
- Verify:
  - tools listed (`generateQuote`, `createProposal`, `createPaymentLink`, `fetchPolicyDetails`)
  - resources discoverable (`policy://...`, `reference://...`)
  - prompts visible if client supports prompt catalogs.

## 12.2 Recommended MCP Metadata
- Tool descriptions should clearly declare:
  - required user fields
  - auto-resolved fields
  - side effects (proposal creation, payment initiation)
- Resource descriptions should include scope and update date.

---

## 13) Production Enhancements Roadmap

## 13.1 Security Upgrades
- Move from API key to OAuth2 client credentials or JWT validation.
- mTLS between MCP server and insurer APIs (if supported).
- Vault-backed secret rotation.

## 13.2 Observability
- Structured JSON logs with correlation IDs.
- Metrics:
  - tool success/failure rates
  - latency percentiles
  - token refresh counts
  - payment success conversion
- Distributed tracing (OpenTelemetry).

## 13.3 Persistence
- Store conversation audit + transaction lifecycle in PostgreSQL.
- Encrypt sensitive fields.
- Build reconciliation jobs for payment vs policy issuance mismatches.

## 13.4 Streaming Support
- SSE event updates for long-running operations:
  - proposal creation in progress
  - payment status polling
  - policy issuance events (if webhook available)

## 13.5 Deployment
- Dockerize service.
- Deploy to Kubernetes with:
  - HPA autoscaling
  - readiness/liveness probes
  - config maps + secrets
- Add WAF/API gateway in front.

---

## 14) Suggested v1 Milestones

## Milestone 1 (Core MCP + Quote)
- Boot app, tool registration, resource registration, `generateQuote`.

## Milestone 2 (Proposal + Session Chaining)
- `createProposal`, session state, premium auto-resolution.

## Milestone 3 (Token + Payment Link)
- token caching, `createPaymentLink`, constants mapping.

## Milestone 4 (Payment Confirmation + Policy Fetch)
- conversational confirmation pattern, `fetchPolicyDetails`.

## Milestone 5 (Hardening)
- security, retries, observability, tests, docker, documentation.

---

## 15) Operational Checklist (Go-Live)

- [ ] Secrets externalized and rotated.
- [ ] PII masking verified in logs.
- [ ] Retry + timeout policies tuned.
- [ ] Circuit breaker configured for insurer downtime.
- [ ] API/schema contract tests green.
- [ ] MCP client integration tested end-to-end.
- [ ] Monitoring dashboards and alerts configured.
- [ ] Runbook for payment/policy mismatch incidents documented.

---

## 16) Minimal Pseudocode for End-to-End Flow

```java
if (userWantsToBuyPolicy) {
  ensureQuoteInputs();
  quote = generateQuote();
  askTenureAndCovers();
  proposal = createProposal(quote.quoteId, selectedTenurePremium, ...);
  askPayerDetails();
  payment = createPaymentLink(proposal, payerDetails);
  respondWith(payment.url);
  ask("Have you completed your payment?");
  if (userSaysYes) {
    policy = fetchPolicyDetails(payment.transactionId);
    respondPolicySummary(policy);
  }
}
```

---

## 17) Final Recommendation

Start with annotation-driven Spring Boot + Spring AI MCP implementation over SSE, keep tool contracts simple and deterministic, and enforce strict session-driven auto-resolution so users never repeat data. Use your static policy resource pack + quick-ref JSON + FAQ as first-class MCP resources to dramatically improve answer quality while keeping token usage efficient.

