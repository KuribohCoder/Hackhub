package it.unicam.cs.ids.hackhub.application.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unicam.cs.ids.hackhub.application.abstraction.repositories.IEvaluationRepository;
import it.unicam.cs.ids.hackhub.application.abstraction.services.IEvaluationService;
import it.unicam.cs.ids.hackhub.domain.model.Evaluation;

@Service
public class EvaluationService implements IEvaluationService {

    private final IEvaluationRepository evaluationRepository;

    public EvaluationService(IEvaluationRepository evaluationRepository) {
        this.evaluationRepository = evaluationRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Evaluation> getEvaluationsByJudge(Long judgeId) {
        return evaluationRepository.findByJudgeId(judgeId);
    }
}