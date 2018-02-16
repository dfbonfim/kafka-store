#!/usr/bin/env bash

echo 'Start task creation on debezium'

curl -i -X POST \
   -H "Content-Type:application/json" \
   -d \
'{
   "name":"connector_mysql",
   "config":{
      "name":"connector_mysql",
      "connector.class":"io.debezium.connector.mysql.MySqlConnector",
      "tasks.max":"1",
      "decimal.handling.mode": "double",
      "database.hostname":"mysql",
      "database.port":"3306",
      "database.user":"root",
      "database.password":"root",
      "database.server.name":"debezium",
      "database.history.kafka.bootstrap.servers":"kafka:9092",
      "database.history.kafka.topic":"debezium.schema.changes",
      "snapshot.mode":"schema_only"
   }
}' \
 'http://localhost:8083/connectors'

echo ""
echo 'Finished task creation on debezium'
