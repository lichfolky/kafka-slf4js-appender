# log-collector

A slf4js kafka appender with a custom json layout.

**Debug levels** `ALL < DEBUG < INFO < WARN < ERROR < FATAL < OFF`

the final json schema should be
```
    "log_source" -> event.loggerName,
    "log_level" -> event.level.toString,
    "message" -> event.message,
    "timestamp" -> DateTimeFormatter.ISO_INSTANT.format(event.timestamp),
    "thread" -> event.thread,
    "cause" -> event.maybeCause.getOrElse(""),
    "stack_trace" -> event.maybeStackTrace.getOrElse(""),
    "all" -> all
```

### log4j2 configuration 

Make sure to not let org.apache.kafka log to a Kafka appender on DEBUG level, 
since that will cause recursive logging:

[SLF4J user manual](http://www.slf4j.org/manual.html)
[log4j 2 configuration](https://logging.apache.org/log4j/2.x/manual/configuration.html)

### the JSON Layout

[Extend log4j 2](https://logging.apache.org/log4j/2.x/manual/extending.html) 
[Layouts](https://logging.apache.org/log4j/2.x/manual/extending.html#Layouts)
[Add a new json lib](https://github.com/bolerio/mjson)

All Layouts must implement the Layout interface.
~~Layouts that format the event into a String should extend AbstractStringLayout~~
Every Layout must declare itself as a plugin using the Plugin annotation.
 The type must be "Core", and the elementType must be "layout". 
 
 printObject should be set to true if the plugin's toString method will provide a representation of the object and its parameters.
 The name of the plugin must match the value users should use to specify it as an element in their Appender configuration. The plugin also must provide a static method annotated as a PluginFactory and with each of the methods parameters annotated with PluginAttr or PluginElement as appropriate.


###  Test the Kafka Appender with a Kafka Docker

https://kafka.apache.org/documentation/
docker image at:
https://github.com/wurstmeister/kafka-docker
his guide:
http://wurstmeister.github.io/kafka-docker/

                                                                    
### Possible improvements
+ Wrap with Async appender and/or set syncSend to false to log asynchronously. http://logging.apache.org/log4j/2.x/manual/appenders.html#AsyncAppender

### Other resources
https://www.baeldung.com/slf4j-with-log4j2-logback
https://blog.10pines.com/2020/03/02/log-custom-json-with-log4j2/
https://gquintana.github.io/2017/12/01/Structured-logging-with-SL-FJ-and-Logback.html


docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' 7d9c59d1defc


### Run the appender in the kafka docker

Do not use localhost or 127.0.0.1 as the host IP if you want to run multiple brokers otherwise the brokers won’t be able to communicate

#### Build the project using the [sbt-native-packager](https://www.scala-sbt.org/sbt-native-packager/gettingstarted.html)

in the sbt shell:
```
universal:packageBin
```
unzip the zip file `log-collector-0.1.zip` in `/log-collector/target/universal`

#### Run the kafka docker
start the kafka-docker with this configuration: (change the with the right path `/Your/Project/Path:/code`)
```
version: '2'
services:
  zookeeper:
    image: wurstmeister/zookeeper
    ports:
      - "2181:2181"
  kafka:
    build: .
    ports:
      - "9092:9092"
    environment:
      KAFKA_ADVERTISED_HOST_NAME: localhost
      KAFKA_CREATE_TOPICS: "test:1:1"
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
    volumes:
      - /var/run/docker.sock:/var/run/docker.sock
      - /Users/lich/Coding/log-collector:/code
```

Start kafka :
```
docker-compose -f docker-compose-single-broker.yml up
```

#### execute the generator 

run a shell in the container:
```
docker exec -it kafka-docker_kafka_1 bash                                                                                                     
```

run log-collector in `/code/target/universal/log-collector-0.1/bin/`
```
./log-collector 
```
consume the logs:
```
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic log-test --from-beginning
```

#### stop kafka

```
docker-compose down
```