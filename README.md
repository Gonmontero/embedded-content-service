# Embedded content service

REST Api which serves as proxy of other service applications that support oEmbed Specification.


# Introduction

In this README file you'll find enough information to Install, Run and test this application. 
Also you will find answers to tech questions asked for this test, and I will also provide information to how this was implemented and why was implemented such way.


## Technical Questions:

1)
We have given you two different response formats (OPTIONS A & B) for you to
select one. In one case the response is returned as the original source returned
it, and in the second case, it is transformed. Which do you think it best for this
use case and why do you think so?

> _I would choose OPTION B, it's not just because structuring third party response seems like organized,
but also because this way we can add value to the processed data by adapting such information to our Business Model._

2)
Can you describe (a summary) of the steps needed to add a new provider into
your service? (just imagine that you are guiding a fellow developer into how to
implement the task that was given to him).

_The steps are:_

> 1. _understand the url Pattern that oembed uses._
> 2. _Create a POST Endpoint that receives unique data from the provider that differentiate from others. These are name, API endpoint, Url Schemes._
> 3. _At all times, write method and variables names that are representative, validate for invalid inputs, handle exceptions and provide logging to track events that occurs in the system_
> 4. _Process and structure these data to create a unique entity. Url Schemes can have wildcards, so replace these wildcards with regular expression that can be used to validate in future requests. Also the schemes can be multiples and each are unique schemes, so a Set data structure would be proper to use._
> 5. _Persist this entity and provide ways to retrieve it later on from Database, currently any ORM framework are friendly enough to implement CRUD methods._
> 6. _If no error has occured and every validation has passed, respond with an http Code that is representative of the result of the transaction, if a problem is encountered then return a proper message explaining the error to the user and a it's http code._

3)
We found that some of the providers are a bit slow to return the response and
this is causing performance issues in our system. We have identified that the
data returned by these services doesn't change with the time so, we have
decided to store the data in our side and just send a request to the providers if
we don't have the data in our storage. Which kind of storage system would you
use for this and why? Given the input for your service is a provider's name and a
URL, can you summarize in a few words how would you query this storage?

> _I would definitely implement any in-memory database like Redis, memcached or ehcache to reduce time consumption.
As an example, if we implement Redis, which is a key-value in-memory database, we can use as a key the Provider's Name and the Url, and store whatever value the provider had returned, in memory.
Meaning that if we have multiple users consuming the same provider and the same Url, then the key will be the same for every client, we only need to call the provider only once, and every other call accessed from memory,
which is a great performance boost. Redis needs to be implemented wisely, since if we have multiples instances of a service running in production environment but not enough Redis Servers, and a Huge Load of request are made,
then the connection pools will get maxxed out, and bottle necks will impact in the Service Performance._

## Prerequisites

* Java JDK 1.8 : https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
* Maven 3 : https://maven.apache.org/install.html
* Docker: https://docs.docker.com/install/
* Docker-compose: https://docs.docker.com/compose/install/

## Installing

 There are two ways for running this application. We can open the proyect in a Java IDE, open Class Application.java and press the Run Button, or we can do the Docker way, which I will explain:

* First we need to Clone this repository:

    > git clone https://github.com/Gonmontero/embedded-content-service.git

* Open Terminal and check Maven is configured correctly:

    > mvn -v

      Command should respond with:

      Apache Maven 3.6.0 (97c98ec64a1fdfee7767ce5ffb20918da4f719f3; 2018-10-24T15:41:47-03:00)
      Maven home: /Library/apache-maven-3.6.0
      Java version: 1.8.0_202, vendor: Oracle Corporation, runtime: /Library/Java/JavaVirtualMachines/jdk1.8.0_202.jdk/Contents/Home/jre
      Default locale: en_US, platform encoding: UTF-8
      OS name: "mac os x", version: "10.14.3", arch: "x86_64", family: "mac"

      (Note that java version is 1.8)

 * Change your current directoy to the location of the project and run:

    > mvn clean package

        Expected result should look like this:

        [INFO] Replacing main artifact with repackaged archive
        [INFO] ------------------------------------------------------------------------
        [INFO] BUILD SUCCESS
        [INFO] ------------------------------------------------------------------------
        [INFO] Total time:  14.001 s
        [INFO] Finished at: 2019-03-27T01:17:46-03:00
        [INFO] ------------------------------------------------------------------------

 * Once the proccess has finished, start the Docker daemon and run:

    > docker-compose build

 * Last but not least, we have to tell docker to start the image we have just built:

    > docker-compose up

         That should start the application and run it in the localhost Port 8080

         The expected result we are looking for is:
         ecs_1  |  INFO [main] org.apache.catalina.startup.Catalina.start Server startup in 13382 ms


## API Documentation

Once the Service is up and running, use Swagger UI for an easier interaction with the service:

- Local ENV: http://localhost:8080/embedded-content-service/swagger-ui.html#/

Endpoint Name | Http Request | Description |
| --- | --- | --- |
List Providers Information | GET http://localhost:8080/embedded-content-service/admin/providers | This will list all the providers that are registered in the database. |
Register Provider | POST http://localhost:8080/embedded-content-service/admin/providers | This will register a new Provider given an "apiEndpoint", "providerName" and a list of "urlSchemes":[] in the Body Request. All these data are provided by https://oembed.com/ especification. Refer to Swagger UI for more information. |
| Get registered provider | GET http://localhost:8080/embedded-content-service/admin/providers/{providerName} | Gets the "apiEndpoint", "providerName" and a list of "urlSchemes" of the provider that has been registered |
Get Embedded Content | GET http://localhost:8080/embedded-content-service/oembed/{ProviderName}?url={URL} | Given a valid Url, gets the embedded content from a registered provider |


# Release notes

* Release-1.0.0:

> This was the first day of the proyect, the repository was created and the technical questions were delivered, but analyzing oembed specification, patterns, and creating a basic model took most of the effort.

* Release-1.1.0:

> The goal for this release was to develop a solid structure that supported Global Error Handling and logging mechanism, to guarantee that bugfixing in the following releases were easier to track. The service was design following the Service Layer Pattern to separate functionality, this way, if we need to do changes on a Layer, this would reduce impact to just that one layer. Also error handling exceptions was implemented to be shared across the layers, thus reusing code.
The main functionality was the the registry of Providers: using ORM through DAOs to persist validated data from user input. In this case I chose JPA and a H2 in-memory Database as this was the easiest and faster way to get functionality delivered on time.
To provide a solution regarding the oembed specification, I decided to use Regular expressions which allowed me to proccess the Url schemes that oembed specified. This was tested mostly with unit tests.

* Release-1.2.0:

> After finishing 1.1.0, I had the perfect baseline to start working on the main problem, retrieving content from the providers. In this release, most of the effort was put into searching and deciding which design pattern provided me the best solution to my problem. I thought of the Proxy Pattern, and setting a redis server in order to cache the Provider's response, but I decided not to go for it, as it was going to be very time consuming, and I had no time for delays.
So the final decision was to go for a command pattern using Hystrix and retrofit to call the Provider.
Command Pattern let me to encapsulate all the information that I needed in order to invoke the Provider's method, and by using Hystrix I could create a resilent and fault tolerance application.
The result of this release-1.2.0 was a complete call to a registered provider, but with OPTION A as a response, which was not desired by me.

* Release-1.3.0:

> Release-1.3.0 kicked off with the goal of getting OPTION B as the service Response. So i had to create new ways to adapt the Provider's Response to my project model. In order to achieve this, I had to delegate the proccessing and modification of the new classes with a Data Processor and some Builders. By doing this I could ensure that the classes that I needed to be updated  were modified to the desired model independently of the the implementation.
These were huge changes, so I had to perfom regression testing to make sure that the service was returning the desired result. The result of the testing resulted in defects found and fixed. Release-1.3.0 ended up having a complete tested solution, with Documentation and some tech debts behind.

* Release-1.4.0:

> The final version of this product, the goal for this release was to clean code, find bugs and fix and do full Regression testing once again. Documentation and Product is ready for delivery.

