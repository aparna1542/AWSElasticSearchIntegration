package com.elasticsearch.service;

import com.amazonaws.services.lambda.runtime.Context;
import com.elasticsearch.gateway.model.ApiGatewayRequest;
import com.elasticsearch.gateway.model.ApiGatewayResponse;

public interface ElasticSearchService {

	public String getSearchResults(String planName,String sponsorName, String sponsorState);
	public ApiGatewayResponse getSearchResults(ApiGatewayRequest req, Context ctx);
}
