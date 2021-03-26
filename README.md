INTRODUCTION
In order to perform a test you need kafka and mongodb installed on your local machine. You can quickly set up the environment using the file docker-compose.yml included in the project, just perform the following steps:

- Download & install Docker from here: https://www.docker.com/products/docker-desktop
- Start Docker and wait until ot will be upper and running
- Execute the command "docker-compose up" from the the folder that contains the file docker-compose.yml


If you have already these software installed, be sure that the properties point to the right addresses:

	spring.data.mongodb.host=localhost
	spring.data.mongodb.port=27017
	spring.kafka.bootstrap-servers=localhost:9092

After that, you can build the microservice using the command: mvn clean install -P dev

Since the project is very large, i moved all dependencies declarations specified in other project modules (directly and indirectly related to the main one) in this project. This will make troubleshooting easier.
I also moved a lot of java files in the same project. Indeed, the structure of the resulting projects is unclear.

At this point run the microservice using as main class my.company.project.DevAppStartup (with DEV profile set -> spring.profiles.active=${ACTIVE_PROFILE:DEV}) and invoke the url http://localhost:9185/api/v1.0/resources/1

The following classes will be executed:

1. MyController	   
2. MyEventConsumer

Finally, Check the console log. You should see something like this:
2021-03-26 10:05:49.825 [http-nio-9185-exec-4][77f01208383f07b4][77f01208383f07b4][] -  INFO  my.company.project.service.MyController  - Called!
2021-03-26 10:05:49.826 [http-nio-9185-exec-4][77f01208383f07b4][77f01208383f07b4][] -  DEBUG my.company.project.service.MyController  - Sending event 'MyEvent'=MyEvent{id='111111'} to: my.topic
2021-03-26 10:05:49.848 [kafka-producer-network-thread | myApplicationName-1][][][] -  DEBUG my.company.project.service.MyController  - The event 'MyEvent'=MyEvent{id='111111'} has been sent to the topic=my.topic correctly!
2021-03-26 10:05:49.853 [org.springframework.kafka.KafkaListenerEndpointContainer#0-0-C-1][9b6090ff79ab6eee][112de37698c09e2a][9b6090ff79ab6eee] -  INFO  my.company.project.service.MyEventConsumer  - Received new event='MyEvent': MyEvent{id='111111'}
