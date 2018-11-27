## Task description:
Design and implement a RESTful API (including data model and the backing implementation) for money
transfers between accounts.
## Technologies/libraries used:
* Java 8
* Play Framework - to build web applications with Java & Scala (sorry Spring)
* JSR 354 RI Moneta - to simplify work with monetary amounts and exchange process
* H2 Database - data storage
* Hibernate - JPA implementation (I thought that it might be an overkill, but Play provides nice out of the box support)
* JUnit, Mockito, AssertJ - for testing
## Endpoints:
There are two main endpoints

View all available accounts:
```
GET     /rest/v1/accounts  
``` 
Make money exchange (you need to pass a valid json with 3 elemets - from, to and amount):
```
POST    /rest/v1/transfer
```  
Different exceptional cases are handled so feel free to break any endpoint :)
## Request examples:
![Transfer](https://pp.userapi.com/c850536/v850536210/55d4b/117beoN7bfI.jpg)
![Accounts](https://pp.userapi.com/c850536/v850536448/54b33/mrYwu2jaGKg.jpg)
## Production ready artifact:
It's absolutely not a best practice to store artifacts in the repository, however you can found production ready **money-transfer-api-1.0.zip**

To run application just unzip it and run script from /bin catalogue (there are Unix and Windows scripts here).
## TODO:
Completely switch to async I/O.