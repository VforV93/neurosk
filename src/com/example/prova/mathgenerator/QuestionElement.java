package com.example.prova.mathgenerator;

public class QuestionElement {
	private int value;
    private Operator operator;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    @Override
    public String toString() {
        return value + (operator == null ? "" : " " + operator.getDisplayValue()) + " ";
    }
}
