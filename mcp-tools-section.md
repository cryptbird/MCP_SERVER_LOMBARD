# MCP Server — Tools Section (ICICI Lombard Complete Home Protect Policy)

## Overview

The Tools section defines **executable actions** that the MCP server exposes to LLM clients. These tools enable the LLM to orchestrate the complete home insurance purchase journey — from quote generation through payment — via API calls to the ICICI Lombard backend.

---

## Tool Orchestration Flow

```
┌──────────────┐    ┌──────────────────┐    ┌──────────────┐    ┌─────────────────────┐    ┌─────────────────────┐
│ 1. Generate  │───▶│ 2. Create        │───▶│ 3. Create    │───▶│ 4. Create Payment   │───▶│ 5. Fetch Policy     │
│    Quote     │    │    Proposal      │    │    Token     │    │    Link             │    │    Details          │
│              │    │                  │    │ (internal)   │    │                     │    │                     │
│ User inputs  │    │ Uses quoteId     │    │ No user      │    │ Uses token +        │    │ Uses transactionId  │
│ home details │    │ + user inputs    │    │ input needed │    │ proposal details    │    │ from payment link    │
└──────────────┘    └──────────────────┘    └──────────────┘    └─────────────────────┘    └─────────────────────┘
     │                      │                      │                       │                        │
     ▼                      ▼                      ▼                       ▼                        ▼
  Returns:              Returns:               Returns:                Returns:                 Returns:
  - quoteId             - proposalNumber       - accessToken           - paymentUrl             - policyNumber
  - sumInsured          - dealId               - expiryTime            - transactionId          - paymentStatus
  - premiums            - customerId                                                            - totalAmount
    (1yr/3yr/5yr)       - totalAmount
```

### Important: State Management

The MCP server must maintain **session state** across tool calls within a conversation. Each tool call produces outputs that subsequent tools consume. The server should store:

| Key | Produced By | Consumed By |
|-----|-------------|-------------|
| `quoteId` | Tool 1 (Generate Quote) | Tool 2 (Create Proposal) |
| `premiums` (1yr/3yr/5yr) | Tool 1 (Generate Quote) | Tool 2 (Create Proposal) — to resolve `selectedTenurePremium` |
| `proposalNumber` | Tool 2 (Create Proposal) | Tool 4 (Create Payment Link) |
| `dealId` | Tool 2 (Create Proposal) | Tool 4 (Create Payment Link) |
| `customerId` | Tool 2 (Create Proposal) | Tool 4 (Create Payment Link) |
| `totalAmount` | Tool 2 (Create Proposal) | Tool 4 (Create Payment Link) |
| `accessToken` | Tool 3 (Create Token) | Tool 4 (Create Payment Link), Tool 5 (Fetch Policy Details) |
| `transactionId` | Tool 4 (Create Payment Link) | Tool 5 (Fetch Policy Details) |

---

## Tool #1: `generateQuote`

**Name:** `generateQuote`
**Description:** Generates a home insurance quote based on user's property details. This is the first step in the insurance purchase journey. Returns a quote ID, sum insured amount, and premium options for 1-year, 3-year, and 5-year tenures. The LLM should present all three premium options to the user after receiving the response.

### Input Parameters

| Parameter | Type | Required | Description | Validation / Notes |
|-----------|------|----------|-------------|-------------------|
| `homeType` | `string` | Yes | Whether the user is a home owner or a tenant | Enum: `"home_owner"`, `"tenant"`. Ask user: "Do you own the home or are you a tenant?" |
| `mobileNumber` | `string` | Yes | User's 10-digit Indian mobile number | Must be exactly 10 digits. Example: `"9876543210"` |
| `emailId` | `string` | Yes | User's email address | Must be valid email format. Example: `"user@example.com"` |
| `pincode` | `string` | Yes | 6-digit PIN code of the property location | Must be exactly 6 digits. Example: `"110001"` |
| `coverageType` | `string` | Yes | Whether to cover the structure, contents, or both | Enum: `"structure"`, `"content"`. Ask user: "Do you want to cover the building structure, the home contents, or both?" If both, two separate quotes may be needed. |
| `carpetArea` | `number` | Yes | Carpet area of the home in square feet | Positive integer. Ask user: "What is the carpet area of your home in square feet?" |
| `costOfConstruction` | `number` | Yes | Cost of construction per square foot in INR | Positive number. Ask user: "What is the approximate cost of construction per square foot in INR?" |

### API Call

```
POST https://api-iltc.insurancearticlez.com/dps-agorax/api/v1/pay/GenerateQuote
Content-Type: application/json

{
  "homeType": "{homeType}",
  "mobileNumber": "{mobileNumber}",
  "emailId": "{emailId}",
  "pincode": "{pincode}",
  "coverageType": "{coverageType}",
  "carpetArea": {carpetArea},
  "costOfConstruction": {costOfConstruction}
}
```

### Response Schema

```json
{
  "quoteId": "string",         // Unique quote identifier — STORE for Tool 2
  "sumInsured": "number",      // Total sum insured amount in INR
  "premiums": {
    "oneYear": "number",       // Premium for 1-year tenure — STORE for Tool 2
    "threeYears": "number",    // Premium for 3-year tenure — STORE for Tool 2
    "fiveYears": "number"      // Premium for 5-year tenure — STORE for Tool 2
  }
}
```

### LLM Behavior After Response

After receiving the quote, the LLM should:
1. Display the quote details to the user in a clear format:
   - Quote ID
   - Sum Insured amount
   - Premium options: 1-year, 3-year, and 5-year with their respective amounts
2. Ask the user which tenure they'd like to choose (1, 3, or 5 years)
3. Ask if they want to proceed to create a proposal

### Error Handling

| Scenario | LLM Action |
|----------|------------|
| Invalid pincode | Ask user to verify and re-enter the pincode |
| Invalid mobile number | Ask user to provide a valid 10-digit mobile number |
| Invalid email | Ask user to provide a valid email address |
| API timeout / server error | Inform user of temporary issue, ask to retry |

---

## Tool #2: `createProposal`

**Name:** `createProposal`
**Description:** Creates a formal insurance proposal using the quote generated in the previous step. Collects additional details from the user including tenure preference, additional covers, date of birth, and address. Returns a proposal number, deal ID, customer ID, and total premium amount. This must be called AFTER `generateQuote`.

### Input Parameters

| Parameter | Type | Required | Description | Validation / Notes |
|-----------|------|----------|-------------|-------------------|
| `quoteId` | `string` | Yes | Quote ID from `generateQuote` response | **Auto-populated from session state** — do NOT ask user for this |
| `tenure` | `number` | Yes | Policy tenure in years | Enum: `1`, `3`, `5`. User should have selected this after seeing quote premiums. |
| `selectedTenurePremium` | `number` | Yes | Premium amount corresponding to selected tenure | **Auto-resolved from session state**: if tenure=1 use `premiums.oneYear`, if tenure=3 use `premiums.threeYears`, if tenure=5 use `premiums.fiveYears`. Do NOT ask user for this value. |
| `additionalCovers` | `array` | No | List of additional/optional covers the user wants | Each item has `coverName` (string) and `coverAmount` (number in INR). Ask user: "Would you like to add any additional covers? For example: Fire Protection, Flood Cover, Earthquake Cover, Theft Cover, etc." If user says no, send empty array `[]`. |
| `customerDOB` | `string` | Yes | Customer's date of birth | Format: `YYYY-MM-DD`. Ask user: "What is your date of birth?" |
| `customerAddress` | `string` | Yes | Customer's residential address | Free text. Ask user: "What is your current residential address?" Example: `"Delhi, India"` |

### API Call

```
POST https://api-iltc.insurancearticlez.com/dps-agorax/api/v1/pay/CreateProposal
Content-Type: application/json

{
  "quoteId": "{quoteId}",
  "tenure": {tenure},
  "selectedTenurePremium": {selectedTenurePremium},
  "additionalCovers": [
    {
      "coverName": "{coverName_1}",
      "coverAmount": {coverAmount_1}
    },
    {
      "coverName": "{coverName_2}",
      "coverAmount": {coverAmount_2}
    }
  ],
  "customerDOB": "{customerDOB}",
  "customerAddress": "{customerAddress}"
}
```

### Response Schema

```json
{
  "proposalNumber": "string",   // Unique proposal number — STORE for Tool 4
  "dealId": "string",           // Deal identifier — STORE for Tool 4
  "customerId": "string",       // Customer identifier — STORE for Tool 4
  "totalAmount": "string"       // Total payable amount in INR (as string) — STORE for Tool 4
}
```

### LLM Behavior After Response

After receiving the proposal, the LLM should:
1. Display proposal details to the user:
   - Proposal Number
   - Total Amount payable (including any additional covers)
2. Confirm with the user: "Your proposal has been created. The total premium amount is ₹{totalAmount}. Would you like to proceed to payment?"
3. If yes, proceed to Tool 3 (Create Token) internally, then Tool 4 (Create Payment Link)

### Error Handling

| Scenario | LLM Action |
|----------|------------|
| Invalid quoteId / expired quote | Inform user the quote may have expired; offer to generate a new quote |
| Invalid DOB format | Ask user to re-enter DOB in DD/MM/YYYY or similar, then convert to YYYY-MM-DD |
| API error | Inform user of temporary issue, ask to retry |

---

## Tool #3: `createAuthToken`

**Name:** `createAuthToken`
**Description:** Generates an authentication token required for the payment link creation and policy details APIs. This is an INTERNAL tool — it requires NO user input and should be called automatically by the MCP server before Tool 4 or Tool 5 when a valid token is not available or has expired. The LLM should NOT mention this tool or token creation to the user.

### Input Parameters

**None** — All credentials are hardcoded in the MCP server configuration.

### Server-Side Configuration (NOT exposed to LLM)

```yaml
# application.yml
icici-lombard:
  auth:
    username: "ILTakeCare"
    password: "GlfOsg+j2+MOGGez4tVT4Q=="
    token-url: "https://api-iltc.insurancearticlez.com/dps-agorax/api/v1/pay/CreateToken"
```

### API Call

```
POST https://api-iltc.insurancearticlez.com/dps-agorax/api/v1/pay/CreateToken
Username: ILTakeCare
Password: GlfOsg+j2+MOGGez4tVT4Q==
```

### Response Schema

```json
{
  "statusCode": 1,
  "statusMessage": "Success",
  "message": null,
  "traceId": null,
  "data": {
    "accessToken": "string",    // JWT Bearer token — STORE for Tool 4 and Tool 5
    "expiryTime": "string"      // Token expiry timestamp — STORE to check before reuse
  }
}
```

### Token Caching Strategy

The MCP server should:
1. Cache the token in session/memory
2. Before each call to Tool 4 or Tool 5, check if cached token exists and has not expired
3. If token is missing or expired (compare `expiryTime` with current time), call this tool to refresh
4. Never expose the token value to the LLM or user

### LLM Behavior

- This tool is **transparent** to the LLM conversation
- The LLM should NOT say "generating token" or "authenticating" to the user
- From the user's perspective, the flow goes directly from proposal confirmation to payment link

### Error Handling

| Scenario | LLM Action |
|----------|------------|
| Authentication failure (statusCode != 1) | Log error server-side; inform user of temporary service issue |
| Network error | Retry up to 3 times; if still failing, inform user |

---

## Tool #4: `createPaymentLink`

**Name:** `createPaymentLink`
**Description:** Generates a payment link for the user to complete the insurance premium payment. This tool uses proposal details from Tool 2 and an auth token from Tool 3. It collects the user's name, email, and mobile number for payment processing. Returns a payment URL that should be shared with the user, and a transaction ID for tracking. This must be called AFTER `createProposal` and `createAuthToken`.

### Input Parameters

| Parameter | Type | Required | Source | Description |
|-----------|------|----------|--------|-------------|
| `name` | `string` | Yes | **User input** | Full name of the person making payment. Ask: "What is your full name for the payment?" |
| `email` | `string` | Yes | **User input** | Email address for payment receipt. Ask: "What email should we send the payment receipt to?" |
| `mobileNumber` | `string` | Yes | **User input** | Mobile number for payment OTP/confirmation. Ask: "What mobile number should be used for payment?" |
| `totalAmount` | `string` | Yes | **Auto from Tool 2 response** | `totalAmount` from `createProposal` response. Do NOT ask user. |
| `dealId` | `string` | Yes | **Auto from Tool 2 response** | `dealId` from `createProposal` response. Do NOT ask user. |
| `customerId` | `string` | Yes | **Auto from Tool 2 response** | `customerId` from `createProposal` response. Do NOT ask user. |
| `proposalNumber` | `string` | Yes | **Auto from Tool 2 response** | `proposalNumber` from `createProposal` response. Do NOT ask user. |
| `accessToken` | `string` | Yes | **Auto from Tool 3 response** | Bearer token from `createAuthToken`. Do NOT ask user. |

### Hardcoded Parameters (Server-Side)

| Parameter | Value | Notes |
|-----------|-------|-------|
| `PayerType` | `"C"` | Always "C" (Customer) |
| `Product` | `"HOME_1"` | Always "HOME_1" |
| `JourneyType` | `"POLICY"` | Always "POLICY" |
| `Core` | `"PF"` | Always "PF" |
| `SendPaymentLink` | `""` | Always empty string |
| `TemplateID` | `126` | Always 126 |
| `ProposalStartDate` | `{tomorrow}` | **Computed** as current date + 1 day in `YYYY-MM-DD` format |
| `is_mobile_user` | `false` | Always false |
| `IntermediaryCode` | `null` | Always null |
| `ProposalStatus` | `"NCCN"` | Always "NCCN" |

### API Call

```
POST https://api-iltc.insurancearticlez.com/dps-agorax/api/v1/pay/AddPaymentRequest
Content-Type: application/json
Authorization: Bearer {accessToken}

{
  "Name": "{name}",
  "Email": "{email}",
  "MobileNumber": "{mobileNumber}",
  "TotalAmount": "{totalAmount}",
  "PayerType": "C",
  "Product": "HOME_1",
  "JourneyType": "POLICY",
  "Core": "PF",
  "SendPaymentLink": "",
  "TemplateID": 126,
  "ProposalStartDate": "{tomorrow_YYYY-MM-DD}",
  "is_mobile_user": false,
  "ProposalList": [
    {
      "DealId": "{dealId}",
      "CustomerId": "{customerId}",
      "IntermediaryCode": null,
      "ProposalNumber": "{proposalNumber}",
      "ProposalAmount": "{totalAmount}",
      "ProposalStatus": "NCCN"
    }
  ]
}
```

### Response Schema

```json
{
  "statusCode": 1,
  "statusMessage": "Success",
  "message": null,
  "traceId": "",
  "data": {
    "url": "string",              // Payment URL — SHARE WITH USER
    "transactionID": "string"     // Transaction ID — STORE for Tool 5
  }
}
```

### LLM Behavior After Response

After receiving the payment link, the LLM should:
1. Present the payment link clearly to the user:
   - "Here is your payment link: {url}"
   - "Please click the link to complete your payment of ₹{totalAmount}."
   - "Your Transaction ID for reference is: {transactionID}"
2. Add helpful notes:
   - "You can pay using Net Banking, UPI, Debit Card, or Credit Card."
   - "Please complete the payment at your earliest convenience."
3. After sharing the link, ask:
   - "Once you've completed the payment, let me know and I can fetch your policy details for you."
4. Wait for user confirmation before calling Tool 5.

### Error Handling

| Scenario | LLM Action |
|----------|------------|
| Token expired (401/403) | Automatically refresh token via Tool 3 and retry |
| Invalid proposal data | Inform user there's an issue with the proposal; may need to regenerate |
| API error | Inform user of temporary issue; provide the Transaction ID if available for manual follow-up |

---

## Tool #5: `fetchPolicyDetails`

**Name:** `fetchPolicyDetails`
**Description:** Fetches the policy issuance details and payment status after the user has completed payment via the payment link. Uses the transaction ID from Tool 4. Returns the policy number, payment status, and confirmation details. This should ONLY be called after the user confirms they have completed the payment.

### Input Parameters

| Parameter | Type | Required | Source | Description |
|-----------|------|----------|--------|-------------|
| `transactionId` | `string` | Yes | **Auto from Tool 4 response** | `transactionID` from `createPaymentLink` response. Do NOT ask user. |
| `accessToken` | `string` | Yes | **Auto from Tool 3 response** | Bearer token (refresh if expired). Do NOT ask user. |

### API Call

```
POST https://api-iltc.insurancearticlez.com/dps-agorax/api/v1/pay/ChannelApplicationOrderDetails
Content-Type: application/json
Authorization: Bearer {accessToken}

{
  "transaction_id": "{transactionId}"
}
```

### Response Schema

```json
{
  "statusCode": 1,
  "statusMessage": "Success",
  "message": "Data Fetched Successfully",
  "traceId": "string",
  "data": {
    "pg_status": "string",                    // Payment gateway status (e.g., "CAPTURED")
    "payment_status": "string",               // Payment status (e.g., "SUCCESS")
    "payment_received_on": "string",          // Payment timestamp (ISO 8601)
    "transaction_id": "string",               // Transaction ID
    "trace_id": "string",                     // Trace ID
    "application_name": "string",             // Application name
    "dps_order_id": "string",                 // DPS order ID
    "auth_code": "string",                    // Authorization code
    "total_amount": "string",                 // Total amount paid
    "payment_mode": "string",                 // Payment mode (NB/UPI/CC/DC)
    "payment_id": "string",                   // Payment reference ID
    "channel_application_details": "string",  // JSON string with channel details
    "channelApplicationProposalDetailsResp": [
      {
        "proposal_number": "string",          // Proposal number
        "policy_number": "string",            // POLICY NUMBER — the main output
        "breakin_id": null,
        "breakin_status": null,
        "breakin_remarks": null,
        "refund_amount": null
      }
    ]
  }
}
```

### LLM Behavior After Response

#### If payment_status = "SUCCESS":
Present the policy details to the user in a congratulatory format:

```
🎉 Congratulations! Your ICICI Lombard Complete Home Protect Policy has been issued successfully!

Here are your policy details:
• Policy Number: {policy_number}
• Transaction ID: {transaction_id}
• Payment ID: {payment_id}
• Amount Paid: ₹{total_amount}
• Payment Mode: {payment_mode}
• Payment Date: {payment_received_on}

You will receive your policy copy on your registered email address shortly.

For any queries, you can contact ICICI Lombard:
• Toll-free: 1800 2666
• Email: customersupport@icicilombard.com
• Website: www.icicilombard.com
```

#### If payment_status is NOT "SUCCESS" (e.g., "PENDING", "FAILED"):
```
Your payment status is currently: {payment_status}

If you've recently made the payment, it may take a few minutes to reflect. 
Would you like me to check again in a moment?

If the payment failed, you can try again using the payment link shared earlier, 
or I can generate a new payment link for you.
```

### Error Handling

| Scenario | LLM Action |
|----------|------------|
| Token expired | Refresh token via Tool 3 and retry |
| Payment not found | Ask user to confirm they completed payment; offer to check again later |
| Payment pending | Inform user payment is processing; offer to check again in a few minutes |
| API error | Provide transaction ID for manual follow-up with customer support |

---

## Tool Directory Layout (Spring Boot Project)

```
src/main/java/com/icicilombard/mcp/
├── tool/
│   ├── QuoteGenerationTool.java          // Tool 1: generateQuote
│   ├── ProposalCreationTool.java         // Tool 2: createProposal
│   ├── PaymentLinkTool.java              // Tool 4: createPaymentLink
│   └── PolicyDetailsTool.java            // Tool 5: fetchPolicyDetails
├── service/
│   ├── AuthTokenService.java             // Tool 3: createAuthToken (internal service)
│   ├── ApiClientService.java             // HTTP client for all API calls
│   └── SessionStateService.java          // Stores quoteId, proposalNumber, etc.
├── model/
│   ├── request/
│   │   ├── QuoteRequest.java
│   │   ├── ProposalRequest.java
│   │   ├── PaymentLinkRequest.java
│   │   └── PolicyDetailsRequest.java
│   ├── response/
│   │   ├── QuoteResponse.java
│   │   ├── ProposalResponse.java
│   │   ├── AuthTokenResponse.java
│   │   ├── PaymentLinkResponse.java
│   │   └── PolicyDetailsResponse.java
│   └── AdditionalCover.java
├── config/
│   ├── ApiConfig.java                    // Base URLs, timeouts
│   └── AuthConfig.java                   // Username, password (from vault/env)
└── exception/
    ├── QuoteGenerationException.java
    ├── ProposalCreationException.java
    └── PaymentException.java
```

---

## Spring Boot Tool Implementation Examples

### Tool 1: GenerateQuote

```java
@Service
public class QuoteGenerationTool {

    private final ApiClientService apiClient;
    private final SessionStateService sessionState;

    @Tool(name = "generateQuote",
          description = "Generates a home insurance quote based on user's property details. "
              + "Returns quote ID, sum insured, and premium options for 1-year, 3-year, "
              + "and 5-year tenures. This is the FIRST step in the insurance purchase journey.")
    public QuoteResponse generateQuote(
        @ToolParam(description = "Home ownership type. Must be either 'home_owner' or 'tenant'.")
        String homeType,

        @ToolParam(description = "User's 10-digit Indian mobile number. Example: '9876543210'")
        String mobileNumber,

        @ToolParam(description = "User's email address. Example: 'user@example.com'")
        String emailId,

        @ToolParam(description = "6-digit PIN code of the property location. Example: '110001'")
        String pincode,

        @ToolParam(description = "Coverage type: 'structure' for building cover, 'content' for contents cover.")
        String coverageType,

        @ToolParam(description = "Carpet area of the home in square feet. Must be a positive number.")
        int carpetArea,

        @ToolParam(description = "Cost of construction per square foot in INR. Must be a positive number.")
        int costOfConstruction
    ) {
        QuoteRequest request = new QuoteRequest(
            homeType, mobileNumber, emailId, pincode,
            coverageType, carpetArea, costOfConstruction
        );

        QuoteResponse response = apiClient.post(
            "/api/v1/pay/GenerateQuote", request, QuoteResponse.class
        );

        // Store in session for subsequent tools
        sessionState.setQuoteId(response.getQuoteId());
        sessionState.setPremiums(response.getPremiums());

        return response;
    }
}
```

### Tool 2: CreateProposal

```java
@Service
public class ProposalCreationTool {

    private final ApiClientService apiClient;
    private final SessionStateService sessionState;

    @Tool(name = "createProposal",
          description = "Creates a formal insurance proposal using the quote from generateQuote. "
              + "Requires tenure selection, optional additional covers, customer DOB, and address. "
              + "Returns proposal number, deal ID, customer ID, and total amount. "
              + "Must be called AFTER generateQuote.")
    public ProposalResponse createProposal(
        @ToolParam(description = "Policy tenure in years. Must be 1, 3, or 5.")
        int tenure,

        @ToolParam(description = "List of additional covers. Each with 'coverName' (string) and "
            + "'coverAmount' (number in INR). Example: [{\"coverName\":\"Fire Protection\","
            + "\"coverAmount\":200000}]. Send empty array [] if no additional covers desired.")
        List<AdditionalCover> additionalCovers,

        @ToolParam(description = "Customer's date of birth in YYYY-MM-DD format. Example: '1990-05-15'")
        String customerDOB,

        @ToolParam(description = "Customer's full residential address. Example: 'Andheri West, Mumbai, India'")
        String customerAddress
    ) {
        // Auto-resolve from session state
        String quoteId = sessionState.getQuoteId();
        double selectedPremium = sessionState.resolvePremiumForTenure(tenure);

        ProposalRequest request = new ProposalRequest(
            quoteId, tenure, selectedPremium,
            additionalCovers, customerDOB, customerAddress
        );

        ProposalResponse response = apiClient.post(
            "/api/v1/pay/CreateProposal", request, ProposalResponse.class
        );

        // Store in session for subsequent tools
        sessionState.setProposalNumber(response.getProposalNumber());
        sessionState.setDealId(response.getDealId());
        sessionState.setCustomerId(response.getCustomerId());
        sessionState.setTotalAmount(response.getTotalAmount());

        return response;
    }
}
```

### Tool 3: AuthTokenService (Internal — NOT a Tool)

```java
@Service
public class AuthTokenService {

    @Value("${icici-lombard.auth.username}")
    private String username;

    @Value("${icici-lombard.auth.password}")
    private String password;

    private String cachedToken;
    private Instant tokenExpiry;

    /**
     * Returns a valid Bearer token. Automatically refreshes if expired.
     * This is NOT exposed as an MCP tool — it's an internal service.
     */
    public synchronized String getValidToken() {
        if (cachedToken != null && Instant.now().isBefore(tokenExpiry)) {
            return cachedToken;
        }
        return refreshToken();
    }

    private String refreshToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Username", username);
        headers.set("Password", password);

        AuthTokenResponse response = restTemplate.postForObject(
            baseUrl + "/api/v1/pay/CreateToken",
            new HttpEntity<>(null, headers),
            AuthTokenResponse.class
        );

        cachedToken = response.getData().getAccessToken();
        tokenExpiry = parseExpiry(response.getData().getExpiryTime())
                          .minus(5, ChronoUnit.MINUTES); // 5-min buffer
        return cachedToken;
    }
}
```

### Tool 4: CreatePaymentLink

```java
@Service
public class PaymentLinkTool {

    private final ApiClientService apiClient;
    private final AuthTokenService authService;
    private final SessionStateService sessionState;

    @Tool(name = "createPaymentLink",
          description = "Generates a payment link for the user to pay the insurance premium. "
              + "Uses proposal details from createProposal. Requires user's name, email, "
              + "and mobile number. Returns a payment URL to share with the user and a "
              + "transaction ID for tracking. Must be called AFTER createProposal.")
    public PaymentLinkResponse createPaymentLink(
        @ToolParam(description = "Full name of the person making the payment.")
        String name,

        @ToolParam(description = "Email address for payment receipt and policy documents.")
        String email,

        @ToolParam(description = "10-digit mobile number for payment OTP and confirmation.")
        String mobileNumber
    ) {
        // Auto-resolve from session state and internal services
        String token = authService.getValidToken();
        String totalAmount = sessionState.getTotalAmount();
        String dealId = sessionState.getDealId();
        String customerId = sessionState.getCustomerId();
        String proposalNumber = sessionState.getProposalNumber();

        // Compute tomorrow's date
        String proposalStartDate = LocalDate.now().plusDays(1)
            .format(DateTimeFormatter.ISO_LOCAL_DATE);

        PaymentLinkRequest request = PaymentLinkRequest.builder()
            .name(name)
            .email(email)
            .mobileNumber(mobileNumber)
            .totalAmount(totalAmount)
            .payerType("C")
            .product("HOME_1")
            .journeyType("POLICY")
            .core("PF")
            .sendPaymentLink("")
            .templateID(126)
            .proposalStartDate(proposalStartDate)
            .isMobileUser(false)
            .proposalList(List.of(
                ProposalListItem.builder()
                    .dealId(dealId)
                    .customerId(customerId)
                    .intermediaryCode(null)
                    .proposalNumber(proposalNumber)
                    .proposalAmount(totalAmount)
                    .proposalStatus("NCCN")
                    .build()
            ))
            .build();

        PaymentLinkResponse response = apiClient.postWithAuth(
            "/api/v1/pay/AddPaymentRequest", request,
            PaymentLinkResponse.class, token
        );

        // Store transaction ID for Tool 5
        sessionState.setTransactionId(response.getData().getTransactionID());

        return response;
    }
}
```

### Tool 5: FetchPolicyDetails

```java
@Service
public class PolicyDetailsTool {

    private final ApiClientService apiClient;
    private final AuthTokenService authService;
    private final SessionStateService sessionState;

    @Tool(name = "fetchPolicyDetails",
          description = "Fetches policy issuance details and payment status after user has "
              + "completed payment. Returns policy number, payment status, and transaction details. "
              + "Should ONLY be called after user confirms payment completion.")
    public PolicyDetailsResponse fetchPolicyDetails() {
        // Auto-resolve from session state and internal services
        String token = authService.getValidToken();
        String transactionId = sessionState.getTransactionId();

        PolicyDetailsRequest request = new PolicyDetailsRequest(transactionId);

        PolicyDetailsResponse response = apiClient.postWithAuth(
            "/api/v1/pay/ChannelApplicationOrderDetails", request,
            PolicyDetailsResponse.class, token
        );

        return response;
    }
}
```

---

## Conversational Flow Guide for LLM

The LLM should follow this conversational pattern when a user wants to purchase home insurance:

### Phase 1: Information Gathering & Quote

```
LLM: "I'd be happy to help you get a home insurance quote! I'll need a few details about your property.

First, are you the owner of the home or a tenant?"

User: "I'm the owner"

LLM: "Great! Now I need some details:
1. What is your mobile number?
2. What is your email address?
3. What is the PIN code of your property?
4. Would you like to cover the building structure, the home contents, or both?
5. What is the carpet area of your home (in square feet)?
6. What is the approximate construction cost per square foot (in INR)?"

User: [provides details]

LLM: [Calls generateQuote]

LLM: "Here's your quote:
• Quote ID: Q-665544BA
• Sum Insured: ₹50,00,000
• Premium Options:
  - 1 Year:  ₹5,000
  - 3 Years: ₹14,250
  - 5 Years: ₹22,500

Which tenure would you prefer?"
```

### Phase 2: Proposal Creation

```
User: "I'll go with 3 years"

LLM: "Perfect! To create your proposal, I need a few more details:
1. Would you like to add any additional covers? (e.g., Fire Protection, Flood Cover, Theft Cover)
2. What is your date of birth?
3. What is your residential address?"

User: [provides details]

LLM: [Calls createProposal]

LLM: "Your proposal has been created successfully!
• Proposal Number: 2177097406
• Total Amount: ₹8,200

Would you like to proceed to payment?"
```

### Phase 3: Payment

```
User: "Yes, let's do it"

LLM: "To generate your payment link, I need:
1. Your full name (as it should appear on the policy)
2. Email for payment receipt
3. Mobile number for payment verification"

User: [provides details]

LLM: [Calls createAuthToken internally, then createPaymentLink]

LLM: "Here is your payment link:
🔗 [Payment Link URL]

• Amount: ₹8,200
• Transaction ID: PG_TXN_102552

You can pay via Net Banking, UPI, Debit Card, or Credit Card.
Once you've completed the payment, let me know and I'll fetch your policy details!"
```

### Phase 4: Policy Confirmation

```
User: "I've completed the payment"

LLM: [Calls fetchPolicyDetails]

LLM: "🎉 Congratulations! Your policy has been issued!

• Policy Number: 4119/IP/53263786/00/000
• Amount Paid: ₹8,200
• Payment Mode: Net Banking
• Transaction ID: PG_TXN_102552

You'll receive your policy copy on your registered email shortly.

For any future queries:
• Toll-free: 1800 2666
• Email: customersupport@icicilombard.com

Is there anything else I can help you with?"
```

---

## Prompt Template for the Tools Section in MCP Server Prompt

Add the following to the **Tools** section of your MCP server design prompt:

```
Tools:-

1. **generateQuote** — Generates a home insurance quote
   - Endpoint: POST /api/v1/pay/GenerateQuote
   - User Inputs: homeType (home_owner|tenant), mobileNumber, emailId, pincode, coverageType (structure|content), carpetArea, costOfConstruction
   - Returns: quoteId, sumInsured, premiums (oneYear, threeYears, fiveYears)
   - Session State: STORES quoteId and premiums for Tool 2
   - LLM Action: Present all 3 premium options, ask user to select tenure

2. **createProposal** — Creates a formal insurance proposal
   - Endpoint: POST /api/v1/pay/CreateProposal
   - Auto-populated from session: quoteId, selectedTenurePremium (resolved from user's tenure choice + stored premiums)
   - User Inputs: tenure (1|3|5), additionalCovers [{coverName, coverAmount}], customerDOB (YYYY-MM-DD), customerAddress
   - Returns: proposalNumber, dealId, customerId, totalAmount
   - Session State: STORES all 4 response fields for Tool 4
   - LLM Action: Present proposal details, confirm total amount, ask to proceed to payment

3. **createAuthToken** — Internal authentication (NOT exposed to user/LLM)
   - Endpoint: POST /api/v1/pay/CreateToken
   - Credentials: Hardcoded server-side (Username: ILTakeCare, Password: GlfOsg+j2+MOGGez4tVT4Q==)
   - Returns: accessToken, expiryTime
   - Caching: Token cached and auto-refreshed before expiry
   - LLM Action: Transparent — never mentioned to user

4. **createPaymentLink** — Generates payment URL for premium payment
   - Endpoint: POST /api/v1/pay/AddPaymentRequest
   - Authorization: Bearer token from Tool 3
   - Auto-populated from session: totalAmount, dealId, customerId, proposalNumber (from Tool 2)
   - Hardcoded values: PayerType="C", Product="HOME_1", JourneyType="POLICY", Core="PF", SendPaymentLink="", TemplateID=126, ProposalStartDate=tomorrow (YYYY-MM-DD), is_mobile_user=false, IntermediaryCode=null, ProposalStatus="NCCN"
   - User Inputs: name, email, mobileNumber
   - Returns: paymentUrl (share with user), transactionID
   - Session State: STORES transactionId for Tool 5
   - LLM Action: Share payment link with user, show amount and transaction ID, ask user to confirm when payment is done

5. **fetchPolicyDetails** — Fetches policy details after payment completion
   - Endpoint: POST /api/v1/pay/ChannelApplicationOrderDetails
   - Authorization: Bearer token from Tool 3
   - Auto-populated from session: transactionId (from Tool 4)
   - User Inputs: NONE (only called after user confirms payment)
   - Returns: policyNumber, paymentStatus, totalAmount, paymentMode, paymentId, transactionId
   - LLM Action: If SUCCESS — present policy number, amount, payment details, inform user policy copy will arrive via email, provide customer support contact. If PENDING/FAILED — inform user and offer to retry or provide support contact.

Tool Execution Rules:
- Tools MUST be called in order: 1 → 2 → 3 (auto) → 4 → 5
- Tool 3 is internal and auto-triggered before Tool 4 or 5 when no valid token exists
- Session state carries forward: each tool reads outputs from previous tools
- Never ask the user for values that come from API responses (quoteId, proposalNumber, dealId, etc.)
- Always validate user inputs before making API calls
- On API errors, provide helpful messages and offer to retry
- Tool 5 should only be called after explicit user confirmation of payment completion
```
