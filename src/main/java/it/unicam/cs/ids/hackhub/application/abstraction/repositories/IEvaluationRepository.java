package it.unicam.cs.ids.hackhub.application.abstraction.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.unicam.cs.ids.hackhub.domain.model.Evaluation;

public interface IEvaluationRepository extends JpaRepository<Evaluation, Long> {
    List<Evaluation> findByJudgeId(Long judgeId);
}