package me.junholee.springbootdeveloper.service;

import me.junholee.springbootdeveloper.domain.Match;
import me.junholee.springbootdeveloper.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MatchService {
    private final MatchRepository matchRepository;
    @Autowired
    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }
    public Match saveMatch(Match match) {
        return matchRepository.save(match);
    }

    public Match findById(int match_day){
        return matchRepository.findById(match_day).orElse(null);
    }
}
