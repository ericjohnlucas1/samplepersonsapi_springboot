# Backend Engineer Homework Assignment - Hatch

This repository contaions my solution to the Backend Engineer Homework Assignment. For convience, the assignment PDF is in this repository as "Backend_Engineer_Homework_Assignment.pdf"

## Spring Boot API

The Spring Boot API was implemented as requested using Maven as the build automation tool. Endpoints are as follows:

1) Create a new Person from input values, a POST will be made to the `/persons` endpoint with a JSON body specifying the input.

    Here is an example request using cURL: 

    `curl -X POST localhost:8080/persons -H "Content-Type: application/json" -d '{"name":"eric", "age":"10"}'`

    A JSON will be returned describing the created person with the unique ID assigned: `{"id":1,"name":"eric","age":10}`

    If the request body does not contain a name, a 400 bad request error will be returned with a JSON describing the error.

2) To fetch a person by ID, a GET request can be made to `/persons/{id}`.

    For example `curl localhost:8080/persons/1` will return `{"id":1,"name":"eric","age":10}` if the above call was made to create this person.

    If a person with that ID does not exist, a 404 not found error will be returned with a JSON describing the error.

3) To fetch all persons, there is an endpoint `/persons` which will list all of the persons in the database. There is an optional query parameter  `youngerthan` that will only fetch persons younger then the specified integer age.

    For example if the call in 1) above was made to create thath person, a subquent request `curl localhost:8080/persons` would return `[{"id":1,"name":"eric","age":10}]`, but `curl localhost:8080/persons?youngerthan=10` will return an empty list

## Scripting

In this repository there is a script I wrote in python called `cumulative_time.py` that calculates the percentage of cumulative time taken running through the endpoints of one or more Spring Boot controllers, and outputing the results sorted from greatest to least. The `controller_endpoint_runthroughs` dictionary at the top of the file can be used to set the controllers and the function for running through the endpoints. I have it currently set to run though the controller of my Spring boot application in two trials.

This solution is based on how I interpreted the problem statement. If it was meant to be interpreted in another way, please shoot me an email and I will correct it accordingly.

## DevOps

Suppose I have an AWS Application Load Balancer that distributes load to 3 EC2 instances running identical server processes. The ALB is assigned a DNS A Record by AWS, and we have a CNAME alias to that A Record.

To be notified if this service is not working, I would have an endpoint health check configured in route 53 with a Amazon CloudWatch alarm to notify the appropriate engineer if the service stops working. For my spring boot sample application, the fetch persons endpoint(GET `/persons`) would be a good health check endpoint because it always return 202 OK no matter what is in the database.

It is also important to have debug logs to see what might have went wrong. I would have the logs from the application running on EC2 streamed to cloudwatch to accomplish this.


## SQL

Given the following schema given in the problem statement:

To set debug_level to 'NONE' if the last time the row was updated is more than 10 days in the past, I would run the following SQL:

```UPDATE user_debug
SET debug_level = 'NONE'
WHERE update_date < NOW()-interval 10 day
```

## Testing

I have manually tested this through many different cases, as well as writting a basic automated test using `org.springframework.test.web.servlet.result.MockMvcResultHandlers`. 

This test can be found in this repository at `/src/test/java/com/example/restservice/RestControllerTests.java`
