package it.unicam.cs.ids.hackhub.application.abstraction.services;

import it.unicam.cs.ids.hackhub.application.dto.request.UpdateSubmissionRequest;
import it.unicam.cs.ids.hackhub.domain.model.Submission;

import java.util.List;

public interface ISubmissionService {
    Submission updateSubmission(Long submissionId, UpdateSubmissionRequest request);
    List<Submission> getSubmissionsByHackathon(Long hackathonId);
}