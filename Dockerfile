From eclipse-temurin:17-jdk-ubi9-minimal

RUN mkdir /deployment && chmod 777 /deployment

workdir /deployment
COPY ./target/demo-0.0.1-SNAPSHOT.jar .

CMD java -jar demo-0.0.1-SNAPSHOT.jar