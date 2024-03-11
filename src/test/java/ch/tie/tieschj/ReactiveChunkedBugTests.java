package ch.tie.tieschj;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.Executor;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReactiveChunkedBugTests {

  private static final int LENGTH = 1_000_000;

  @Autowired
  @Qualifier("taskExecutor")
  private Executor executor;

  @LocalServerPort
  private int port;

  @Test
  void test() {
    Integer result = WebClient.builder()
        .baseUrl("http://localhost:" + port)
        .build()
        .post()
        .uri("/length")
        .body(BodyInserters.fromOutputStream(this::writeBody, executor))
        .retrieve()
        .bodyToMono(Integer.class)
        .block();
    assertEquals(LENGTH, result);
  }

  private void writeBody(OutputStream output) {
    try {
      for (int i = 0; i < LENGTH; ++i) {
        output.write(65);
        output.flush();
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
