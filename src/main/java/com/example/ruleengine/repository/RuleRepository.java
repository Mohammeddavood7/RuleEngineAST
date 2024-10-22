package com.example.ruleengine.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ruleengine.model.Node;

public interface RuleRepository extends JpaRepository<Node, Long> 
{
    // Additional query methods can be defined here
}
