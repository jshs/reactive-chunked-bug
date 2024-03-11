package ch.tie.tieschj;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class ReproducerController {

  @PostMapping(path = "/length", consumes = "*/*", produces = "application/json")
  public Mono<Integer> length(ServerHttpRequest request) {
    return request.getBody().map(DataBuffer::readableByteCount).reduce(0, Integer::sum);
  }
}
