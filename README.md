1.http://localhost:8080/api/rules
=================================
{
    "type": "operand",
    "left": null,
    "right": null,
    "value": "age > 30"
}



================================
2.http://localhost:8080/api/rules
==================================




3.http://localhost:8080/api/rules/evaluate
===========================================
{
    "rule": {
        "type": "operator",
        "left": {
            "type": "operand",
            "value": "age > 30"
        },
        "right": {
            "type": "operator",
            "left": {
                "type": "operand",
                "value": "department = 'Sales'"
            },
            "right": {
                "type": "operand",
                "value": "salary > 50000"
            },
            "value": "AND"
        },
        "value": "AND"
    },
    "data": {
        "age": 35,
        "department": "Sales",
        "salary": 60000
    }
}


4.http://localhost:8080/api/rules/create-from-string
=========================================================================
{
  "type": "operator",
  "value": "AND",
  "left": {
    "type": "operand",
    "value": "age > 30"
  },
  "right": {
    "type": "operand",
    "value": "department = 'Sales'"
  }
}



5.http://localhost:8080/api/rules/combine
================================================================
[
  "age > 30 AND department = 'Sales'",
  "salary > 50000"
]
