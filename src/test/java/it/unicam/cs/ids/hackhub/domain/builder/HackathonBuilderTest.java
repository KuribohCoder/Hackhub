package it.unicam.cs.ids.hackhub.domain.builder;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unicam.cs.ids.hackhub.domain.enums.HackathonStatus;
import it.unicam.cs.ids.hackhub.domain.enums.Role;
import it.unicam.cs.ids.hackhub.domain.model.Hackathon;
import it.unicam.cs.ids.hackhub.domain.model.User;

class HackathonBuilderTest {

    private User organizer;
    private User judge;
    private User mentor;
    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        organizer = new User("Mario", "mario@test.it");
        organizer.setRole(Role.ORGANIZER);

        judge = new User("Luca", "luca@test.it");
        judge.setRole(Role.JUDGE);

        mentor = new User("Giuseppe", "giuseppe@test.it");
        mentor.setRole(Role.MENTOR);

        now = LocalDateTime.now();
    }

    @Test
    void testBuildValidHackathon() {
        Hackathon hackathon = new HackathonBuilder()
                .withTitle("AI Hackathon 2026")
                .withDescription("Competizione di intelligenza artificiale")
                .withRules("Regolamento ufficiale")
                .withLocation("Camerino")
                .withPrizeAmount(5000.0)
                .withRegistrationDeadline(now.plusDays(5))
                .withStartDate(now.plusDays(10))
                .withEndDate(now.plusDays(12))
                .withMaxTeamMembers(4)
                .withOrganizer(organizer)
                .withJudge(judge)
                .addMentor(mentor)
                .build();

        assertNotNull(hackathon);
        assertEquals("AI Hackathon 2026", hackathon.getTitle());
        assertEquals("Competizione di intelligenza artificiale", hackathon.getDescription());
        assertEquals("Regolamento ufficiale", hackathon.getRules());
        assertEquals("Camerino", hackathon.getLocation());
        assertEquals(5000.0, hackathon.getPrizeAmount());
        assertEquals(4, hackathon.getMaxTeamMembers());
        assertEquals(organizer, hackathon.getOrganizerUser());
        assertEquals(judge, hackathon.getJudgeUser());
        assertEquals(1, hackathon.getMentors().size());
        assertEquals(HackathonStatus.REGISTRATION_OPEN, hackathon.getStatus());
    }

    @Test
    void testBuildMissingTitleThrowsException() {
        HackathonBuilder builder = new HackathonBuilder()
                .withRegistrationDeadline(now.plusDays(5))
                .withStartDate(now.plusDays(10))
                .withEndDate(now.plusDays(12))
                .withMaxTeamMembers(4)
                .withOrganizer(organizer);

        assertThrows(IllegalStateException.class, builder::build);
    }

    @Test
    void testBuildInvalidDatesThrowsException() {
        HackathonBuilder builder = new HackathonBuilder()
                .withTitle("Hackathon")
                .withRegistrationDeadline(now.plusDays(15)) // deadline after start
                .withStartDate(now.plusDays(10))
                .withEndDate(now.plusDays(12))
                .withMaxTeamMembers(4)
                .withOrganizer(organizer);

        assertThrows(IllegalStateException.class, builder::build);
    }
}
