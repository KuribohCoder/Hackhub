package it.unicam.cs.ids.hackhub.presentation.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.unicam.cs.ids.hackhub.application.abstraction.services.IEvaluationService;
import it.unicam.cs.ids.hackhub.application.dto.mapper.EvaluationMapper;
import it.unicam.cs.ids.hackhub.application.dto.response.EvaluationResponse;
import it.unicam.cs.ids.hackhub.domain.model.Evaluation;

@RestController
@RequestMapping("/api/evaluations")
public class EvaluationController {

    private final IEvaluationService evaluationService;

    public EvaluationController(IEvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }

    @GetMapping("/judge/{judgeId}")
    public ResponseEntity<List<EvaluationResponse>> getEvaluationsByJudge(
            @PathVariable Long judgeId) {
        List<Evaluation> evaluations = evaluationService.getEvaluationsByJudge(judgeId);
        List<EvaluationResponse> responseList = new ArrayList<>(evaluations.size());

        for (Evaluation evaluation : evaluations) {
            responseList.add(EvaluationMapper.toResponse(evaluation));
        }

        return ResponseEntity.ok(responseList);
    }
}