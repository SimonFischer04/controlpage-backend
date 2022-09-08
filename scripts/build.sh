#!/bin/bash
mvn clean install -Dmaven.test.skip=true
# docker build -t controlpage-backend .
docker build -t gitlab-registry.fischerserver.eu/controlpage/controlpage-releases/backend:latest .
docker push gitlab-registry.fischerserver.eu/controlpage/controlpage-releases/backend:latest
