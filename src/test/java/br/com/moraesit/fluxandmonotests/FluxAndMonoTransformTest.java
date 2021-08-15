package br.com.moraesit.fluxandmonotests;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

public class FluxAndMonoTransformTest {

    List<String> names = Arrays.asList("João", "Pedro", "Andressa", "Cristina");

    @Test
    public void transformUsingMap() {
        Flux<String> stringFlux = Flux.fromIterable(names)
                .map(s -> s.concat("Map"));

        StepVerifier.create(stringFlux)
                .expectNext("JoãoMap", "PedroMap", "AndressaMap", "CristinaMap")
                .verifyComplete();
    }
}
