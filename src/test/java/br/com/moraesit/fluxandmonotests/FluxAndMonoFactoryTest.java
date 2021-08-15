package br.com.moraesit.fluxandmonotests;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class FluxAndMonoFactoryTest {

    List<String> names = Arrays.asList("João", "Pedro", "Andressa", "Cristina");
    String[] namesArray = new String[]{"João", "Pedro", "Andressa", "Cristina"};

    @Test
    public void fluxUsingIterable() {
        Flux<String> namesFlux = Flux.fromIterable(names)
                .log();

        StepVerifier.create(namesFlux)
                .expectNext("João", "Pedro", "Andressa", "Cristina")
                .verifyComplete();
    }

    @Test
    public void fluxUsingArray() {
        Flux<String> namesArrayFlux = Flux.fromArray(namesArray);

        StepVerifier.create(namesArrayFlux)
                .expectNext("João", "Pedro", "Andressa", "Cristina")
                .verifyComplete();
    }

    @Test
    public void fluxUsingStream() {
        Flux<String> namesStreamFlux = Flux.fromStream(names.stream());

        StepVerifier.create(namesStreamFlux)
                .expectNext("João", "Pedro", "Andressa", "Cristina")
                .verifyComplete();
    }

    @Test
    public void fluxUsingRange() {
        Flux<Integer> integerFlux = Flux.range(1, 8);

        StepVerifier.create(integerFlux)
                .expectNext(1, 2, 3, 4, 5, 6, 7, 8)
                .verifyComplete();
    }

    /**
     * MONO
     */

    @Test
    public void monoUsingJustOrEmpty() {
        Mono<String> mono = Mono.justOrEmpty(null);

        StepVerifier.create(mono.log())
                .verifyComplete();
    }

    @Test
    public void monoUsingSupplier() {
        Supplier<String> stringSupplier = () -> "João";

        Mono<String> stringMono = Mono.fromSupplier(stringSupplier);

        System.out.println(stringSupplier.get());

        StepVerifier.create(stringMono)
                .expectNext("João")
                .verifyComplete();
    }
}
