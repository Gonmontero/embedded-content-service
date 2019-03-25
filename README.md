# embedded Content service

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

_I would choose OPTION B, it's not just because structuring third party response seems like organized,
but also because this way we can add value to the processed data by adapting such information to our Business Model._

2)
Can you describe (a summary) of the steps needed to add a new provider into
your service? (just imagine that you are guiding a fellow developer into how to
implement the task that was given to him).

_The steps are:_ 

1. _understand the url Pattern that oembed uses._
2. _Create a POST Endpoint that receives unique data from the provider that differentiate from others. These are name, API endpoint, Url Schemes._
3. _At all times, write method and variables names that are representative, validate for invalid inputs, handle exceptions and provide logging to track events that occurs in the system_
4. _Process and structure these data to create a unique entity. Url Schemes can have wildcards, so replace these wildcards with regular expression that can be used to validate in future requests. Also the schemes can be multiples and each are unique schemes, so a Set data structure would be proper to use._
5. _Persist this entity and provide ways to retrieve it later on from Database, currently any ORM framework are friendly enough to implement CRUD methods._
6. _If no error has occured and every validation has passed, respond with an http Code that is representative of the result of the transaction, if a problem is encountered then return a proper message explaining the error to the user and a it's http code._

3)
We found that some of the providers are a bit slow to return the response and
this is causing performance issues in our system. We have identified that the
data returned by these services doesn't change with the time so, we have
decided to store the data in our side and just send a request to the providers if
we don't have the data in our storage. Which kind of storage system would you
use for this and why? Given the input for your service is a provider's name and a
URL, can you summarize in a few words how would you query this storage?

_I would definitely implement any in-memory database like Redis, memcached or ehcache to reduce time consumption. 
As an example, if we implement Redis, which is a key-value in-memory database, we can use as a key the Provider's Name and the Url, and store whatever value the provider had returned, in memory.
Meaning that if we have multiple users consuming the same provider and the same Url, then the key will be the same for every client, we only need to call the provider only once, and every other call accessed from memory,
which is a great performance boost. Redis needs to be implemented wisely, since if we have multiples instances of a service running in production environment but not enough Redis Servers, and a Huge Load of request are made,
then the connection pools will get maxxed out, and bottle necks will impact in the Service Performance._


## API Documentation

Once Service is up and running, use Swagger UI for an easier interaction with the service:

- Local ENV: http://localhost:8080/embedded-content-service/swagger-ui.html#/

## Prerequisites

* Java JDK 1.8
* Maven 3

# Installing

* TBD

# Release notes

* TBD