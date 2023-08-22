package com.example.ubpclient.controller;

import com.example.ubpclient.dto.StatisticResult;
import com.example.ubpclient.service.StatisticService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

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


    // ici j'ai mis le handling d'une exception generique sur un endpoint on retourne par défaut BAD REQUEST + message exception
    // Il est plus propre de le mettre dans une classe dédiée comme RestResponseEntityExceptionHandler mais je ne sais pas pourquoi elle semble ne pas etre prise en compte lorsque l'erreur arrive
    // Pour l'exercice je l'ai mis dans le controller meme si ce n'est pas ce que j'aurais fait dans une situation réelle
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleNoSuchElementFoundException(
            Exception exception
    ) {
        return ResponseEntity.badRequest()              //.status(HttpStatus.I_AM_A_TEAPOT)
                .body(exception.getMessage());
    }

}
