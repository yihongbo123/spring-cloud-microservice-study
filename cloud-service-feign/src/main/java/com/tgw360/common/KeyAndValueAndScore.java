package com.tgw360.common;

import java.io.Serializable;
import java.util.PriorityQueue;

public class KeyAndValueAndScore implements Serializable {
    private String key;
    private String value;
    private Double score;

    public KeyAndValueAndScore() {
    }

    public KeyAndValueAndScore(String key, String value, Double score) {
        this.key = key;
        this.value = value;
        this.score = score;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

    @Override
    public String toString() {
        return "KeyAndValueAndScore{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                ", score=" + score +
                '}';
    }
}
