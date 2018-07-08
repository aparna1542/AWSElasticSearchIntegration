package com.elasticsearch.service;

import com.amazonaws.services.lambda.runtime.Context;

public interface ElasticSearchService {

	public String getSearchResults(String planName,String sponsorName, String sponsorState);
}
