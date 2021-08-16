package br.com.moraesit.fluxandmonotests;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

public class FluxAndMonoWithTimeTest {

    @Test
    public void infiniteSequence() throws InterruptedException {
        Flux<Long> infiniteFlux = Flux.interval(Duration.ofMillis(300))
                .log();

        infiniteFlux.subscribe((element) -> System.out.println("Value is : " + element));

        Thread.sleep(3000);
    }

    @Test
    public void finiteSequenceTest() {
        Flux<Long> finiteFlux = Flux.interval(Duration.ofMillis(300))
                .take(3)
                .log();

        StepVerifier.create(finiteFlux)
                .expectSubscription()
                .expectNext(0L, 1L, 2L)
                .verifyComplete();
    }

    @Test
    public void finiteSequenceMap() {
        Flux<Integer> finiteFlux = Flux.interval(Duration.ofMillis(300))
                .map(v -> v.intValue())
                .take(3)
                .log();

        StepVerifier.create(finiteFlux)
                .expectSubscription()
                .expectNext(0, 1, 2)
                .verifyComplete();
    }

    @Test
    public void finiteSequenceMapWithDelay() {
        Flux<Integer> finiteFlux = Flux.interval(Duration.ofMillis(300))
                .delayElements(Duration.ofSeconds(1))
                .map(v -> v.intValue())
                .take(3)
                .log();

        StepVerifier.create(finiteFlux)
                .expectSubscription()
                .expectNext(0, 1, 2)
                .verifyComplete();
    }
}
