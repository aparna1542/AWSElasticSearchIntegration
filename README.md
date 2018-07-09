# AWSElasticSearchIntegration

This is a Spring Boot application thats connects with AWS Elastic Search service and renders search resutls.

Building the Project
-----------------------

1) mvn clean package
2) mvn spring-boot::run

 aws-elastic-search-integration-0.1.0.jar is uploaded to Lambda function created in the AWS console.

Step 1:
--------
Bulk upload the Json payload that has the data from f_5500_2017_latest.csv using the below Curl command. 

This will index the documents to AWS Elastic Search Index.

curl -XPOST https://search-myelasticsearch-zuw4no5mbkaybczk3wvzuzrnsy.us-west-1.es.amazonaws.com/_bulk --data-binary @bulk_upload_2.json -H 'Content-Type: application/json'

ConvertCSVtoJson.java helper class is used to convert Csv to Json.

bulk_upload_2.json has around 800 documents for testing and due to size constraints for elastic search indexing,
limiting the number of documents to the same.

Step 2:
--------
Since its a spring boot application, it can be run stand alone. Please use the below endpoint for testing

http://localhost:8080/search?sponsorName=VELOCITY%20CAPITAL%20MANAGEMENT

Next the application can be accessed using AWS API gateway, for this I chose Lambda function that would be invoked by the api gateway.

Please find the API gateway endpoints below

1) Search by Sponsor Name
https://lbs8v3iwv8.execute-api.us-west-1.amazonaws.com/dev/search?sponsorName=VELOCITY%20CAPITAL%20MANAGEMENT

2) Search by Sponsor State
https://lbs8v3iwv8.execute-api.us-west-1.amazonaws.com/dev/search?sponsorState=CA

3) Search by Plan Name
https://lbs8v3iwv8.execute-api.us-west-1.amazonaws.com/dev/search?planName=QLOGIC