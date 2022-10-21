# Getting Started

### Guides to run 
1- ./gradlew bootjar
2- ./gradlew composeUp 


OpenApi link http://localhost:8080/swagger-ui/index.html

//To get List of available medications to load, Please use 

curl -X 'GET' \
'http://localhost:8080/v1/medications' \
-H 'accept: application/json'