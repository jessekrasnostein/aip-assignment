package au.edu.uts.aip.pin.charge;

import au.edu.uts.aip.pin.responses.CardResponse;

public class ChargeResponse {
    
    private ChargeResponse response;

    public ChargeResponse getResponse() {
        return response;
    }
    
    private String token;
    private String success;
    private String amount;
    private String currency;
    private String description;
    private String email;
    private String ip_address;
    private String created_at;
    private String status_message;
    private String error_message;
    private String captured;
    private String authorisation_expired;
    private String transfer;
    private String amount_refunded;
    private String total_fees;
    private String merchant_entitlement;
    private String refund_pending;
    private String settlement_currency;
    
    private CardResponse card;

    public String getCaptured() {
        return captured;
    }

    public void setCaptured(String captured) {
        this.captured = captured;
    }

    public String getAuthorisation_expired() {
        return authorisation_expired;
    }

    public void setAuthorisation_expired(String authorisation_expired) {
        this.authorisation_expired = authorisation_expired;
    }

    public String getTransfer() {
        return transfer;
    }

    public void setTransfer(String transfer) {
        this.transfer = transfer;
    }

    public String getAmount_refunded() {
        return amount_refunded;
    }

    public void setAmount_refunded(String amount_refunded) {
        this.amount_refunded = amount_refunded;
    }

    public String getTotal_fees() {
        return total_fees;
    }

    public void setTotal_fees(String total_fees) {
        this.total_fees = total_fees;
    }

    public String getMerchant_entitlement() {
        return merchant_entitlement;
    }

    public void setMerchant_entitlement(String merchant_entitlement) {
        this.merchant_entitlement = merchant_entitlement;
    }

    public String getRefund_pending() {
        return refund_pending;
    }

    public void setRefund_pending(String refund_pending) {
        this.refund_pending = refund_pending;
    }

    public String getSettlement_currency() {
        return settlement_currency;
    }

    public void setSettlement_currency(String settlement_currency) {
        this.settlement_currency = settlement_currency;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIp_address() {
        return ip_address;
    }

    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getStatus_message() {
        return status_message;
    }

    public void setStatus_message(String status_message) {
        this.status_message = status_message;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }
    
    
    

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CardResponse getCard() {
        return card;
    }

    public void setCard(CardResponse card) {
        this.card = card;
    }
    
    
    

}
