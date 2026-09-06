package it.unicam.cs.ids.hackhub.application.abstraction.services;

import java.util.List;

import it.unicam.cs.ids.hackhub.domain.model.Report;

public interface IReportService {
    List<Report> getReportsByHackathon(Long hackathonId);
}