package com.example.ubpclient.service;

import org.springframework.stereotype.Service;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatisticService {
    public double mean(List<Double> listDouble) {
        DoubleSummaryStatistics stats = listDouble.stream().collect(Collectors.summarizingDouble(Double::doubleValue));
        return stats.getAverage();

    }

    public double standardDeviation(List<Double> listDouble, boolean population){
        double mean = mean(listDouble);
        Double variance = listDouble.stream().map(value -> Math.pow(value - mean, 2)).reduce(0.0, Double::sum);
        //Selon la definition de l'ecart type, il faut diviser par N pour une population et par N-1 pour un echantillion
        if(population){
            return Math.sqrt(variance/((double)listDouble.size()));
        }
        return Math.sqrt(variance/((double)listDouble.size()-1.0));
    }
}
