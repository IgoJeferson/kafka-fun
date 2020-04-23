# Kafka Basics

## Setup 

1. Download and Setup Java 8 JDK:
    ``` sudo apt install openjdk-8-jdk ```
2. Download & Extract the Kafka binaries from https://kafka.apache.org/downloads
3. Try Kafka commands using ```bin/kafka-topics.sh``` (for example)
4. Edit PATH to include Kafka (in ```~/.bashrc``` for example) ```PATH="$PATH:/your/path/to/your/kafka/bin ```
5. Edit Zookeeper & Kafka configs using a text editor
zookeeper.properties: ```dataDir=/your/path/to/data/zookeeper```
server.properties: ```log.dirs=/your/path/to/data/kafka```
6. Start Zookeeper in one terminal window: ```zookeeper-server-start.sh config/zookeeper.properties```
7. Start Kafka in another terminal window: ```kafka-server-start.sh config/server.properties```

## Kafka CLI

Some basic commands:

```kafka-topics.sh --zookeeper 127.0.0.1:2181 --topic first_topic --create --partitions 3 --replication-factor 1```
 
```kafka-topics.sh --zookeeper 127.0.0.1:2181 --topic first_topic --describe```

```kafka-topics.sh --zookeeper 127.0.0.1:2181 --topic second_topic --create --partitions 6 --replication-factor 1```

```kafka-topics.sh --zookeeper 127.0.0.1:2181 --topic second_topic --delete```

```kafka-console-producer.sh --broker-list 127.0.0.1:9092 --topic first_topic --producer-property acks=all```

```kafka-console-consumer.sh --bootstrap-server 127.0.0.1:9092 --topic first_topic --from-beginning```

```kafka-console-consumer.sh --bootstrap-server 127.0.0.1:9092 --topic first_topic --from-beginning```

```kafka-console-consumer.sh --bootstrap-server 127.0.0.1:9092 --topic first_topic --group my-first-application```

- Consumer Groups
```kafka-consumer-groups.sh --bootstrap-server localhost:9092 --list```

```kafka-consumer-groups.sh --bootstrap-server localhost:9092 --describe --group my-first-application```

- Reset offset
```kafka-consumer-groups.sh --bootstrap-server localhost:9092 --group my-first-application --reset-offsets --to-earliest --execute --topic first_topic```

### The CLI have many options, but here are the other that are most commonly used:

* Producer with keys

```kafka-console-producer --broker-list 127.0.0.1:9092 --topic first_topic --property parse.key=true --property key.separator=,```
> key,value

> another key,another value
	
* Consumer with keys

```kafka-console-consumer --bootstrap-server 127.0.0.1:9092 --topic first_topic --from-beginning --property print.key=true --property key.separator=,```

### Conduktor - Kafka GUI
Kafka does not come bundled with a UI, but I have created a software name Conduktor to help you use Kafka visually.
https://www.conduktor.io/

### Client Configurations
There exist a lot of options to:
- configure producer: https://kafka.apache.org/documentation/#producerconfigs
- configure consumers:  https://kafka.apache.org/documentation/#consumerconfigs

** For more information: https://kafka.apache.org/documentation/