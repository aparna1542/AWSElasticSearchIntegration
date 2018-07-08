package com.elasticsearch.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;

import org.apache.http.HttpHost;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.util.Strings;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.elasticsearch.constants.ElasticSearchConstants;

@Service
public class ElasticSearchServiceImpl implements ElasticSearchService{

	@Autowired
	private Environment env;

	@Override
    public String getSearchResults(String planName,String sponsorState, String sponsorName) {
		String index = ElasticSearchConstants.SEARCH_INDEX;
		Response response = null;
        String query = null,results = null;
        try {
        	 query = buildElasticSearchQuery(planName,sponsorState,sponsorName);
        } catch (UnsupportedEncodingException e) {
        	e.printStackTrace();
        }
        RestClient client = null;
		try {
			client = getElasticSearchClient();
			response = client.performRequest("GET", "/" + index + "/_search?q="+query);
			results = EntityUtils.toString(response.getEntity());
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

	private String buildElasticSearchQuery(String planName, String sponsorState, String sponsorName) throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
		if (Strings.isNotBlank(planName)) {
			sb.append("+PLAN_NAME:").append(URLEncoder.encode(planName, "UTF-8"));
		}
		if(Strings.isNotBlank(sponsorName)) {
			sb.append("+SPONSOR_DFE_NAME:").append(URLEncoder.encode(sponsorName, "UTF-8"));
		}
		if(Strings.isNotBlank(sponsorState)) {
			sb.append("+SPONS_DFE_MAIL_US_STATE:").append(URLEncoder.encode(sponsorState, "UTF-8"));
		}
		return sb.toString();
	}

}
