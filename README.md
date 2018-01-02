Giulio Dallatorre giulio.dallatorre@studenti.unitn.it
Worked alone
My server is at https://introsde-assignment-2-dallator.herokuapp.com

My project tries to follow the principles of eXtreme Programming, thus I did refactored whenever possible and extracted methods to avoid duplicated code. 
The client works on jersey 2 which also gives an automatic marshalling and unmarshalling from/to classes provided there are libraries recognized as valid parsers for that kind of stream.
The client object is generared fresh at every request, the response is always buffered in memory and uses the same entities from the server, except the persistence methods have been removed.
Printing is done tru the superclass ClientEvaluation and I did my best to extract all the useful informations from the webtarget and the repsonse. The result is printed on a Writer (file) while the result header is also printed on console. Based on the response payload mediaType I did used 2 methods to pretty print XML and JSON, ORG.json is used as json formatter, apache xerces as XML formatter.
The server is pretty much standard except the merging of persons in the persistence unit, there I do check if I'm creating a person which is essentially a copy of another existing one, it will update the existing one. Person.java line 110.

The client essentially does a sequential number of calls twice, differentiating them via a global valiable that defines the MediaType to marshal and the accepted one, from XML to JSON... and the Writer is also refreshed to write the log on a new file.
The server divides the services in 3 categories:
- Initializer /database_init
which initializes the database with the sample data required
- ActivityResource /activity_types
which only accepts a get, and returns the activity types
- PersonResource /person
which is resposible for handling all the requests coming based on /person
The responses are created using a ResponseBuilder, and the data is automatically marshalled/unmarshalled.
I had to implement some wrapper classes in order to have the correct output as per specifications (return the list of activity types). These objects only contain a List of the wrapped objects.

just run ant execute.client to compile and run the client

I did implemented an exception mapper on the server, which catches every jersey WebApplicationException and returns them to the client with the same error code, but with the added stacktrace. Debugging was a nightmare without this. If the exception does not come from the application a INTERNAL_SERVER_ERROR is thrown with the "cause". If such an error comes to the cluent with no apparent stacktrace or with DAO initialization failure it means that the heroku app has hanged and needs a restart.
