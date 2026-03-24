package com.icicilombard.mcp.service;

import com.icicilombard.mcp.model.Dtos.CreatePaymentLinkRequest;
import com.icicilombard.mcp.model.Dtos.CreatePaymentLinkResponse;
import com.icicilombard.mcp.model.Dtos.CreateProposalRequest;
import com.icicilombard.mcp.model.Dtos.CreateProposalResponse;
import com.icicilombard.mcp.model.Dtos.FetchPolicyDetailsRequest;
import com.icicilombard.mcp.model.Dtos.FetchPolicyDetailsResponse;
import com.icicilombard.mcp.model.Dtos.GenerateQuoteRequest;
import com.icicilombard.mcp.model.Dtos.GenerateQuoteResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class IltcApiClient {

    private final RestClient restClient;

    public IltcApiClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public GenerateQuoteResponse generateQuote(GenerateQuoteRequest request) {
        return restClient.post().uri("/GenerateQuote").body(request).retrieve().body(GenerateQuoteResponse.class);
    }

    public CreateProposalResponse createProposal(CreateProposalRequest request) {
        return restClient.post().uri("/CreateProposal").body(request).retrieve().body(CreateProposalResponse.class);
    }

    public CreatePaymentLinkResponse createPaymentLink(CreatePaymentLinkRequest request, String token) {
        return restClient.post().uri("/AddPaymentRequest")
                .header("Authorization", "Bearer " + token)
                .body(request)
                .retrieve()
                .body(CreatePaymentLinkResponse.class);
    }

    public FetchPolicyDetailsResponse fetchPolicyDetails(FetchPolicyDetailsRequest request, String token) {
        return restClient.post().uri("/ChannelApplicationOrderDetails")
                .header("Authorization", "Bearer " + token)
                .body(request)
                .retrieve()
                .body(FetchPolicyDetailsResponse.class);
    }
}
