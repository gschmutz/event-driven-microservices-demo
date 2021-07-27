package com.trivadis.ms.sample.order.api;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderApi {

    @JsonProperty(value = "orderId", required = true)
    private String orderId;
    
    @JsonProperty(value = "customerId", required = true)
    private String customerId;

	@JsonProperty(value = "currency", required = true)
	private String currency;

    @JsonProperty(value = "orderLines", required = false)
    private List<OrderLineApi> orderLines;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public List<OrderLineApi> getOrderLines() {
		return orderLines;
	}

	public void setOrderLines(List<OrderLineApi> orderLines) {
		this.orderLines = orderLines;
	}


    
}
