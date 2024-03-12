# Bug reproducer

Tomcat + reactive servlet + chunked transfer encoding.

## Run

```shell
gradle test
```

## Expected outcome

The test passes.

## Actual outcome

The test fails with a `reactor.netty.http.client.PrematureCloseException`.
The server log reports `org.apache.coyote.BadRequestException: Invalid chunk header`.

## Note

The test passes if Undertow is used instead of Tomcat.
