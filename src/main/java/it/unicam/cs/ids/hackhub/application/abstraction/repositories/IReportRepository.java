package it.unicam.cs.ids.hackhub.application.abstraction.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.unicam.cs.ids.hackhub.domain.model.Report;

public interface IReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByHackathonId(Long hackathonId);
}