# Kafka Producer Twitter

<p align="center">
  <img alt="Project Goals" src=".github/project-goals.png" width="100%">
</p>

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

8. Create the topic that will be used

```kafka-topics.sh --zookeeper 127.0.0.1:2181 --create --topic twitter_tweets --partitions 6 --replication-factor 1```

9. Start the consumer

```kafka-console-consumer.sh --bootstrap-server 127.0.0.1:9092 --topic twitter_tweets```

10. Before run the main class, you need to create an application in https://developer.twitter.com/  and take your keys and tokens

Set the variables: 

- BONSAI_ELASTICSEARCH_URL
- BONSAI_ELASTICSEARCH_ACCESS_TOKEN
- BONSAI_ELASTICSEARCH_ACCESS_TOKEN_SECRET


## For more information

Used Elasticsearch - 

- Manual installation: https://www.elastic.co/guide/en/elasticsearch/reference/current/getting-started-install.html

- Elasticsearch on Demand - 3-node-cluster: https://bonsai.io/ - 

## Delivery Semantics

- At most once: offsets are commited as soon as the message batch is received. If the processing goes wrong, the message will be lost (it won't be read again).
- At least once: offsets are commited after the message is processed. If the processing goes wrong, the message will be read again. This can result in duplicate processing of messages. Make sure your processing
is idempotent (i.e processing again the messages won't impact your systems)
- Exacly once: Can be achieved for Kafka => Kafka workflows using Kafka Streams API. For Kafka => Sink workflows, use an idempotent consumer.

## Consumer Poll Behavior

- Kafka consumers have a "poll" model, while many other messaging bus is enterprises have a "push" model.
- This allows consumers to control where in the log they want to consume, how fast, and gives them the ability to replay events.
- Fetch.min.bytes (default 1):
    - Controls how much data you want to pull at least on each request
    - Helps improving throughput and decreasing request number
    - At the cost of latency
- Max.poll.records (default 500):
    - Controls how many records to receive per poll request
    - Increase if your messages are very small and have a lot of available RAM
    - Good to monitor how many records are polled per request
- Max.partitions.fetch.bytes (default 1MB):
    - Maximum data returned by the broker per partition
    - If you read from 100 partitions, you'll need a lot of memory (RAM)
- Fetch.max.bytes (default 50MB):
    - Maximum data returned for each fetch request (covers multiple partitions)
    - The consumer performs multiple fetches in parallel
    
***- Change these settings only if your consumer maxes out on throughput already***

## Consumer Offset Commits Strategies

- There are two most common patterns for committing offsets in a application

- 2 strategies:
    - (easy) enable.auto.commit = true & synchronous processing of batches
    - (medium) enable.auto.commit = false & manual commit of offsets
        - You control when you commit offsets and what's the condition for committing them.
        - Example: accumulating records into a buffer and then flushing the buffer to a database + committing offsets then.
    
- With auto-commit, offsets will be committed automatically for you at regular interval (auto.commit.interval.ms=5000 by default) every-time you call .poll()
- If you don't use synchronous processing, you will be in "at-most-once" behavior because offsets will be committed before your data is processed
    
