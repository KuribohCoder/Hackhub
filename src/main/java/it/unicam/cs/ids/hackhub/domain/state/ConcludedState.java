package it.unicam.cs.ids.hackhub.domain.state;
import it.unicam.cs.ids.hackhub.domain.model.Hackathon;
import it.unicam.cs.ids.hackhub.domain.model.Team;
public class ConcludedState implements HackathonState {
    @Override
    public void registerTeam(Hackathon hackathon, Team team) {
        throw new IllegalStateException(
                "Impossibile iscrivere il team '%s': l'hackathon '%s' è già concluso."
                        .formatted(team.getName(), hackathon.getTitle())
        );
    }
    @Override
    public void unregisterTeam(Hackathon hackathon, Team team) {
        throw new IllegalStateException(
                "Impossibile cancellare l'iscrizione del team '%s': l'hackathon '%s' è già concluso."
                        .formatted(team.getName(), hackathon.getTitle())
        );
    }
}
