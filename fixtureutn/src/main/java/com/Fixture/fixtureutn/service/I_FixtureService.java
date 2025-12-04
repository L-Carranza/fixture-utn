package com.Fixture.fixtureutn.service;

import com.Fixture.fixtureutn.model.Match;
import com.Fixture.fixtureutn.model.News;
import com.Fixture.fixtureutn.model.Player;

import java.util.List;

public interface I_FixtureService {

    public List<Match> findAllMatches ();
    public Match getMatchId (Integer id);
    public Match postMatch (Match match);
    public Match putMatch (Match match);
    public Match deleteMatchId (Integer id);
    public Match patchMatch (Match matches);


    public List <News> findAllNews ();
    public News getNewsId(Integer id);
    public News postNews (News news);
    public News putNews (News news);
    public News deleteNewsId (Integer id);
    public News patchNews (News news);


    public List <Player> findAllPlayers ();
    public Player getPlayerId (Integer id);
    public Player postPlayer (Player player);
    public Player putPlayer (Player player);
    public Player deletePlayerId (Integer id);
    public Player patchPlayer (Player player);
}
