package com.elasticsearch.lambda;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.elasticsearch.gateway.model.ApiGatewayRequest;
import com.elasticsearch.gateway.model.ApiGatewayResponse;
import com.elasticsearch.service.ElasticSearchService;

public class ElasticSearchRequestHandler implements RequestHandler<ApiGatewayRequest, ApiGatewayResponse>{

	private ElasticSearchService searchService;
	
	public ApiGatewayResponse handleRequest(ApiGatewayRequest req, Context context) {
	       ApiGatewayResponse response = searchService.getSearchResults(req, context);
	       return response;

	}
}
