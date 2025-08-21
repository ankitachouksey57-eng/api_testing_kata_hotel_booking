# API_Automation_Hotel_Booking

We have used below:
* Java - JDK version: 21.0.8
* Maven 3.9.11

To run all test:
select the "tags = @hotelBooking" in TestRunner file and run with command `mvn clean test`

we have different tags as per the requirement of test scenario.
1. @successfulBookingCreation
2. @negativeBookingCreation
3. @getBookingByRoomID
4. @getBookingByInvalidRoomID
5. @getBookingByInvalidBookingID
6. @bookingUpdateSuccess
7. @bookingUpdateNegative
8. @successfullyDeletedWithId
9. @negativeScenarioForDelete
10. @getBookingSummaryByValidID
11. @getBookingSummaryByInvalidID
12. @AuthValidation
13. @e2eBookingFlow

## Background
The application under test is a simple hotel booking website where you can book a room and also send a form with a request.
The website can be accessed at https://automationintesting.online/.
The Swagger documentation for the two endpoints you will be testing can be found at:
Booking endpoint: https://automationintesting.online/booking/swagger-ui/index.html
Optionally, you also have the Authentican endpoint: https://automationintesting.online/auth/swagger-ui/index.html

## Task
You are provided with an extremely basic API test project.
Please clone the project and create a new branch with your name. At the end, please push your branch to this project.
The project to start from, can be found here: https://github.com/freddyschoeters/API_Testing_kata
Your task is to set up an API Test Automation framework from this project using Java, Rest-Assured, and Cucumber (feel free to add more dependencies if required).
It is up to you to define the test cases. You don’t need to have a full coverage, but you need to show enough variation on the types of tests that you would need to write and execute, and what to check in the response.
This kata has the purpose to evaluate both your technical skills as well as your testing skills.
`For this task, you will use the booking endpoint.`

## Requirements
* Use Java as the programming language
* Use Rest-Assured as the API testing library
* Use Cucumber as the BDD framework
* Design your codebase using a proper Java design pattern
* Write good tests with correct checks
* Use Git for version control and push your codebase to an open GitHub repository
* Make regular commits to demonstrate your progress


## Deliverables
* Your branch pushed in the provided project.
* A comprehensive test suite covering the scenarios mentioned above
* A well-structured codebase with proper design patterns and comments
* Regular commits demonstrating your progress

## Evaluation Criteria
* Being able to successfully run the tests
* Correctness and completeness of the test suite
* Quality of the codebase (design patterns, structure, code quality, …)
* Use of Rest-Assured and Cucumber features
* Commit history and progress demonstration
