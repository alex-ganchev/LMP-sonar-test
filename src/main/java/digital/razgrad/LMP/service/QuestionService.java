package digital.razgrad.LMP.service;

import digital.razgrad.LMP.entity.Answer;
import digital.razgrad.LMP.entity.Question;
import digital.razgrad.LMP.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    @Transactional
    public Question saveQuestionWithAnswers(Question question, Set<Answer> answers) {
        Question savedQuestion = questionRepository.save(question);
        int i = 1;
        for (Answer answer : answers) {
            if (!answer.getAnswer().isBlank()) {
                answer.setQuestion(savedQuestion);
                savedQuestion.setAnswerSet(answers);
                System.out.println(i++);
            }
//            answer.setQuestion(savedQuestion);
//            savedQuestion.setAnswerSet(answers);
        }
        return savedQuestion;
    }
}
