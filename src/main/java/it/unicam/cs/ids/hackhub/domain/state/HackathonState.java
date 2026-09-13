package it.unicam.cs.ids.hackhub.domain.state;

import it.unicam.cs.ids.hackhub.domain.enums.HackathonStatus;
import it.unicam.cs.ids.hackhub.domain.model.Hackathon;
import it.unicam.cs.ids.hackhub.domain.model.Team;

/**
 * Interfaccia che definisce il contratto per gli stati dell'hackathon secondo lo State Pattern.
 * Regola le operazioni di iscrizione e cancellazione in base alla fase corrente dell'evento.
 */
public interface HackathonState {

    /**
     * Esegue l'iscrizione di un team all'hackathon nello stato corrente.
     *
     * @param hackathon l'hackathon su cui effettuare l'operazione
     * @param team il team da iscrivere
     */
    void registerTeam(Hackathon hackathon, Team team);

    /**
     * Esegue la cancellazione dell'iscrizione di un team all'hackathon nello stato corrente.
     *
     * @param hackathon l'hackathon su cui effettuare l'operazione
     * @param team il team da disiscrivere
     */
    void unregisterTeam(Hackathon hackathon, Team team);

    /**
     * Factory method per ottenere l'istanza dello stato corrispondente all'enum di stato.
     *
     * @param status l'enum HackathonStatus corrente
     * @return l'istanza di HackathonState appropriata
     */
    static HackathonState fromStatus(HackathonStatus status) {
        return switch (status) {
            case REGISTRATION_OPEN -> new RegistrationOpenState();
            case IN_PROGRESS       -> new InProgressState();
            case UNDER_EVALUATION  -> new UnderEvaluationState();
            case CONCLUDED         -> new ConcludedState();
            default -> throw new IllegalArgumentException("Unexpected value: " + status);
        };
    }
}
