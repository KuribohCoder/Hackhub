package it.unicam.cs.ids.hackhub.application.dto.mapper;

import it.unicam.cs.ids.hackhub.application.dto.response.EvaluationResponse;
import it.unicam.cs.ids.hackhub.domain.model.Evaluation;

public class EvaluationMapper {

    public static EvaluationResponse toResponse(Evaluation evaluation) {
        if (evaluation == null) {
            return null;
        }
        return new EvaluationResponse(
                evaluation.getId(),
                evaluation.getJudgeId(),
                evaluation.getSubmissionId(),
                evaluation.getScore(),
                evaluation.getFeedback()
        );
    }
}