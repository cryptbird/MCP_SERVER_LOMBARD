package com.icicilombard.mcp.model;

public class SessionState {
    private String quoteId;
    private Dtos.GenerateQuoteResponse.Premiums premiums;
    private String proposalNumber;
    private String dealId;
    private String customerId;
    private String totalAmount;
    private String transactionId;

    public String getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(String quoteId) {
        this.quoteId = quoteId;
    }

    public Dtos.GenerateQuoteResponse.Premiums getPremiums() {
        return premiums;
    }

    public void setPremiums(Dtos.GenerateQuoteResponse.Premiums premiums) {
        this.premiums = premiums;
    }

    public String getProposalNumber() {
        return proposalNumber;
    }

    public void setProposalNumber(String proposalNumber) {
        this.proposalNumber = proposalNumber;
    }

    public String getDealId() {
        return dealId;
    }

    public void setDealId(String dealId) {
        this.dealId = dealId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
