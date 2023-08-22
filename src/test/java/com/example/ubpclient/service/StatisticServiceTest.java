package com.example.ubpclient.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StatisticServiceTest {

    private StatisticService statisticService;
    private List<Double> numberToProcess;
    @BeforeEach
    void setup(){
        statisticService = new StatisticService();
        //Given on utilise le meme data set pour tous les tests pour simplifier
        numberToProcess = Stream.of(2.0, 4.0, 6.0, 8.0).collect(Collectors.toList());

    }
    @Test
    void testMean(){
        //when test de la moyenne
        double mean = statisticService.mean(numberToProcess);
        Assertions.assertEquals(5.0, mean);

    }

    @Test
    void testStandardDeviationPopulation(){
        //when test de l'ecart-type
        // je ne me suis pas soucié d arrondi ici a 2 digits apres la virgule mais on aurait pu
        double standardDeviation = statisticService.standardDeviation(numberToProcess, true);
        Assertions.assertEquals(2.581988897471611, standardDeviation);

    }
    @Test
    void testStandardDeviationEchantillon(){
        //when test de l'ecart-type
        // je ne me suis pas soucié d arrondi ici a 2 digits apres la virgule mais on aurait pu
        double standardDeviation = statisticService.standardDeviation(numberToProcess, false);
        Assertions.assertEquals(2.581988897471611, standardDeviation);

    }
}
