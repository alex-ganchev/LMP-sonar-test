package digital.razgrad.LMP.mapper;

import digital.razgrad.LMP.constant.AnswerType;
import digital.razgrad.LMP.dto.QuestionRegistrationDTO;
import digital.razgrad.LMP.entity.Answer;
import digital.razgrad.LMP.entity.Lecture;
import digital.razgrad.LMP.entity.Question;
import digital.razgrad.LMP.repository.LectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class QuestionRegistrationMapper {
    @Autowired
    LectureRepository lectureRepository;
    public Question toEntityQuestion(QuestionRegistrationDTO questionRegistrationDTO){
        Question question = new Question();
        question.setLecture(lectureRepository.findById(questionRegistrationDTO.getLecture().getId()).get());
        question.setQuestion(questionRegistrationDTO.getQuestion());
        question.setAnswerType(questionRegistrationDTO.getAnswerType());
        question.setPoints(questionRegistrationDTO.getPoints());
        //question.setAnswerSet(new HashSet<>(questionRegistrationDTO.getAnswerSet()));
        return question;
    }

}
