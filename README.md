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

1. 
	my.company.project.common.interceptor.LoggerInterceptor - it will send the events to the kafka broker
2. 
	my.company.project.service.myfunctionality.logging.events.messaging.consumer.CreateLoggingRequestEventConsumer
	my.company.project.service.myfunctionality.logging.events.messaging.consumer.CreateLoggingResponseEventConsumer
	my.company.project.service.myfunctionality.logging.events.messaging.consumer.CreateLoggingInternalEventConsumer


Finally, Check the console log. You should see something like this: 
...
2021-03-25 16:47:07.298 [http-nio-9185-exec-3][881ad091450c81e5][881ad091450c81e5][] -  INFO  my.company.project.common.interceptor.LoggerInterceptor  - Incoming request: [GET][http://localhost:9185/api/error][host=localhost:9185;connection=keep-alive;cache-control=no-cache;user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.121 Safari/537.36;auth-token=111;postman-token=ebafb154-54cb-d262-feec-304e79a5df0f;accept=*/*;sec-fetch-site=none;sec-fetch-mode=cors;sec-fetch-dest=empty;accept-encoding=gzip, deflate, br;accept-language=it-IT,it;q=0.9,en-GB;q=0.8,en;q=0.7,en-US;q=0.6;cookie=JSESSIONID=2EA1A93F8EE5C4DF3188C037C14B681C][{}][0:0:0:0:0:0:0:1]
...
2021-03-25 16:47:07.305 [org.springframework.kafka.KafkaListenerEndpointContainer#1-0-C-1][687b49129f1c5e9b][47d46202bb7d8cc6][687b49129f1c5e9b] -  INFO  my.company.project.service.myfunctionality.logging.events.messaging.consumer.CreateLoggingRequestEventConsumer  - Received new event='CREATE_REQUEST_LOGGING': my.company.project.kafka.dto.logging.RequestEvent@47edf1ae
...
2021-03-25 16:47:07.316 [org.springframework.kafka.KafkaListenerEndpointContainer#2-0-C-1][83cbcd24c392d18a][35c308c1790f89e0][83cbcd24c392d18a] -  INFO  my.company.project.service.myfunctionality.logging.events.messaging.consumer.CreateLoggingResponseEventConsumer  - Received new event='CREATE_RESPONSE_LOGGING': my.company.project.kafka.dto.logging.ResponseEvent@5556cd1d