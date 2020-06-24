# opentracing

The project deals with instrumentation of spring boot application.

This project reliases of Manual Instrumentation of application.

The projects is consisted of 3 Microservices

1.Demo Application
2.Subscription Application
3.Special Services Application

We have added dependancy of opentracing and jaeger client in pom.xml for instrumentation

The application microservices is set to run on port 8090,8091,8092 respectively.

If you do not have maven install you can use ./mvnw executable script.

to run application, you can run on seperate terminals:

./mvnw spring-boot:run -Dmain.class=opentracinng.DemoApplication
./mvnw spring-boot:run -Dmain.class=opentracinng.subscription.SubscriptionApp
./mvnw spring-boot:run -Dmain.class=opentracinng.specialservice.SpecialSubApp

I have added dependancy of mysql in pom and mysql properties in application.properties which can be ignored as project does not deal with database.

(Knowledge Base)
Manual Autoinstrumentation requires bean of opentacing declaed in every microservices
That tracer bean can be used by autowiring in respective microservices classes.

