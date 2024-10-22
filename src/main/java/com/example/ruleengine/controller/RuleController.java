package com.example.ruleengine.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ruleengine.model.EvaluateRequest;
import com.example.ruleengine.model.Node;
import com.example.ruleengine.service.RuleService;

@RestController
@RequestMapping("/api/rules")
public class RuleController {

    @Autowired
    private RuleService ruleService;

    // Endpoint to create a rule
    @PostMapping
    public Node createRule(@RequestBody Node rule) {
        return ruleService.saveRule(rule); 
    }

    // Endpoint to retrieve all rules
    @GetMapping
    public List<Node> getAllRules() {
        return ruleService.getAllRules(); // Ensure this method is implemented in RuleService
    }
 
    @PostMapping("/evaluate")
    public boolean evaluateRule(@RequestBody EvaluateRequest request) {
        Node rule = request.getRule();
        Map<String, Object> data = request.getData();
        return ruleService.evaluateRule(rule, data);
    }

    

  

    // New endpoint to create a rule from a rule string
    @PostMapping("/create-from-string")
    public ResponseEntity<Node> createRuleFromString(@RequestBody Node ruleNode) {
        try {
            Node createdNode = ruleService.createRule(ruleNode);
            return ResponseEntity.ok(createdNode);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


    // New endpoint to combine multiple rules
    @PostMapping("/combine")
    public Node combineRules(@RequestBody List<String> ruleStrings) {
        return ruleService.combineRules(ruleStrings);
    }
}
