package com.example.properties.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * RandomConfig
 *
 * @author star
 **/
@Component
public class RandomConfig {

    @Value("${rand.stringValue}")
    private String stringValue;

    @Value("${rand.intNumber}")
    private Integer intNumber;

    @Value("${rand.longNumber}")
    private Long longNumber;

    @Value("${rand.number}")
    private Integer number;

    @Value("${rand.rangeNumber}")
    private Integer rangeNumber;

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public Integer getIntNumber() {
        return intNumber;
    }

    public void setIntNumber(Integer intNumber) {
        this.intNumber = intNumber;
    }

    public Long getLongNumber() {
        return longNumber;
    }

    public void setLongNumber(Long longNumber) {
        this.longNumber = longNumber;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getRangeNumber() {
        return rangeNumber;
    }

    public void setRangeNumber(Integer rangeNumber) {
        this.rangeNumber = rangeNumber;
    }
}
