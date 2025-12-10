package com.Fixture.fixtureutn.service;


import com.Fixture.fixtureutn.model.Match;
import com.Fixture.fixtureutn.model.News;
import com.Fixture.fixtureutn.model.Player;
import com.Fixture.fixtureutn.repository.MatchRepository;
import com.Fixture.fixtureutn.repository.NewsRepository;
import com.Fixture.fixtureutn.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Primary
public class FixtureServiceImpl implements I_FixtureService {

@Autowired
private PlayerRepository playerRepository;

@Autowired
private MatchRepository matchRepository;

@Autowired
private NewsRepository newsRepository;

    public FixtureServiceImpl() {


    }
    @Override
    public List <Match> findAllMatches () {

        return matchRepository.findAll();
    }

    @Override
    public Match getMatchId(Integer id) {
        return matchRepository.findById(id).orElse(null);

    }

    @Override
    public Match postMatch (Match match){
        return matchRepository.save(match); // save hace insert o update solo

        }
        @Override
        public Match putMatch (Match match){
        return matchRepository.save(match);
    }
@Override
    public Match deleteMatchId (Integer id){
        Match m = getMatchId(id);

        if (m!= null){
            matchRepository.deleteById(id);
        }
        return m;

    }
    @Override
    public Match patchMatch (Match match) {
        return matchRepository.save(match);

    }
@Override
    public List<News> findAllNews () {

        return newsRepository.findAll();
    }
@Override
    public News getNewsId(Integer id) {
        return newsRepository.findById(id).orElse(null);

    }

    @Override
    public News postNews (News news){
        return newsRepository.save(news);
    }
    @Override
    public News putNews (News news){
        return newsRepository.save(news);

    }

    @Override
    public News deleteNewsId (Integer id) {
        News n = getNewsId(id);
        if (n != null) newsRepository.deleteById(id);
        return n;
    }
    public News patchNews (News news){
        return newsRepository.save(news);
    }

@Override
    public List <Player> findAllPlayers () {
        return playerRepository.findAll();
    }

@Override
    public Player getPlayerId(Integer id) {
        return playerRepository.findById(id).orElse(null);

    }

    @Override
    public Player postPlayer (Player player){
        return playerRepository.save(player);

    }
    @Override
    public Player putPlayer (Player player){
        return playerRepository.save(player);

    }

    @Override
    public Player deletePlayerId (Integer id){
        Player p = getPlayerId(id);
        if (p!= null)
            playerRepository.deleteById(id);
        return p;

    }
@Override
    public Player patchPlayer (Player player){
        return playerRepository.save(player);
    }



}
