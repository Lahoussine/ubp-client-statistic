package com.example.ubpclient.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StatisticResult {

    private double mean;
    private double standardDeviation;
    private List<Double> doubleSet;

    public StatisticResult(double mean, double standardDeviation, List<Double> doubleSet) {
        this.mean = mean;
        this.standardDeviation = standardDeviation;
        this.doubleSet = doubleSet;
    }
}
