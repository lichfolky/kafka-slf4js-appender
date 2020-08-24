# log-collector

A kafka slf4js appender implementation

### log4j2 configuration 

Make sure to not let org.apache.kafka log to a Kafka appender on DEBUG level, 
since that will cause recursive logging:

[SLF4J user manual](http://www.slf4j.org/manual.html)
[log4j 2 configuration](https://logging.apache.org/log4j/2.x/manual/configuration.html)

**Debug levels**  
`ALL < DEBUG < INFO < WARN < ERROR < FATAL < OFF`

### Change JSON Layout schema

[Extend log4j 2](https://logging.apache.org/log4j/2.x/manual/extending.html)  
[Add a new json lib?](https://github.com/bolerio/mjson)

final schema should be
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

### Kafka Docker

https://kafka.apache.org/documentation/
https://docs.cloudera.com/runtime/7.2.1/kafka-managing/topics/kafka-manage-cli-consumer.html

"org.apache.kafka" % "kafka-clients"
  
### Possible improvements

Wrap with Async appender and/or set syncSend to false to log asynchronously.
http://logging.apache.org/log4j/2.x/manual/appenders.html#AsyncAppender

### Other
https://www.baeldung.com/slf4j-with-log4j2-logback
https://blog.10pines.com/2020/03/02/log-custom-json-with-log4j2/
https://gquintana.github.io/2017/12/01/Structured-logging-with-SL-FJ-and-Logback.html
