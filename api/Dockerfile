FROM java:8-jre-alpine

ENV APP_PATH=/app

WORKDIR $APP_PATH

ADD build/libs/kafka-store-api.jar $APP_PATH/

ENTRYPOINT java -jar kafka-store-api.jar