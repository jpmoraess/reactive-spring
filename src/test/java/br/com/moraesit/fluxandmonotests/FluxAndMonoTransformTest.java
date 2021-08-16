package br.com.moraesit.fluxandmonotests;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static reactor.core.scheduler.Schedulers.parallel;

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

    @Test
    public void transformUsingFlatMap() {
        Flux<String> stringFlux = Flux.fromIterable(Arrays.asList("A", "B", "C", "D"))
                .flatMap(s -> {
                    return Flux.fromIterable(convertToList(s)).log(); // A -> List[A, newValue]
                }); // db or external service call that returns a Flux ->  s -> Flux<String>

        StepVerifier.create(stringFlux)
                .expectNextCount(8)
                .verifyComplete();
    }

    @Test
    public void transformUsingFlatMapUsingParallel() {
        Flux<String> stringFlux = Flux.fromIterable(Arrays.asList("A", "B", "C", "D"))
                .window(2) //Flux<Flux<String>> -> (A,B), (C,D)
                .flatMap((s) ->
                        s.map(this::convertToList).subscribeOn(parallel())) // Flux<List<String>>
                .flatMap(s -> Flux.fromIterable(s)) // Flux<String>
                .log();

        StepVerifier.create(stringFlux)
                .expectNextCount(8)
                .verifyComplete();
    }

    @Test
    public void transformUsingFlatMapUsingParallelMaintainOrder() {
        Flux<String> stringFlux = Flux.fromIterable(Arrays.asList("A", "B", "C", "D"))
                .window(2) //Flux<Flux<String>> -> (A,B), (C,D)
//                .concatMap((s) ->
//                        s.map(this::convertToList).subscribeOn(parallel())) // Flux<List<String>>

                .flatMapSequential((s) ->
                        s.map(this::convertToList).subscribeOn(parallel()))
                .flatMap(s -> Flux.fromIterable(s)) // Flux<String>
                .log();

        StepVerifier.create(stringFlux)
                .expectNextCount(8)
                .verifyComplete();
    }

    private List<String> convertToList(String s) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Arrays.asList(s, "newValue");
    }
}
