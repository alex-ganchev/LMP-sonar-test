package digital.razgrad.LMP.mapper;

import digital.razgrad.LMP.dto.QuestionRegistrationDTO;
import digital.razgrad.LMP.entity.Question;
import digital.razgrad.LMP.repository.LectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuestionRegistrationMapper {

    private LectureRepository lectureRepository;

    @Autowired
    private void setLectureRepository(LectureRepository lectureRepository) {
        this.lectureRepository = lectureRepository;
    }

    public Question toEntityQuestion(QuestionRegistrationDTO questionRegistrationDTO) {
        Question question = new Question();
        question.setLecture(lectureRepository.findById(questionRegistrationDTO.getLecture().getId()).get());
        question.setQuestion(questionRegistrationDTO.getQuestion());
        question.setAnswerType(questionRegistrationDTO.getAnswerType());
        question.setPoints(questionRegistrationDTO.getPoints());

        return question;
    }

    public QuestionRegistrationDTO toQuestionRegistrationDTO(Question question) {
        QuestionRegistrationDTO questionRegistrationDTO = new QuestionRegistrationDTO();
        questionRegistrationDTO.setLecture(question.getLecture());
        questionRegistrationDTO.setAnswerList(question.getAnswerList());
        questionRegistrationDTO.setPoints(question.getPoints());
        questionRegistrationDTO.setQuestion(question.getQuestion());
        questionRegistrationDTO.setAnswerType(question.getAnswerType());

        return questionRegistrationDTO;
    }

}
