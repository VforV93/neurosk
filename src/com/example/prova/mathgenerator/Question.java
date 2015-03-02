package com.example.prova.mathgenerator;

import java.util.ArrayList;
import java.util.List;

public class Question {
	private List<QuestionElement> questionElements;

    public Question(int sizeOfQuestionElemets) {
        questionElements = new ArrayList<QuestionElement>(sizeOfQuestionElemets);
    }

    public void addElement(QuestionElement questionElement) {
        questionElements.add(questionElement);
    }

    public List<QuestionElement> getElements() {
        return questionElements;
    }

    public int size() {
        return questionElements.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (QuestionElement questionElement : questionElements) {
            sb.append(questionElement);
        }
        return sb.toString().trim();
    }
}
