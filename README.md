# AWSElasticSearchIntegration

This is a Spring Boot application thats connects with AWS Elastic Search service and renders search resutls.

Step 1:
Bulk upload the Json payload that has the data from f_5500_2017_latest.csv using the below Curl command. This will index the documents to AWS Elastic Search Index.

curl -XPOST https://search-myelasticsearch-zuw4no5mbkaybczk3wvzuzrnsy.us-west-1.es.amazonaws.com/_bulk --data-binary @bulk_upload_1.json -H 'Content-Type: application/json'

ConvertCSVtoJson.java helper class is used to convert Csv to Json.
