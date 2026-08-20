package it.unicam.cs.ids.hackhub.application.abstraction.services;
public interface IHackathonService {
    void registerTeamToHackathon(Long userId, Long hackathonId);
    void unregisterTeamFromHackathon(Long userId, Long hackathonId);
}
