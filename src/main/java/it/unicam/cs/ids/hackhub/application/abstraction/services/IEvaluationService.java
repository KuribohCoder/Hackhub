package it.unicam.cs.ids.hackhub.application.abstraction.services;

import java.util.List;

import it.unicam.cs.ids.hackhub.domain.model.Evaluation;

public interface IEvaluationService {
    List<Evaluation> getEvaluationsByJudge(Long judgeId);
}