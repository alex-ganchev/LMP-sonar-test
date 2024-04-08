package digital.razgrad.LMP.dto;

import digital.razgrad.LMP.constant.AnswerType;
import digital.razgrad.LMP.entity.Answer;
import digital.razgrad.LMP.entity.Lecture;


import java.util.List;
import java.util.Set;

public class QuestionRegistrationDTO {
    private Lecture lecture;
    private String question;
    private AnswerType answerType;
    private List<Answer> answerSet;
    private boolean isCorrect;
    private int points;

    public Lecture getLecture() {
        return lecture;
    }

    public void setLecture(Lecture lecture) {
        this.lecture = lecture;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public AnswerType getAnswerType() {
        return answerType;
    }

    public void setAnswerType(AnswerType answerType) {
        this.answerType = answerType;
    }

    public List<Answer> getAnswerSet() {
        return answerSet;
    }

    public void setAnswerSet(List<Answer> answerSet) {
        this.answerSet = answerSet;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
