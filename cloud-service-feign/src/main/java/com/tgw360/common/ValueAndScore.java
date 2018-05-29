package com.tgw360.common;

import java.io.Serializable;

public class ValueAndScore implements Serializable{
    private String value;
    private Double score;

    public ValueAndScore() {
    }

    public ValueAndScore(String value, Double score) {
        this.value = value;
        this.score = score;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}
