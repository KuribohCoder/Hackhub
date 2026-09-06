package it.unicam.cs.ids.hackhub.application.abstraction.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.unicam.cs.ids.hackhub.domain.model.Submission;

public interface ISubmissionRepository extends JpaRepository<Submission, Long> {
    List<Submission> findByHackathonId(Long hackathonId);
}