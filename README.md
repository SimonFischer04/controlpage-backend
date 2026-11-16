# ControlPage-Backend

## Canonical source

I do development on my [personal GitLab instance](https://gitlab.fischerserver.eu/controlpage/controlpage-backend).

But contributions are welcome on [Gitlab.com](https://gitlab.com/controlpage/controlpage-backend).

Furthermore, this project is mirrored to [GitHub](https://github.com/SimonFischer04/controlpage-backend). But don't open any issues or pull requests there!

## Introduction

This is the backend part of the ControlPage application. For more information see the [Docs](https://gitlab.com/controlpage/controlpage-docs).

## ---WIP---

## Docker image

`docker build -t controlpage-backend .` builds the backend from source into an image, it only needs Docker.

On the default branch the pipeline (`.gitlab-ci.yml`) runs the tests and, if they pass, pushes the image to this
project's container registry as `<registry>/<this project>:latest`. On other branches the image is only built, not pushed.
Other projects use that image instead of building the backend themselves, e.g. the frontend for its end-to-end tests.

## Notes:
- http://localhost:42000/swagger-ui.html
- http://localhost:42000/v3/api-docs

---

# AUTO GENERATED SUMMARY

## Database

The backend stores its data in a [PostgreSQL](https://www.postgresql.org/) database called `controlpage`.

| Setting  | Environment variable         | Default           |
|----------|------------------------------|-------------------|
| Host     | `DB_HOST`                    | `localhost`       |
| User     | `SPRING_DATASOURCE_USERNAME` | `controlpageuser` |
| Password | `SPRING_DATASOURCE_PASSWORD` | see `application.yml` |

For development `docker compose -f dev-env-compose.yml up -d` starts a database that matches these defaults (and [Adminer](http://localhost:8080)).
The tables are created on startup.

The tests start their own PostgreSQL with [Testcontainers](https://testcontainers.com/), so they only need Docker (`mvn test`).
