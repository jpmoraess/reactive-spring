package br.com.moraesit.fluxandmonotests;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

public class FluxAndMonoFilterTest {

    List<String> names = Arrays.asList("João", "Pedro", "Andressa", "Cristina");

    @Test
    public void filterTest() {
        Flux<String> namesFlux = Flux.fromIterable(names)
                .filter(s -> s.startsWith("P"))
                .log();

        StepVerifier.create(namesFlux)
                .expectNext("Pedro")
                .verifyComplete();
    }

    @Test
    public void filterTestLength() {
        Flux<String> namesFlux = Flux.fromIterable(names)
                .filter(s -> s.length() == 8)
                .log();

        StepVerifier.create(namesFlux)
                .expectNext("Andressa", "Cristina")
                .verifyComplete();
    }
}
