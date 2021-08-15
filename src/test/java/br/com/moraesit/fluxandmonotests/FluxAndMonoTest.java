package br.com.moraesit.fluxandmonotests;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

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
}
