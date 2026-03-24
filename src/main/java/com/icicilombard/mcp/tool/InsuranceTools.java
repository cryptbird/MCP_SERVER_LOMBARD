package com.icicilombard.mcp.tool;

import com.icicilombard.mcp.model.Dtos;
import com.icicilombard.mcp.model.SessionState;
import com.icicilombard.mcp.service.AuthTokenService;
import com.icicilombard.mcp.service.IltcApiClient;
import com.icicilombard.mcp.service.SessionStateService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

@Component
public class InsuranceTools {

    private final IltcApiClient apiClient;
    private final AuthTokenService authTokenService;
    private final SessionStateService sessionStateService;

    public InsuranceTools(
            IltcApiClient apiClient,
            AuthTokenService authTokenService,
            SessionStateService sessionStateService
    ) {
        this.apiClient = apiClient;
        this.authTokenService = authTokenService;
        this.sessionStateService = sessionStateService;
    }

    @Tool(name = "generateQuote", description = "Generate quote for ICICI Lombard home insurance.")
    public Dtos.GenerateQuoteResponse generateQuote(
            @ToolParam(description = "Session ID to maintain state across tool calls.") String sessionId,
            @ToolParam(description = "home_owner or tenant") String homeType,
            @ToolParam(description = "10-digit mobile number") String mobileNumber,
            @ToolParam(description = "Email") String emailId,
            @ToolParam(description = "6-digit pincode") String pincode,
            @ToolParam(description = "structure or content") String coverageType,
            @ToolParam(description = "Carpet area in square feet") int carpetArea,
            @ToolParam(description = "Construction cost per sqft") int costOfConstruction
    ) {
        Dtos.GenerateQuoteRequest req = new Dtos.GenerateQuoteRequest(
                homeType, mobileNumber, emailId, pincode, coverageType, carpetArea, costOfConstruction);
        Dtos.GenerateQuoteResponse res = apiClient.generateQuote(req);
        SessionState state = sessionStateService.get(sessionId);
        state.setQuoteId(res.quoteId());
        state.setPremiums(res.premiums());
        return res;
    }

    @Tool(name = "createProposal", description = "Create proposal using quote and selected tenure.")
    public Dtos.CreateProposalResponse createProposal(
            @ToolParam(description = "Session ID from generateQuote step") String sessionId,
            @ToolParam(description = "1, 3, or 5") int tenure,
            @ToolParam(description = "Optional add-on covers list") List<Dtos.AdditionalCover> additionalCovers,
            @ToolParam(description = "DOB in YYYY-MM-DD") String customerDOB,
            @ToolParam(description = "Customer address") String customerAddress
    ) {
        SessionState state = sessionStateService.get(sessionId);
        if (state.getQuoteId() == null || state.getPremiums() == null) {
            throw new IllegalStateException("Missing quote state. Run generateQuote first.");
        }
        double premium = switch (tenure) {
            case 1 -> state.getPremiums().oneYear();
            case 3 -> state.getPremiums().threeYears();
            case 5 -> state.getPremiums().fiveYears();
            default -> throw new IllegalArgumentException("Tenure must be 1, 3, or 5");
        };
        Dtos.CreateProposalRequest req = new Dtos.CreateProposalRequest(
                state.getQuoteId(),
                tenure,
                premium,
                additionalCovers == null ? List.of() : additionalCovers,
                customerDOB,
                customerAddress
        );
        Dtos.CreateProposalResponse res = apiClient.createProposal(req);
        state.setProposalNumber(res.proposalNumber());
        state.setDealId(res.dealId());
        state.setCustomerId(res.customerId());
        state.setTotalAmount(res.totalAmount());
        return res;
    }

    @Tool(name = "createPaymentLink", description = "Create payment URL from proposal data and user payer info.")
    public Dtos.CreatePaymentLinkResponse createPaymentLink(
            @ToolParam(description = "Session ID from previous steps") String sessionId,
            @ToolParam(description = "Customer full name") String name,
            @ToolParam(description = "Customer email") String email,
            @ToolParam(description = "Customer mobile number") String mobileNumber
    ) {
        SessionState state = sessionStateService.get(sessionId);
        if (state.getProposalNumber() == null || state.getDealId() == null || state.getCustomerId() == null || state.getTotalAmount() == null) {
            throw new IllegalStateException("Missing proposal state. Run createProposal first.");
        }
        String token = authTokenService.getValidToken();
        Dtos.CreatePaymentLinkRequest req = new Dtos.CreatePaymentLinkRequest(
                name,
                email,
                mobileNumber,
                state.getTotalAmount(),
                "C",
                "HOME_1",
                "POLICY",
                "PF",
                "",
                126,
                LocalDate.now().plusDays(1).toString(),
                false,
                List.of(new Dtos.ProposalItem(
                        state.getDealId(),
                        state.getCustomerId(),
                        null,
                        state.getProposalNumber(),
                        state.getTotalAmount(),
                        "NCCN"
                ))
        );
        Dtos.CreatePaymentLinkResponse res = apiClient.createPaymentLink(req, token);
        if (res != null && res.data() != null) {
            state.setTransactionId(res.data().transactionID());
        }
        return res;
    }

    @Tool(name = "fetchPolicyDetails", description = "Fetch policy details after payment completion.")
    public Dtos.FetchPolicyDetailsResponse fetchPolicyDetails(
            @ToolParam(description = "Session ID from previous steps") String sessionId
    ) {
        SessionState state = sessionStateService.get(sessionId);
        if (state.getTransactionId() == null) {
            throw new IllegalStateException("Missing transaction ID. Run createPaymentLink first.");
        }
        String token = authTokenService.getValidToken();
        return apiClient.fetchPolicyDetails(new Dtos.FetchPolicyDetailsRequest(state.getTransactionId()), token);
    }
}
