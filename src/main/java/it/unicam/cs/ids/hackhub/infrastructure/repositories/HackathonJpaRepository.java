package it.unicam.cs.ids.hackhub.infrastructure.repositories;

import it.unicam.cs.ids.hackhub.domain.model.Hackathon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HackathonJpaRepository extends JpaRepository<Hackathon, Long> {

    @Query("SELECT DISTINCT h FROM Hackathon h LEFT JOIN h.mentors m WHERE h.organizerUser.id = :userId OR h.judgeUser.id = :userId OR m.id = :userId")
    List<Hackathon> findByStaffMemberId(@Param("userId") Long userId);
}
