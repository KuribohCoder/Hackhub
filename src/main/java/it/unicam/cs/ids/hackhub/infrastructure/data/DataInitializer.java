package it.unicam.cs.ids.hackhub.infrastructure.data;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import it.unicam.cs.ids.hackhub.application.abstraction.repositories.IEvaluationRepository;
import it.unicam.cs.ids.hackhub.application.abstraction.repositories.IHackathonRepository;
import it.unicam.cs.ids.hackhub.application.abstraction.repositories.IInvitationRepository;
import it.unicam.cs.ids.hackhub.application.abstraction.repositories.IReportRepository;
import it.unicam.cs.ids.hackhub.application.abstraction.repositories.ISubmissionRepository;
import it.unicam.cs.ids.hackhub.application.abstraction.repositories.ITeamRepository;
import it.unicam.cs.ids.hackhub.application.abstraction.repositories.IUserRepository;
import it.unicam.cs.ids.hackhub.domain.enums.HackathonStatus;
import it.unicam.cs.ids.hackhub.domain.enums.InvitationStatus;
import it.unicam.cs.ids.hackhub.domain.enums.Role;
import it.unicam.cs.ids.hackhub.domain.model.Evaluation;
import it.unicam.cs.ids.hackhub.domain.model.Hackathon;
import it.unicam.cs.ids.hackhub.domain.model.Invitation;
import it.unicam.cs.ids.hackhub.domain.model.Report;
import it.unicam.cs.ids.hackhub.domain.model.Submission;
import it.unicam.cs.ids.hackhub.domain.model.Team;
import it.unicam.cs.ids.hackhub.domain.model.User;

/**
 * Inizializzatore dei dati di seed per il database H2 in-memory all'avvio dell'applicazione.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private final IUserRepository userRepository;
    private final ITeamRepository teamRepository;
    private final IHackathonRepository hackathonRepository;
    private final IInvitationRepository invitationRepository;
    private final ISubmissionRepository submissionRepository;
    private final IEvaluationRepository evaluationRepository;
    private final IReportRepository reportRepository;

    public DataInitializer(IUserRepository userRepository,
                           ITeamRepository teamRepository,
                           IHackathonRepository hackathonRepository,
                           IInvitationRepository invitationRepository,
                           ISubmissionRepository submissionRepository,
                           IEvaluationRepository evaluationRepository,
                           IReportRepository reportRepository) {
        this.userRepository       = userRepository;
        this.teamRepository       = teamRepository;
        this.hackathonRepository  = hackathonRepository;
        this.invitationRepository = invitationRepository;
        this.submissionRepository = submissionRepository;
        this.evaluationRepository = evaluationRepository;
        this.reportRepository     = reportRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (userRepository.findById(1L).isPresent()) {
            return;
        }

        User organizer = new User("mario.org", "Mario", "Rossi", "mario.organizzatore@hackhub.it",
                "password123", "333111222", LocalDate.of(1985, 4, 12), "M", "IT60X0542811101000000123456");
        organizer.setRole(Role.ORGANIZER);
        organizer = userRepository.save(organizer);

        User judge = new User("giulia.judge", "Giulia", "Bianchi", "giulia.giudice@hackhub.it",
                "password123", "333222333", LocalDate.of(1988, 7, 23), "F", "IT60X0542811101000000123457");
        judge.setRole(Role.JUDGE);
        judge = userRepository.save(judge);

        User mentor1 = new User("luca.mentor", "Luca", "Verdi", "luca.mentore@hackhub.it",
                "password123", "333333444", LocalDate.of(1990, 11, 5), "M", "IT60X0542811101000000123458");
        mentor1.setRole(Role.MENTOR);
        mentor1 = userRepository.save(mentor1);

        User mentor2 = new User("sara.mentor", "Sara", "Neri", "sara.mentore@hackhub.it",
                "password123", "333444555", LocalDate.of(1992, 2, 18), "F", "IT60X0542811101000000123459");
        mentor2.setRole(Role.MENTOR);
        mentor2 = userRepository.save(mentor2);

        User creator1 = new User("alice.creator", "Alice", "Gialli", "alice.creator@hackhub.it",
                "password123", "333555666", LocalDate.of(1998, 9, 14), "F", "IT60X0542811101000000123460");
        creator1.setRole(Role.TEAM_CREATOR);
        creator1 = userRepository.save(creator1);

        User member1 = new User("bob.member", "Bob", "Blu", "bob.member@hackhub.it",
                "password123", "333666777", LocalDate.of(1999, 1, 30), "M", "IT60X0542811101000000123461");
        member1.setRole(Role.TEAM_MEMBER);
        member1 = userRepository.save(member1);

        User creator2 = new User("carlo.creator", "Carlo", "Viola", "carlo.creator@hackhub.it",
                "password123", "333777888", LocalDate.of(1997, 6, 8), "M", "IT60X0542811101000000123462");
        creator2.setRole(Role.TEAM_CREATOR);
        creator2 = userRepository.save(creator2);

        User genericUser1 = new User("elena.user", "Elena", "Rosa", "elena.generic@hackhub.it",
                "password123", "333888999", LocalDate.of(2001, 12, 1), "F", "IT60X0542811101000000123463");
        genericUser1.setRole(Role.GENERIC_USER);
        genericUser1 = userRepository.save(genericUser1);

        User genericUser2 = new User("davide.user", "Davide", "Marrone", "davide.generic@hackhub.it",
                "password123", "333999000", LocalDate.of(2000, 3, 25), "M", "IT60X0542811101000000123464");
        genericUser2.setRole(Role.GENERIC_USER);
        genericUser2 = userRepository.save(genericUser2);

        Team team1 = new Team("ByteCraft", creator1);
        team1 = teamRepository.save(team1);
        team1.addMember(creator1, Role.TEAM_CREATOR);
        team1.addMember(member1, Role.TEAM_MEMBER);
        userRepository.save(creator1);
        userRepository.save(member1);
        team1 = teamRepository.save(team1);

        Team team2 = new Team("CodeNinjas", creator2);
        team2 = teamRepository.save(team2);
        team2.addMember(creator2, Role.TEAM_CREATOR);
        userRepository.save(creator2);
        team2 = teamRepository.save(team2);

        Hackathon h1 = new Hackathon(
                "AI Innovation Challenge",
                "Hackathon dedicato a soluzioni innovative con Intelligenza Artificiale Generativa.",
                LocalDateTime.now().plusDays(10),
                LocalDateTime.now().plusDays(15),
                LocalDateTime.now().plusDays(17),
                4,
                organizer
        );
        h1.setRules("Tutti i progetti devono essere open source e creati durante l'evento.");
        h1.setLocation("Milano / Online");
        h1.setPrizeAmount(5000.0);
        h1.setJudgeUser(judge);
        h1.addMentor(mentor1);
        h1.addMentor(mentor2);
        h1.addRegisteredTeam(team1);
        h1.setStatus(HackathonStatus.REGISTRATION_OPEN);
        h1 = hackathonRepository.save(h1);

        Hackathon h2 = new Hackathon(
                "CyberSecurity Marathon",
                "Gara di sicurezza informatica, penetration testing e crittografia applicata.",
                LocalDateTime.now().minusDays(5),
                LocalDateTime.now().minusDays(1),
                LocalDateTime.now().plusDays(2),
                4,
                organizer
        );
        h2.setRules("Divieto assoluto di attacchi contro l'infrastruttura del contest.");
        h2.setLocation("Roma / Ibrido");
        h2.setPrizeAmount(3500.0);
        h2.setJudgeUser(judge);
        h2.addMentor(mentor1);
        h2.addRegisteredTeam(team1);
        h2.addRegisteredTeam(team2);
        h2.setStatus(HackathonStatus.IN_PROGRESS);
        h2 = hackathonRepository.save(h2);

        Hackathon h3 = new Hackathon(
                "Green Tech Sprint",
                "Sviluppo di prototipi sostenibili a basso impatto ambientale.",
                LocalDateTime.now().minusDays(10),
                LocalDateTime.now().minusDays(5),
                LocalDateTime.now().minusDays(1),
                4,
                organizer
        );
        h3.setRules("Codice compilabile su architetture a basso consumo energetico.");
        h3.setLocation("Torino / Online");
        h3.setPrizeAmount(4000.0);
        h3.setJudgeUser(judge);
        h3.addMentor(mentor1);
        h3.addRegisteredTeam(team1);
        h3.addRegisteredTeam(team2);
        h3.setStatus(HackathonStatus.UNDER_EVALUATION);
        h3 = hackathonRepository.save(h3);

        Hackathon h4 = new Hackathon(
                "Legacy Web Contest",
                "Hackathon concluso per la modernizzazione di applicazioni web legacy.",
                LocalDateTime.now().minusDays(30),
                LocalDateTime.now().minusDays(20),
                LocalDateTime.now().minusDays(18),
                4,
                organizer
        );
        h4.setRules("Refactoring e migrazione a Spring Boot 3.");
        h4.setLocation("Bologna");
        h4.setPrizeAmount(2000.0);
        h4.setJudgeUser(judge);
        h4.addRegisteredTeam(team1);
        h4.setWinningTeam(team1);
        h4.setStatus(HackathonStatus.CONCLUDED);
        h4 = hackathonRepository.save(h4);

        Invitation inv1 = new Invitation(creator1, genericUser1);
        invitationRepository.save(inv1);

        Invitation inv2 = new Invitation(creator2, genericUser2);
        inv2.setStatus(InvitationStatus.REJECTED);
        invitationRepository.save(inv2);

        Submission sub1 = new Submission();
        sub1.setTitle("GreenEnergy AI Optimizer");
        sub1.setDescription("Algoritmo per l'ottimizzazione e riduzione dei consumi nei data center.");
        sub1.setRepositoryUrl("https://github.com/ByteCraft/green-optimizer");
        sub1.setHackathon(h3);
        sub1 = submissionRepository.save(sub1);

        Submission sub2 = new Submission();
        sub2.setTitle("CyberShield Monitor");
        sub2.setDescription("Strumento di monitoraggio real-time per minacce cyber.");
        sub2.setRepositoryUrl("https://github.com/ByteCraft/cybershield");
        sub2.setHackathon(h2);
        sub2 = submissionRepository.save(sub2);

        Report rep1 = new Report();
        rep1.setHackathonId(h3.getId());
        rep1.setContent("Segnalazione per utilizzo di librerie non consentite.");
        reportRepository.save(rep1);

        Evaluation eval1 = new Evaluation();
        eval1.setJudgeId(judge.getId());
        eval1.setSubmissionId(sub1.getId());
        eval1.setScore(94.5);
        eval1.setFeedback("Ottima architettura e chiara riduzione dei consumi energetici misurata.");
        evaluationRepository.save(eval1);
    }
}
