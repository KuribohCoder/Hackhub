package it.unicam.cs.ids.hackhub.application.services;

import it.unicam.cs.ids.hackhub.application.abstraction.services.ISubmissionService;
import it.unicam.cs.ids.hackhub.application.abstraction.repositories.ISubmissionRepository;
import it.unicam.cs.ids.hackhub.application.dto.request.UpdateSubmissionRequest;
import it.unicam.cs.ids.hackhub.domain.model.Submission;
import it.unicam.cs.ids.hackhub.domain.enums.HackathonStatus;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SubmissionService implements ISubmissionService {

    private final ISubmissionRepository submissionRepository;

    public SubmissionService(ISubmissionRepository submissionRepository) {
        this.submissionRepository = submissionRepository;
    }

    @Override
    @Transactional
    public Submission updateSubmission(Long submissionId, UpdateSubmissionRequest request) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Sottomissione non trovata con id: " + submissionId));

        if (submission.getHackathon().getStatus() != HackathonStatus.IN_PROGRESS) {
            throw new IllegalStateException(
                    "Impossibile aggiornare la sottomissione: l'hackathon non è in corso.");
        }

        submission.setTitle(request.title());
        submission.setDescription(request.description());
        submission.setRepositoryUrl(request.repositoryUrl());

        return submissionRepository.save(submission);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Submission> getSubmissionsByHackathon(Long hackathonId) {
        return submissionRepository.findByHackathonId(hackathonId);
    }
}