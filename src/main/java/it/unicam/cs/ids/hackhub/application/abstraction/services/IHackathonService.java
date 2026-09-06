package it.unicam.cs.ids.hackhub.application.abstraction.services;

import java.util.List;

import it.unicam.cs.ids.hackhub.domain.model.Hackathon;

public interface IHackathonService {

    void registerTeamToHackathon(Long userId, Long hackathonId);

    void unregisterTeamFromHackathon(Long userId, Long hackathonId);

    List<Hackathon> getAllHackathons();

    Hackathon getHackathonById(Long id);

    List<Hackathon> getAllMyHackathons(Long userId);

    void addMentor(Long hackathonId, Long userId);

    void cancelHackathon(Long hackathonId);

    void deleteHackathon(Long hackathonId, Long requestingUserId);
}
