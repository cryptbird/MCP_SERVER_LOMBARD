package com.icicilombard.mcp.model;

import java.util.List;
import java.util.Map;

public final class Dtos {
    private Dtos() {}

    public record AdditionalCover(String coverName, double coverAmount) {}

    public record GenerateQuoteRequest(
            String homeType,
            String mobileNumber,
            String emailId,
            String pincode,
            String coverageType,
            int carpetArea,
            int costOfConstruction
    ) {}

    public record GenerateQuoteResponse(String quoteId, long sumInsured, Premiums premiums) {
        public record Premiums(double oneYear, double threeYears, double fiveYears) {}
    }

    public record CreateProposalRequest(
            String quoteId,
            int tenure,
            double selectedTenurePremium,
            List<AdditionalCover> additionalCovers,
            String customerDOB,
            String customerAddress
    ) {}

    public record CreateProposalResponse(
            String proposalNumber,
            String dealId,
            String customerId,
            String totalAmount
    ) {}

    public record CreateTokenResponse(int statusCode, String statusMessage, TokenData data) {
        public record TokenData(String accessToken, String expiryTime) {}
    }

    public record ProposalItem(
            String DealId,
            String CustomerId,
            Object IntermediaryCode,
            String ProposalNumber,
            String ProposalAmount,
            String ProposalStatus
    ) {}

    public record CreatePaymentLinkRequest(
            String Name,
            String Email,
            String MobileNumber,
            String TotalAmount,
            String PayerType,
            String Product,
            String JourneyType,
            String Core,
            String SendPaymentLink,
            int TemplateID,
            String ProposalStartDate,
            boolean is_mobile_user,
            List<ProposalItem> ProposalList
    ) {}

    public record CreatePaymentLinkResponse(int statusCode, String statusMessage, PaymentData data) {
        public record PaymentData(String url, String transactionID) {}
    }

    public record FetchPolicyDetailsRequest(String transaction_id) {}

    public record FetchPolicyDetailsResponse(
            int statusCode,
            String statusMessage,
            String message,
            PolicyData data
    ) {
        public record PolicyData(
                String pg_status,
                String payment_status,
                String payment_received_on,
                String transaction_id,
                String total_amount,
                String payment_mode,
                String payment_id,
                List<Map<String, Object>> channelApplicationProposalDetailsResp
        ) {}
    }
}
