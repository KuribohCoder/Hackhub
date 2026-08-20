package it.unicam.cs.ids.hackhub.domain.state;
import it.unicam.cs.ids.hackhub.domain.enums.HackathonStatus;
import it.unicam.cs.ids.hackhub.domain.model.Hackathon;
import it.unicam.cs.ids.hackhub.domain.model.Team;
public interface HackathonState {
    void registerTeam(Hackathon hackathon, Team team);
    void unregisterTeam(Hackathon hackathon, Team team);
    static HackathonState fromStatus(HackathonStatus status) {
        return switch (status) {
            case REGISTRATION_OPEN -> new RegistrationOpenState();
            case IN_PROGRESS       -> new InProgressState();
            case UNDER_EVALUATION  -> new UnderEvaluationState();
            case CONCLUDED         -> new ConcludedState();
        };
    }
}
