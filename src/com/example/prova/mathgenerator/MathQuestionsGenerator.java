package com.example.prova.mathgenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MathQuestionsGenerator {
	 private static final int NUMBER_OF_QUESTIONS = 10;
	 private static final int MIN_QUESTION_ELEMENTS = 2;
	 private static final int MAX_QUESTION_ELEMENTS = 4;
	 private static final int MIN_QUESTION_ELEMENT_VALUE = 1;
	 private static final int MAX_QUESTION_ELEMENT_VALUE = 100;
	 private final Random randomGenerator = new Random();
	 private List<Question> randomQuestions;
	 
	 public MathQuestionsGenerator(){
		 this.randomQuestions = this.getGeneratedRandomQuestions();
		/* for (Question question : randomQuestions) {
	            System.out.println(question);
	        }*/
	}
	 
	 public List<Question> getAllQuestions()
	 {
		 return randomQuestions;
	 }
	 
	private List<Question> getGeneratedRandomQuestions() {
		List<Question> randomQuestions = new ArrayList<Question>(NUMBER_OF_QUESTIONS);
        for (int i = 0; i < NUMBER_OF_QUESTIONS; i++) {
            int randomQuestionElementsCapacity = this.getRandomQuestionElementsCapacity();
            Question question = new Question(randomQuestionElementsCapacity);
            for (int j = 0; j < randomQuestionElementsCapacity; j++) {
                boolean isLastIteration = j + 1 == randomQuestionElementsCapacity;

                QuestionElement questionElement = new QuestionElement();
                questionElement.setValue(getRandomQuestionElementValue());
                questionElement.setOperator(isLastIteration ? null : Operator.values()[randomGenerator.nextInt(Operator.values().length)]);

                question.addElement(questionElement);
            }
            randomQuestions.add(question);
        }
        
        return randomQuestions;
	}
	
	
	
	private int getRandomQuestionElementsCapacity() {
        return getRandomIntegerFromRange(MIN_QUESTION_ELEMENTS, MAX_QUESTION_ELEMENTS);
    }

    private int getRandomQuestionElementValue() {
        return getRandomIntegerFromRange(MIN_QUESTION_ELEMENT_VALUE, MAX_QUESTION_ELEMENT_VALUE);
    }

    private int getRandomIntegerFromRange(int min, int max) {
        return randomGenerator.nextInt(max - min + 1) + min;
    }
}
