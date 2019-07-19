# Event-Driven Microservices with Kafka

This project shows how to setup and run the demo used in the talk "Event-Driven Microservices with Apache Kafka".  

## Prepare Environment

The environment is completely based on docker containers. In order to easily start the multiple containers, we are going to use Docker Compose. You need to have at least 8 GB of RAM available, better is 12 GB or 16 GB. 

Clone the GitHub repo

```
git clone https://github.com/gschmutz/event-driven-microservices-demo.git
```

and navigate to the folder `event-driven-microservices-demo`

```
cd event-driven-microservices-demo
```

### Preparing environment

In order for Kafka to work with this Docker Compose setup, two environment variables are necessary, which are configured with the IP address of the docker machine as well as the Public IP of the docker machine. 

You can either add them to `/etc/environment` (without export) to make them persistent:

```
export DOCKER_HOST_IP=192.168.25.136
export PUBLIC_IP=192.168.25.136
```

Make sure to adapt the IP address according to your environment. 

Optionally you can also create an `.env` file inside the `docker` folder with the following content:

```
DOCKER_HOST_IP=192.168.25.136
PUBLIC_IP=192.168.25.136
```

Last but not least add `streamingplatform` as an alias to the `/etc/hosts` file on the machine you are using to run the demo on.

```
192.168.25.136	streamingplatform
```

### Starting the infrastructure using Docker Compose

From the `event-driven-microservices-demo` folder, navigate to the docker folder and start the various docker containers 

```
cd docker
docker-compose up -d
```

To show all logs of all containers use

```
docker-compose logs -f
```

To show only the logs for some of the containers, for example `connect-1` and `connect-2`, use

```
docker-compose logs -f broker-1 broker-2
```

Some services in the `docker-compose.yml` are optional and can be removed, if you don't have enough resources to start them. 

### Creating the necessary Kafka Topics

The Kafka cluster is configured with `auto.topic.create.enable` set to `false`. Therefore we first have to create all the necessary topics, using the `kafka-topics` command line utility of Apache Kafka. 

We can easily get access to the `kafka-topics` CLI by navigating into one of the containers for the 3 Kafka Brokers. Let's use `broker-1`

```
docker exec -ti broker-1 bash
```

First let's see all existing topics

```
kafka-topics --zookeeper zookeeper-1:2181 --list
```

And now create the topics `customer` and `order` both with cleanup policy set to `compact` in order to enable log compaction. 

```
kafka-topics --zookeeper zookeeper-1:2181 --create --topic customer --partitions 8 --replication-factor 2 --config cleanup.policy=compact --config segment.ms=100 --config segment.bytes=1000 --config delete.retention.ms=100 --config min.cleanable.dirty.ratio=0.01

kafka-topics --zookeeper zookeeper-1:2181 --create --topic order --partitions 8 --replication-factor 2 --config cleanup.policy=compact --config segment.ms=100 -config segment.bytes=1000 --config delete.retention.ms=100 --config min.cleanable.dirty.ratio=0.01
```

Make sure to exit from the container after the topics have been created successfully.

```
exit
```

If you don't like to work with the CLI, you can also create the Kafka topics using the [Kafka Manager GUI](http://streamingplatform:29000). 

## Register Schemas in Confluent Schema Registry

Before we can use the microservices, we have to register the Avro schemas in the Confluent Schema Registry. 

The Avro schemas are available in the `meta` project. Navigate into the folder 

```
cd event-driven-microservices-demo/java/meta
```

and execute the `schema-registry:register` goal

```
mvn schema-registry:register
```

The schemas should be displayed in the Schema Registry UI: <http://streamingplatform:28002>. 

## Start the Customer Microservice

Let's now start the Customer Microservice. It is available as Docker image and has to be configured with the IP address of the Docker Host. 

```
docker run -ti -e PUBLIC_IP=$PUBLIC_IP -p 8080:8080 -d --name customer-management-ms gschmutz/customer-management-ms:1.0.1
```

to view the log and see that is has been started successfully, enter

```
docker logs -f customer-managment-ms
```


## Start the Order Microservice

Next let's also start the Order Microservice. It is also available as Docker image and has to be configured with the IP address of the Docker Host. 


```
docker run -ti -e PUBLIC_IP=$PUBLIC_IP -p 8081:8080 -d --name order-management-ms gschmutz/order-management-ms:1.0.1
```

to view the log and see that is has been started successfully, enter

```
docker logs -f order-management-ms
```

## Add a Customer

The following curl command adds a new customer via the Customer Microservice:

```
curl -X POST -H 'Content-Type: application/json' -i http://streamingplatform:8080/api/customers --data '{
  "customerId" : 1,
  "firstName" : "Guido",
  "lastName" : "Schmutz",
  "title" : "Mr",
  "emailAddress" : "guido.schmutz@someemail.com",
  "addresses" : [
  		{
  		  "street" : "Altikofenstrasse",
         "number" : "158",
         "postcode" : "",
		  "city" : "Worblaufen",
         "country" : "Switzerland"
  		}
  ]
}'
```

```
curl -X POST -H 'Content-Type: application/json' -i http://streamingplatform:8080/api/customers --data '{
  "customerId" : 2,
  "firstName" : "Renata",
  "lastName" : "Schmutz",
  "title" : "Ms",
  "emailAddress" : "renata.schmutz@someemail.com",
  "addresses" : [
  		{
  		  "street" : "Altikofenstrasse",
         "number" : "158",
         "postcode" : "",
		  "city" : "Worblaufen",
         "country" : "Switzerland"
  		}
  ]
}'
```


## Consuming messages with "Normal" consumer

In one of the Kafka broker

```
docker exec -ti docker_broker-1_1 bash
```

Use the Kafka Console Consumer to get the current messages

```
kafka-console-consumer --bootstrap-server broker-1:9092 --topic customer
```

Use the Kafka Console Consumer to get the all historical messages

```
kafka-console-consumer --bootstrap-server broker-1:9092 --topic customer --from-beginning
```

## Consuming messages with Avro-Console Consumer

Connect to the schema-registry container to use the `kafka-avro-console-consumer` utility:

```
docker exec -ti schema-registry bash
```

Consume the Avro messages from the topic `customer`

```
kafka-avro-console-consumer --bootstrap-server broker-1:9092 --topic customer --property schema.registry.url=http://schema-registry:8081
```

Consume the Avro messages from the topic `customer` using the `--from-beginning` option

```
kafka-avro-console-consumer --bootstrap-server broker-1:9092 --topic customer --from-beginning
```

## Reset consumer group

List the topics to which the group is subscribed

```
kafka-consumer-groups --bootstrap-server broker-1:9092  --group orderManagement --describe
```

Reset the consumer offset for a topic (preview)

```
kafka-consumer-groups --bootstrap-server broker-1:9092 --group orderManagement --topic customer --reset-offsets --to-earliest
```

Reset the consumer offset for a topic (execute)

```
kafka-consumer-groups --bootstrap-server broker-1:9092 --group orderManagement --topic customer --reset-offsets --to-earliest --execute
```
