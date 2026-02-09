BUILD_JAR=target/demo-0.0.1-SNAPSHOT.jar
IMAGE_NAME=demo:latest

.PHONY: all build test run docker-build clean

all: build

build:
	./mvnw -B -DskipTests package

test:
	./mvnw test

run:
	./mvnw spring-boot:run

docker-build: build
	docker build -t $(IMAGE_NAME) .

clean:
	./mvnw -B clean
