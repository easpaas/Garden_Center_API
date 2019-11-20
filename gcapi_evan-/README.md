**Garden Center API**

This RESTful API follows the Business/Technical Requirements specified in the GCAPI project instructions found [here](https://docs.google.com/document/d/1Wvulm4bhMcKJyaK2fkuNUiqN0p7QdssoJKG4M-JfOBM/edit#). (This link can only be accessed if user has permissions.)

### Setup
1. Start a basic MongoDB Docker container 
```
docker run -d -p 27017:27017 --name gcdb mongo
```
2. Import into IntelliJ as Maven project.
3. Start the application.

**Running Mockito Tests**
* Verify that there exists a test file as a sibling to src/main. 
* Right-click the java folder in test/java and select run all tests.
* Verify that all tests pass. 

**Postman Setup**\
Import the Postman collection to test the API: https://www.getpostman.com/collections/f916eda89ecc547b47c8

**Running Postman**
* User should verify that there are four entities (Users, Customers, Products, Orders) each with full CRUD functionality.
* Starting in any order, follow the collection requests for each CRUD operation. 
