package digital.razgrad.LMP.dto;

import digital.razgrad.LMP.constant.AnswerType;
import digital.razgrad.LMP.entity.Answer;
import digital.razgrad.LMP.entity.Lecture;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


import java.util.List;
import java.util.Set;

public class QuestionRegistrationDTO {
    @NotNull
    private Lecture lecture;
    @Size(min = 5, message = "Минимален брой символи 5!")
    private String question;
    private AnswerType answerType;
    private List<Answer> answerList;
    private boolean isCorrect;
    @Min(value = 1, message = "Въведете брой точки!")
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

    public List<Answer> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<Answer> answerList) {
        this.answerList = answerList;
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
