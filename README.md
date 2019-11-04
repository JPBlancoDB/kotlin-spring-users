# Demo Kotlin+Spring
Demo application for handling users and authorization with jwt.

## Pre-requisites
* [Liquibase](http://www.liquibase.org/) (migrations)
* MySQL

## Getting started

### Migrations

Migration project boilerplate and migration files were generated with [Liquibase-CLI](https://github.com/JPBlancoDB/liquibase-cli)

1. Rename `liquibase.properties.sample` to `liquibase.properties` and modify accordingly.

2. For running migrations, execute the following in your console:

```bash
cd migrations && liquibase update
```

### Run the app

1. Add your DB credentials in `application.yml`

2. Execute in your console
```bash
./gradlew bootRun
```