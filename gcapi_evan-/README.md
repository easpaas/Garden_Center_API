**Garden Center API**

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
