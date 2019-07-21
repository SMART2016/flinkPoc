# flinkPoc
Flink poc
## Project creation
mvn archetype:generate                               \
      -DarchetypeGroupId=org.apache.flink              \
      -DarchetypeArtifactId=flink-quickstart-java      \
      -DarchetypeVersion=1.2.1

## Build project:
mvn clean package -Pbuild-jar