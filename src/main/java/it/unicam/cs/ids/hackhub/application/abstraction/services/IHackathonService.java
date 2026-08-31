package it.unicam.cs.ids.hackhub.application.abstraction.services;

import it.unicam.cs.ids.hackhub.domain.model.Hackathon;

import java.util.List;

public interface IHackathonService {

    void registerTeamToHackathon(Long userId, Long hackathonId);

    void unregisterTeamFromHackathon(Long userId, Long hackathonId);

    List<Hackathon> getAllHackathons();

    Hackathon getHackathonById(Long id);

    List<Hackathon> getAllMyHackathons(Long userId);
}
