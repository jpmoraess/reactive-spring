package br.com.moraesit.fluxandmonotests;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxAndMonoTest {

    @Test
    public void fluxTest() {
        Flux<String> stringFlux = Flux.just("João", "Pedro", "Moraes")
                //.concatWith(Flux.error(new RuntimeException("Exception....")))
                .concatWith(Flux.just("After RuntimeException"))
                .log();

        stringFlux.subscribe(System.out::println,
                (ex) -> System.err.println("Exception is: " + ex),
                () -> System.out.println("Completed with successfully.."));
    }

    @Test
    public void fluxTestElementsWithoutError() {
        Flux<String> stringFlux = Flux.just("João", "Pedro", "Moraes")
                .log();

        StepVerifier.create(stringFlux)
                .expectNext("João")
                .expectNext("Pedro")
                .expectNext("Moraes")
                .verifyComplete();
    }

    @Test
    public void fluxTestElementsWithError() {
        Flux<String> stringFlux = Flux.just("João", "Pedro", "Moraes")
                .concatWith(Flux.error(new RuntimeException("Exception....")))
                .log();

        StepVerifier.create(stringFlux)
                .expectNext("João")
                .expectNext("Pedro")
                .expectNext("Moraes")
                //.expectError(RuntimeException.class)
                .expectErrorMessage("Exception....")
                .verify();
    }

    @Test
    public void fluxTestElementsCountWithError() {
        Flux<String> stringFlux = Flux.just("João", "Pedro", "Moraes")
                .concatWith(Flux.error(new RuntimeException("Exception....")))
                .log();

        StepVerifier.create(stringFlux)
                .expectNextCount(3)
                .expectErrorMessage("Exception....")
                .verify();
    }

    @Test
    public void fluxTestElementsWithError1() {
        Flux<String> stringFlux = Flux.just("João", "Pedro", "Moraes")
                .concatWith(Flux.error(new RuntimeException("Exception....")))
                .log();

        StepVerifier.create(stringFlux)
                .expectNext("João", "Pedro", "Moraes")
                .expectErrorMessage("Exception....")
                .verify();
    }
}
