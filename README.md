# Distributed Systems - console client (example app)

This is a demo Java console app demonstrating the usage of Spring Integration.
For the server application it connects to, see [Distributed Systems - server] (https://github.com/bresalio/distributed-systems-server).

## Built With

* [Eclipse Kepler](https://www.eclipse.org/) - the IDE used
* [Maven](https://maven.apache.org/) - Dependency Management
* [Spring Integration](https://projects.spring.io/spring-integration/)

## Installation

Due to the fact that this program is a client to a server app, we need to do some work before running it.

### Deploying the server app

Before running this app, you must deploy [Distributed Systems - server] (https://github.com/bresalio/distributed-systems-server).
About how to deploy this server app, you can read in its readme file.

For verifying whether the server has been deployed correctly, test its endpoints with HTTP calls (as documented in its readme),
and verify that their response status code is 200 ("OK").
An example HTTP GET request for testing endpoint "getFilmData":

```
http://<host:port>/distributed-systems-server/getFilmData?title=Titanic
```

"<host:port>" should be replaced with the host you deployed the app on and its HTTP port (e.g. "localhost:8081").
("distributed-systems-server" might be different, as well, if you deployed the app with a different name.)
The response body JSON should contain the following fields: "title", "genre", "director", "mainActors", "minutes", "publicationYear".

### Setting the host and port in Spring configuration

As the client app makes calls to the server endpoints, it needs info about the URL where the server app is available;
however, the URL depends on the host and port you previously deployed the server app on.

The default "host:port" setting is "localhost:8081".
But, if this information is not valid in your case, you can rewrite this configuration as a system property
in the client app's Spring configuration file: [si-config.xml] (https://github.com/bresalio/distributed-systems-console-client/blob/master/src/main/resources/si-config.xml) (src/main/resources).

The configuration takes place in the tag <beans> ... <bean id="systemProps" ...>, concretely:

```
<util:properties>
	<prop key="hostAndPort">localhost:8081</prop>
	<prop key="deploymentName">distributed-systems-server</prop>
</util:properties>
```

As you can see, you can optionally also rewrite info about the deployment name here (the name under that you deployed the server app).

### Running the JAR from command line

If all of the previous workaround operations are completed, we can finally run the program.
We can do so in an IDE (e.g. Eclipse) internally, but we can also run a .jar (Java ARchive) file from command line.
The following steps assume that you have installed Maven on your machine.

How to create an executable .jar file from the downloaded source code:
* Open command line and navigate to the root folder of the downloaded project.
* Type the following command:
```
mvn clean package
```
* When the build has ended with success, you should find two generated .jar files in the "/target" folder:
their names end in "-0.0.1-SNAPSHOT" and "-0.0.1-SNAPSHOT.one-jar". (You can copy/rename the files.)
The second one is the so-called "fat" .jar, containing the dependencies.
This is the one you should run.

* Run the .jar file with this command (where <distributed-systems-console-client> refers to the path and name of the .jar file):
```
java -jar <distributed-systems-console-client>.jar
```

* Wait for the program's own prompt (">> ") to appear, and type your command (and arguments) after it.

## Commands

There are 10+1 commands in the app. The "+1" is "exit"; the other ones correspond to endpoints in the server app.

Both commands and arguments are case-insensitive.
In case of multiple arguments, they should be delimited with spaces, but most commands are one-argument:
here spaces imply intended spaces (e.g. in a multi-word name or title).
In case of an invalid command or invalid count of arguments, the program's response tells us about this situation.

An example command, with the program's response below:

```
>> film_data once upon a time in the west
Film:
	Title: Once Upon a Time in the West
	Genre: western
	Director: Sergio Leone
	Main actors: [Henry Fonda, Claudia Cardinale]
	Length (minutes): 171
	Publication year: 1968
```

### List of commands:

* all_films

Queries and prints all 3 films of the server's dummy "database".

* all_films_of_genre, all_films_of_director, all_films_of_actor <parameter>

Queries and prints the films of the server's dummy "database" where the given genre / director / actor appears.

* all_films_between_length <min_length> <max_length>

Queries and prints all films of our database between the given length bounds (inclusive, minutes).
The arguments must be non-negative integers (e.g. "100 300").
If the bounds are given inverted (e.g. "300 100"), the program reverts them and takes the greater bound as the maximum.

* film_data <title>

Queries and prints the film's description if found, "No data found" otherwise.

* add_rating <opinion> <title>

Adds a rating to the dummy "database".
It takes two arguments: an integer between 1-5 representing the opinion, and the film title.
It can print two sentences based on whether the operation was successful:
"Rating added successfully: <rating verbally>" or "Film is not existing; could not add rating!".

* ratings_of_film <title>

It queries and lists the ratings added to a film, or "No data found!" if no ratings have been added yet.

* average_rating_of_film <title>

Prints the average rating in a verbal form (e.g. "Very good!"), or "Not found!" if no ratings have been added.

* omdb_film_data <title>

Queries OMDb film data indirectly, through our server. OMDb is a huge open movie database, so many films can be found there.