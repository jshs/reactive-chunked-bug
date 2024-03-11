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

## Note

The test passes if Undertow is used instead of Tomcat.
