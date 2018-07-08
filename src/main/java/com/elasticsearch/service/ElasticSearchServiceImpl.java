package com.elasticsearch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.elasticsearch.constants.ElasticSearchConstants;
import com.elasticsearch.gateway.model.ApiGatewayRequest;
import com.elasticsearch.gateway.model.ApiGatewayResponse;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;

@Service
public class ElasticSearchServiceImpl implements ElasticSearchService{

	@Autowired
	private Environment env;
	private int responseCode;
	
	public String getSearchResults(String planName,String sponsorState, String sponsorName) {		
        return processSearchResults(planName, sponsorState, sponsorName);
	}
	
	private String processSearchResults(String planName,String sponsorState, String sponsorName) {
		String index = ElasticSearchConstants.SEARCH_INDEX;
		Response response = null;
        String results = null;
        String query = buildElasticSearchQuery(planName,sponsorState,sponsorName);
        RestClient client = null;
		try {
			client = getElasticSearchClient();
			response = client.performRequest("GET", "/" + index + "/_search?q="+query);
			results = EntityUtils.toString(response.getEntity());
			responseCode = response.getStatusLine().getStatusCode();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
       return results; 
	}
	
	private RestClient getElasticSearchClient() {
		return RestClient.builder(new HttpHost(env.getProperty("aws.elasticsearch.host"))).build();
	}

	private String buildElasticSearchQuery(String planName, String sponsorState, String sponsorName) {
		StringBuilder sb = new StringBuilder();
		if (planName !=null) {
			sb.append("+PLAN_NAME:").append(planName);
		}
		if(sponsorName != null) {
			sb.append("+SPONSOR_DFE_NAME:").append(sponsorName);
		}
		if(sponsorState != null) {
			sb.append("+SPONS_DFE_MAIL_US_STATE:").append(sponsorState);
		}
		return sb.toString();
	}

	@Override
	public ApiGatewayResponse getSearchResults(ApiGatewayRequest req, Context ctx) {
		ApiGatewayResponse response = new ApiGatewayResponse();
		LambdaLogger logger = ctx.getLogger();
		String results = processSearchResults(req.getQueryStringParameters().get("planName"), req.getQueryStringParameters().get("sponsorState"), req.getQueryStringParameters().get("sponsorName"));
		response.setBody(results);
		response.setStatusCode(String.valueOf(responseCode));
		return response;
	}

}
