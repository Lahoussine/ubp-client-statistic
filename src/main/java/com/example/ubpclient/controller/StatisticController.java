package com.example.ubpclient.controller;

import com.example.ubpclient.dto.StatisticResult;
import com.example.ubpclient.service.StatisticService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController

@RequestMapping("/v1/statistic/")
public class StatisticController {

    private WebClient webClient;
    private StatisticService statisticService;

    public StatisticController(WebClient.Builder webClientBuilder, StatisticService statisticService) {
        //Normalement on aurait du creer une config pour parametrer les urls mais ca depasse le cadre de l'exercice il me semble
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080").build();
        this.statisticService=statisticService;
    }
/*
    public Mono<Details> someRestCall(String name) {
        return this.webClient.get().url("/{name}/details", name)
                .retrieve().bodyToMono(Details.class);
    }*/



    @GetMapping("/population/{size}")
    public ResponseEntity<StatisticResult> getStatisticForPopulation(@PathVariable int size) throws Exception {
        //on appelle l'url qui retourne les nombres aléatoires en précisant la taille
        List<Double> numberToProcess = this.webClient.get().uri("/v1/random/numbers/{size}", size).exchange()
                .block()

                .bodyToMono(List.class)
                .block();


        double mean = statisticService.mean(numberToProcess);
        double standardDeviation = statisticService.standardDeviation(numberToProcess,true);

        StatisticResult statResult = new StatisticResult(mean, standardDeviation,numberToProcess);

        //si pas d'erreur on retourne la réponse avec le http code OK 200
        return ResponseEntity.ok(statResult);

    }

    @GetMapping("/sample/{size}")
    public ResponseEntity<StatisticResult> getStatisticForSamples(@PathVariable int size) throws Exception {
        //on appelle l'url qui retourne les nombres aléatoires en précisant la taille
        List<Double> numberToProcess = this.webClient.get().uri("/v1/random/numbers/{size}", size).exchange()
                .block()

                .bodyToMono(List.class)
                .block();


        double mean = statisticService.mean(numberToProcess);
        double standardDeviation = statisticService.standardDeviation(numberToProcess,false);

        StatisticResult statResult = new StatisticResult(mean, standardDeviation,numberToProcess);

        //si pas d'erreur on retourne la réponse avec le http code OK 200
        return ResponseEntity.ok(statResult);

    }

}
