# Helpers

```
http://localhost:8085/user-service/default
```

```
curl -X POST http://localhost:9090/connectors \
  -H "Content-Type: application/json" \
  -d @post-outbox-connector.json
```

```
curl http://localhost:9090/connectors
```

```
curl http://localhost:9090/connectors/post-outbox-connector/status
```

```
curl -X DELETE http://localhost:9090/connectors/post-outbox-connector
```

```
docker exec -it linked-in-kafka-1 kafka-console-consumer \
  --bootstrap-server kafka:9092 \
  --property print.topic=true \
  --property print.key=true \
  --property print.value=true \
  --from-beginning \
  --whitelist '.*'
```

```
http://localhost:8080/api/v1/users/auth/signup
```


```
http://localhost:8080/api/v1/users/auth/login
```

```
http://localhost:8080/api/v1/posts/core
```

```
export JAVA_HOME=$(/usr/libexec/java_home -v 21)
export PATH="$JAVA_HOME/bin:$PATH"

```
```
http://localhost:8080/api/v1/posts/likes?type=POST&id=52
```

```
http://localhost:8080/api/v1/connections/core/request/3
```

```
http://localhost:8080/api/v1/connections/core/accept/4
```