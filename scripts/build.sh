#!/bin/bash
# Builds the backend image by hand. The pipeline (.gitlab-ci.yml) does this on every push.
# (the image builds the backend from source, see Dockerfile)
#docker build -t controlpage-backend .
docker build -t gitlab-registry.fischerserver.eu/controlpage/controlpage-releases/backend:latest .
docker push gitlab-registry.fischerserver.eu/controlpage/controlpage-releases/backend:latest
