package com.example.ruleengine.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ruleengine.model.Node;
import com.example.ruleengine.repository.RuleRepository;

@Service
public class RuleService {

    @Autowired
    private RuleRepository ruleRepository; 

    public Node saveRule(Node rule) {
        return ruleRepository.save(rule); 
    }

    public List<Node> getAllRules() {
        return ruleRepository.findAll(); 
    }

    // 1. Create a rule by parsing the rule string and returning an AST (Node object)
    @SuppressWarnings("unused")
	public Node createRule(String ruleString) {
        Node left = null, right = null;
        String operator = null;

        if (ruleString.contains("AND")) {
            operator = "AND";
            String[] parts = ruleString.split("AND");
            left = createConditionNode(parts[0].trim());
            right = createConditionNode(parts[1].trim());
        } else if (ruleString.contains("OR")) {
            operator = "OR";
            String[] parts = ruleString.split("OR");
            left = createConditionNode(parts[0].trim());
            right = createConditionNode(parts[1].trim());
        } else {
            // Single condition, no operator
            return createConditionNode(ruleString.trim());
        }

        // Ensure operator is not null
        if (operator == null) {
            System.err.println("Error: Operator is null");
            throw new IllegalArgumentException("Operator cannot be null");
        }

        return new Node(null, "operator", left, right, operator);
    }
    
    public Node createRule(Node ruleNode) {
        Node left = null, right = null;
        String operator = ruleNode.getValue();

        if (operator.equals("AND") || operator.equals("OR")) {
            left = ruleNode.getLeft();
            right = ruleNode.getRight();
        } else {
            // Single condition
            return createConditionNode(ruleNode.getValue());
        }

        return new Node(null, "operator", left, right, operator);
    }

    // Helper method to create a condition node from a condition string
    private Node createConditionNode(String condition) {
        return new Node(null, "operand", null, null, condition);
    }

    // 2. Combine multiple rules into a single AST
    public Node combineRules(List<String> ruleStrings)
    {
        Node root = null;

        for (String ruleString : ruleStrings)
        {
            Node newRule = createRule(ruleString); 
            root = (root == null) ? newRule : combineNodes(root, newRule);
        }

        return root;
    }

    // Helper method to combine two nodes (AND as default)
    private Node combineNodes(Node existing, Node newNode)
    {
        return new Node(null, "operator", existing, newNode, "AND"); // You can change this logic based on your strategy
    }

    // 3. Evaluate a rule against provided data
    public boolean evaluateRule(Node rule, Map<String, Object> data) {
        if (rule == null) {
            return false; // Return false if the rule is null
        }

        // Debug: Print the node details
        System.out.println("Evaluating Node - Type: " + rule.getType() + ", Value: " + rule.getValue());

        // If the node type is "operand", evaluate the condition
        if (rule.getType().equals("operand")) {
            String condition = rule.getValue();
            return evaluateCondition(condition, data);
        } 
        // If the node type is "operator", evaluate left and right child nodes
        else if (rule.getType().equals("operator")) {
            boolean leftEval = evaluateRule(rule.getLeft(), data);
            boolean rightEval = evaluateRule(rule.getRight(), data);
            
            // Get the operator's value and check for null
            String operatorValue = rule.getValue();
            if (operatorValue == null) {
                System.err.println("Error: Operator value is null at this node");
                throw new IllegalArgumentException("Operator value cannot be null");
            }

            // Evaluate based on operator type
            switch (operatorValue) {
                case "AND":
                    return leftEval && rightEval;
                case "OR":
                    return leftEval || rightEval;
                default:
                    throw new IllegalArgumentException("Unsupported operator: " + operatorValue);
            }
        }

        return false; // Default case if none of the above conditions are met
    }



    
    
    
 // Evaluate a condition against provided data
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean evaluateCondition(String condition, Map<String, Object> data) {
        // Example of evaluating simple conditions
        String[] parts = condition.split(" ");
        String left = parts[0]; // e.g., age
        String operator = parts[1]; // e.g., >
        String right = parts[2]; // e.g., 30

        Object leftValue = data.get(left);
        Object rightValue = parseValue(right);

        switch (operator) {
            case ">":
                return ((Comparable) leftValue).compareTo(rightValue) > 0;
            case "<":
                return ((Comparable) leftValue).compareTo(rightValue) < 0;
            case "=":
                return leftValue.equals(rightValue);
            // Add more operators as needed
            default:
                throw new IllegalArgumentException("Unsupported operator: " + operator);
        }
    }
    
    

    // Helper method to parse values
    private Object parseValue(String value) {
        if (value.startsWith("'") && value.endsWith("'")) {
            return value.substring(1, value.length() - 1); // Return as string without quotes
        } else {
            try {
                return Integer.parseInt(value); // Try parsing as integer
            } catch (NumberFormatException e) {
                return value; // Return as string if parsing fails
            }
        }
    }

}













