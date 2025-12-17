# Linked-in Clone

## Table of Contents
- [Overview](#overview)
- [Services](#services)
- [Other Components](#other-components)
- [How to Run](#how-to-run)
  - [pgAdmin Setup](#note-about-pgadmin)
  - [Service Startup Order](#service-startup-order)
- [Access the Application](#access-the-application)
- [TODO](#todo)

## Overview
This project is a LinkedIn-style backend system built using a microservices architecture. Each core feature is implemented as an independent service, and each service owns its own database. Microservices communicate with each other via `OpenFeign` for synchronous HTTP calls,
and use `Kafka` for asynchronous event-based communication (Java 21, Maven).

<p align="center">
  <img src="./system-diagram.png" width="100%"/>
</p>

## Services

- **API Gateway**
  - A single entry point for all HTTP requests
  - Routes traffic to internal services

- **User Service**
  - Handles user-related functionality
  - Authentication implemented using JWT

- **Post Service**
  - Handles creating and interacting with posts
  - Uses the Transactional Outbox pattern with Debezium to publish events reliably

- **Connection Service**
  - Manages user connections (first-degree, second-degree, etc.)
  - Designed for graph-style relationship queries

- **Notification Service**
  - Listens to Kafka events from other services
  - Creates notifications for users (e.g. new post, new connection)

## Other Components
  
- **Discovery Server (Eureka)**
  - Service registry
  - Allows services to discover each other dynamically

- **Config Server**
  - Central place for configuration
  - Enable change config wiothout restart
 
## How to Run

Start all infrastructure services using Docker:
```
docker compose up -d
```

### Note about pgAdmin

On first run, each PostgreSQL container must be registered as a server in pgAdmin.

Example: `post_db`
- **General Tab**
  - **Name**: `post_db`

- **Connection Tab**
  - **Host name/address**: `post_db`
  - **Port**: `5432`
  - **Maintenance database**: `post_db`
  - **Username**: `postgres`
  - **Password**: `changeme`
  - Enable **Save password**

- Repeat the same steps for:
  - `user_db`
  - `notification_db`

### Service Startup Order

Start services in the following order:

1. config-server

2. discovery-server

3. api-gateway

4. All other services
      - user-service
      - post-service
      - connection-service
      - notification-service

## Access the application

- API Gateway: http://localhost:8080
- Eureka Dashboard: http://localhost:8761
- PgAdmin: http://localhost:5050
- Neo4j UI: http://localhost:7474
- Kafbat UI: http://localhost:9091
- Config: https://github.com/immasei/linkedin-config-server

## TODO
- Distributed Tracing using Zipkin & Micrometer
- Centralised logging (ELK)
- Authorization
- Frontend
- Additional features and improvements
