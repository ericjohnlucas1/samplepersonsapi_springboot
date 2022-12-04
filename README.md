# samplepersonsapi_springboot

This repository contaions my solution to the Backend Engineer Homework Assignment. 

## The Spring Boot API was implemented as requested using Maven as the build automation tool. Endpoints are as follows:

1) Create a new Person from input values, a POST will be made to the `/persons` endpoint with a JSON body specifying the input.

Here is an example request using cURL: `curl -X POST localhost:8080/persons -H "Content-Type: application/json" -d '{"name":"eric", "age":"10"}'`

A JSON will be returned describing the created person with the unique ID assigned: `{"id":1,"name":"eric","age":10}`

If the request body does not contain a name, a 400 bad request error will be returned with a JSON describing the error.

2) To fetch a person by ID, a GET request can be made to `/persons/{id}`.

For example `curl localhost:8080/persons/1` will return `{"id":1,"name":"eric","age":10}` if the above call was made to create this person.

If a person with that ID does not exist, a 404 not found error will be returned with a JSON describing the error.

3) To fetch all persons, there is an endpoint `/persons` which will list all of the persons in the database. There is an optional query parameter `youngerthan` that will only fetch persons younger then the specified integer age.

For example if the call in 1) above was made to create thath person `curl localhost:8080/persons` will return `[{"id":1,"name":"eric","age":10}]`, but `curl localhost:8080/persons?youngerthan=10` will return an empty list
