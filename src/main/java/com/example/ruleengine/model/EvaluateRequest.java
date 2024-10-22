package com.example.ruleengine.model;


import java.util.Map;

public class EvaluateRequest {
    private Node rule;
    private Map<String, Object> data;

    // Getters and setters
    public Node getRule() {
        return rule;
    }

    public void setRule(Node rule) {
        this.rule = rule;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
