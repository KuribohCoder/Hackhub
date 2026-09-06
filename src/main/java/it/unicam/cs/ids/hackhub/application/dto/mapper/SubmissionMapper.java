package it.unicam.cs.ids.hackhub.application.dto.mapper;

import it.unicam.cs.ids.hackhub.application.dto.response.SubmissionResponse;
import it.unicam.cs.ids.hackhub.domain.model.Submission;

public class SubmissionMapper {

    public static SubmissionResponse toResponse(Submission submission) {
        if (submission == null) {
            return null;
        }
        return new SubmissionResponse(
                submission.getId(),
                submission.getTitle(),
                submission.getDescription(),
                submission.getRepositoryUrl()
        );
    }
}