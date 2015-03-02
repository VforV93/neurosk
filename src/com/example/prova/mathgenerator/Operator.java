package com.example.prova.mathgenerator;

public enum Operator {
	 PLUS("+"), MINUS("-"), MULTIPLIER("*"), DIVIDER("/");
	    private String displayValue;

	    private Operator(String displayValue) {
	        this.displayValue = displayValue;
	    }

	    public String getDisplayValue() {
	        return displayValue;
	    }
}
