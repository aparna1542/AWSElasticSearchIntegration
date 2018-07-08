package com.elasticsearch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.elasticsearch.service.ElasticSearchService;

@RestController
public class ElasticSearchController {

	@Autowired
	ElasticSearchService elasticSearchService;
	
	@RequestMapping("/search")
	public String searchDocuments(@RequestParam(value = "planName", required = false) String planName,
			@RequestParam(value = "sponsorName", required = false) String sponsorName,
			@RequestParam(value = "sponsorState", required = false) String sponsorState) {
		String results = elasticSearchService.getSearchResults(planName, sponsorState, sponsorName);
		return results;
	}
}
