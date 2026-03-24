# Home Insurance MCP Server

Spring Boot + Spring AI MCP server for ICICI Lombard Home Insurance:

- `generateQuote`
- `createProposal`
- `createPaymentLink`
- `fetchPolicyDetails`
- `getPolicyResource`

## Prerequisites

- Java 21
- Maven 3.9+

## Run Locally

```bash
mvn clean spring-boot:run
```

Environment variables:

- `ILTC_BASE_URL` (default set)
- `ILTC_USERNAME` (default `ILTakeCare`)
- `ILTC_PASSWORD` (required)
- `PORT` (optional, default `8080`)

## Tool Flow

1. `generateQuote` (collect user property details)
2. `createProposal` (tenure + covers + customer details)
3. `createPaymentLink` (payer details; token handled internally)
4. Ask user "Have you completed your payment?"
5. `fetchPolicyDetails` (reads `transactionId` from session state)

Use a stable `sessionId` across tool calls.

## Railway Deployment

1. Push this project to GitHub.
2. Create new project in Railway from GitHub repo.
3. Railway detects `Dockerfile` and builds container.
4. Set env vars in Railway:
   - `ILTC_PASSWORD`
   - `ILTC_USERNAME` (optional override)
   - `ILTC_BASE_URL` (optional override)
5. Deploy and note public URL.

## Claude MCP Integration (custom HTTP MCP server)

Configure your MCP client to point to deployed URL and expose tool list from this server.

Required behavior in client/system prompt:

- Always pass same `sessionId` while conversation is active.
- Follow tool order.
- Ask payment-completion confirmation before calling `fetchPolicyDetails`.

## Resource Corpus

Authoritative source docs in repo root:

- `mcp-resources-section.md`
- `mcp-tools-section.md`
- `MCP_SERVER_IMPLEMENTATION_PLAN.md`
