# Claude MCP Setup

Use your deployed Railway URL and register this MCP server in Claude Desktop MCP config.

## Example config

```json
{
  "mcpServers": {
    "icici-lombard-home": {
      "transport": "sse",
      "url": "https://<your-railway-domain>/mcp",
      "headers": {
        "X-MCP-API-KEY": "<optional-if-enabled>"
      }
    }
  }
}
```

## Conversation instruction snippet

Use this behavioral instruction in Claude prompt:

- Always keep one `sessionId` through full purchase journey.
- Call tools in order:
  1. `generateQuote`
  2. `createProposal`
  3. `createPaymentLink`
  4. Ask user if payment is completed
  5. `fetchPolicyDetails` only if user confirms yes
- Do not ask for IDs auto-resolved from previous tool outputs.
