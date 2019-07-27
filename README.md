# flinkPoc
Flink poc
## Project creation
mvn archetype:generate                               \
      -DarchetypeGroupId=org.apache.flink              \
      -DarchetypeArtifactId=flink-quickstart-java      \
      -DarchetypeVersion=1.2.1

## Build project:
mvn clean package -Pbuild-jar

## host resolution
> sudo vi /etc/hosts
> Add below entry in the host file to resolve kafka as localhost:
    127.0.0.1       kafka