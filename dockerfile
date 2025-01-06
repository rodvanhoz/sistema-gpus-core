FROM openjdk:11
VOLUME /tmp
ADD ./target/sistema-gpus-core.jar sistema-gpus-core.jar
ENTRYPOINT ["java","-jar","/sistema-gpus-core.jar"]