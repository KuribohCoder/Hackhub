package it.unicam.cs.ids.hackhub.application.services;

import it.unicam.cs.ids.hackhub.application.abstraction.services.IReportService;
import it.unicam.cs.ids.hackhub.application.abstraction.repositories.IReportRepository;
import it.unicam.cs.ids.hackhub.domain.model.Report;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReportService implements IReportService {

    private final IReportRepository reportRepository;

    public ReportService(IReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Report> getReportsByHackathon(Long hackathonId) {
        return reportRepository.findByHackathonId(hackathonId);
    }
}