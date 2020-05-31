package com.paranika.erp.heap_flow_reports.common;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ResponseBuilder {

	private String message;
	private String errorMessage;
	private Integer statusCode;

	public ResponseBuilder() {

	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public String getResponseText() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String jsonVal = gson.toJson(this);
		return jsonVal;

	}

	public static void main(String[] args) {
		ResponseBuilder builder = new ResponseBuilder();
		builder.setErrorMessage(null);
		builder.setMessage("Success");
		builder.setStatusCode(201);

		System.out.println(builder.getResponseText());
	}
}
