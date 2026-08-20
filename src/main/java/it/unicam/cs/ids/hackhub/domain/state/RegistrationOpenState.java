package it.unicam.cs.ids.hackhub.domain.state;
import it.unicam.cs.ids.hackhub.domain.model.Hackathon;
import it.unicam.cs.ids.hackhub.domain.model.Team;
public class RegistrationOpenState implements HackathonState {
    @Override
    public void registerTeam(Hackathon hackathon, Team team) {
        if (hackathon.getRegisteredTeams().contains(team)) {
            throw new IllegalArgumentException(
                    "Il team '%s' è già iscritto all'hackathon '%s'."
                            .formatted(team.getName(), hackathon.getTitle())
            );
        }
        hackathon.addRegisteredTeam(team);
    }
    @Override
    public void unregisterTeam(Hackathon hackathon, Team team) {
        if (!hackathon.getRegisteredTeams().contains(team)) {
            throw new IllegalArgumentException(
                    "Il team '%s' non è iscritto all'hackathon '%s'."
                            .formatted(team.getName(), hackathon.getTitle())
            );
        }
        hackathon.removeRegisteredTeam(team);
    }
}
