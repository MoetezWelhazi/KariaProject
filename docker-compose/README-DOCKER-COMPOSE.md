# JHipster generated Docker-Compose configuration

## Usage

Launch all your infrastructure by running: `docker compose up -d`.

## Configured Docker services

### Service registry and configuration server:

- [Consul](http://localhost:8500)

### Applications and dependencies:

- KariaMain (gateway application)
- KariaMain's mysql database
- notification (microservice application)
- notification's mongodb database
- property (microservice application)
- property's mysql database

### Additional Services:

- Kafka
- Zookeeper
- [Keycloak server](http://localhost:9080)
