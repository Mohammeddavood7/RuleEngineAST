package com.example.ruleengine.model;

import lombok.Data;

@Data
public class Rule {
    private Integer id;
    private String type;
    private String value;
    private Rule left;
    private Rule right;
	public Rule(Integer id, String type, String value, Rule left, Rule right) {
		super();
		this.id = id;
		this.type = type;
		this.value = value;
		this.left = left;
		this.right = right;
	}
	public Rule() {
		super();
		// TODO Auto-generated constructor stub
	}

}
