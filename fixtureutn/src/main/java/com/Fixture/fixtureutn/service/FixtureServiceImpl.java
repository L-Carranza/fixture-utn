package com.Fixture.fixtureutn.service;


import com.Fixture.fixtureutn.model.Match;
import com.Fixture.fixtureutn.model.News;
import com.Fixture.fixtureutn.model.Player;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Primary
public class FixtureServiceImpl implements I_FixtureService {

    private final List<Match> matches = new ArrayList<>();
    private final List<News> news = new ArrayList<>();
    private final List<Player> players = new ArrayList<>();

    // Contadores atomicos para generar id unicos
    private final AtomicInteger matchesIdCounter = new AtomicInteger(0);
    private final AtomicInteger newsIdCounter = new AtomicInteger(0);
    private final AtomicInteger playerIdCounter = new AtomicInteger(0);


    public FixtureServiceImpl() {

// Inicializar los contadores con el ID más alto existente en los datos iniciales
        // o simplemente empezar desde 1 si siempre vas a agregar.
        // Por simplicidad, los inicializamos en 0 y el nextId será 1.

        // PARTIDOS
        // Aquí deberías crear imágenes reales en static/img/partidos_flags/
        // y usar esas rutas. Por ahora, las rutas que tienes funcionarán si las imágenes existen.

//PARTIDOS
        matches.add(new Match(matchesIdCounter.incrementAndGet(),"Brazil","Bolivia","Vie., 14/6","21:30","Estadio X","/img/partidos/brazil.png","/img/partidos/bolivia.png"));
        matches.add(new Match(matchesIdCounter.incrementAndGet(),"Argentina","Colombia","Sab., 13/4","20:00","Estadio A","/img/partidos/argentina.jfif","/img/partidos/colombia.jpg"));
        matches.add(new Match(matchesIdCounter.incrementAndGet(),"Talleres","Belgrano","Lun., 24/7","22:00","Estadio Mario Alberto Kempes","/img/partidos/talleres.jfif","/img/partidos/belgrano.png"));
        matches.add(new Match(matchesIdCounter.incrementAndGet(),"Boca","River","Mar., 31/7","23:00","Estadio Z","/img/partidos/boca.png","/img/partidos/river.png"));
        matches.add(new Match(matchesIdCounter.incrementAndGet(),"Instituto","Belgrano","Mie., 14/6","21:30","Estadio X","/img/partidos/instituto.png","/img/partidos/belgrano.png"));


        //NOTICIAS

        news.add(new News(newsIdCounter.incrementAndGet(), "Elegí los 11 para el debut de la Selección", "Resumen o bajada corta.", "Olé", "hace 31 minutos", "/img/noticias/noticia1.png"));
        news.add(new News(newsIdCounter.incrementAndGet(), "Las duplas en las habitaciones...", "Bajada breve.", "Infobae", "hace 17 horas", "/img/noticias/noticia2.png"));
        news.add(new News(newsIdCounter.incrementAndGet(), "\"Queremos traer la Copa\"", "Entrevista breve.", "Olé", "hace 49 minutos", "/img/noticias/noticia3.png"));

        //Jugadores

        players.add(new Player(playerIdCounter.incrementAndGet(), "Lionel Messi", "Delantero", 36, "/img/jugadores/messi.png", "/img/jugadores/messi.png"));
        players.add(new Player(playerIdCounter.incrementAndGet(), "Carlos Tevez", "Delantero", 32, "/img/jugadores/tevez.png", "/img/jugadores/tevez.png"));
        players.add(new Player(playerIdCounter.incrementAndGet(), "Luis Angulo", "Delantero", 27, "/img//jugadores/angulo.png", "/img/jugadores/angulo.png"));
        players.add(new Player(playerIdCounter.incrementAndGet(), "Juan Portilla", "Centrocampista", 29, "/img//jugadores/portilla.png", "/img/jugadores/portilla.png"));
        players.add(new Player(playerIdCounter.incrementAndGet(), "Emanuel Reynoso", "Centrocampista", 30, "/img/jugadores/reynoso.png", "/img/jugadores/reynoso.png"));
        players.add(new Player(playerIdCounter.incrementAndGet(), "Zelarayan", "Delantero", 33, "/img/jugadores/escudobelgrano.png", "/img/jugadores/zelarayan.jpg"));

    }
    @Override
    public List <Match> findAllMatches () {

        return matches;
    }

    @Override
    public Match getMatchId(Integer id) {
        for (Match p : matches){

            if (p.getId()==id){

                return p;


            }
        }
        return null;

    }

    @Override
    public Match postMatch (Match match){
        match.setId(matchesIdCounter.incrementAndGet());

       matches.add(match);

       return match;

        }
        @Override
        public Match putMatch (Match match){

        for (Match p : matches){

            if (p.getId()== match.getId()){
                p.setAway(match.getAway());
                p.setDate(match.getDate());
                p.setFlagAway(match.getFlagAway());
                p.setHome(match.getHome());
                p.setFlagHome(match.getFlagHome());
                p.setTime(match.getTime());
                p.setStadium(match.getStadium());

                return p;

            }
        }
        return null;


    }
@Override
    public Match deleteMatchId (Integer id){

        for (Match p : matches){

            if (p.getId()==id){

                matches.remove(p);

                return p;
            }
        }

        return null;
    }
    @Override
    public Match patchMatch (Match match) {

        for (Match p : matches) {

            if (p.getId() == match.getId()) {

                if (match.getHome() != null) {

                    p.setHome(match.getHome());

                }
                if (match.getAway() != null) {

                    p.setAway(match.getAway());
                }
                if (match.getDate() != null) {

                    p.setDate(match.getDate());
                }

                if (match.getTime() != null) {

                    p.setTime(match.getTime());
                }

                if (match.getStadium() != null) {

                    p.setStadium(match.getStadium());
                }

                return p;

            }

        }
        return null;
    }
@Override
    public List<News> findAllNews () {

        return news;
    }
@Override
    public News getNewsId(Integer id) {
        for (News n : news){

            if (n.getId()==id){

                return n;


            }
        }
        return null;

    }
    @Override
    public News postNews (News news){
        news.setId(newsIdCounter.incrementAndGet());

        this.news.add(news);

        return news;

    }
    @Override
    public News putNews (News news){

        for (News n : this.news){

            if (n.getId()== news.getId()){

                n.setTitle(news.getTitle());
                n.setSummary(news.getSummary());
                n.setSource(news.getSource());
                n.setTimeAgo(news.getTimeAgo());
                n.setImagePath(news.getImagePath());

                return n;
            }

        }

        return null;
    }

    @Override
    public News deleteNewsId (Integer id){

        for (News n : news){

            if (n.getId()==id){

                news.remove(n);

                return n;
            }
        }

        return null;
    }
    public News patchNews (News news){

        for (News n : this.news){

            if (n.getId()== news.getId()){

                if (n.getTitle()!=null){

                    n.setTitle(news.getTitle());
                }

                if (n.getSummary()!=null){

                    n.setSummary(news.getSummary());
                }

                if (n.getSource()!=null){

                    n.setSource(news.getSource());
                }
                if (n.getTimeAgo()!=null){

                    n.setTimeAgo(news.getTimeAgo());
                }


                return n;

            }
        }
        return null;

    }




@Override
    public List <Player> findAllPlayers () {

        return players;
    }

@Override
    public Player getPlayerId(Integer id) {
        for (Player j : players){

            if (j.getId()==id){

                return j;


            }
        }
        return null;

    }

    @Override
    public Player postPlayer (Player player){
        player.setId(playerIdCounter.incrementAndGet());
        players.add(player);

        return player;

    }
    @Override
    public Player putPlayer (Player player){

        for (Player j : players){
            if (j.getId()== player.getId()){
                j.setName(player.getName());
                j.setPosition(player.getPosition());
                j.setAge(player.getAge());
                j.setCountryFlag(player.getCountryFlag());
                j.setPhotoPath(player.getPhotoPath());


                return j;
            }

        }
        return null;

    }

    @Override
    public Player deletePlayerId (Integer id){

        for (Player j : players){

            if (j.getId()==id){

                players.remove(j);

                return j;
            }
        }

        return null;
    }
@Override
    public Player patchPlayer (Player player){

        for (Player j : players){

            if (j.getId()== player.getId()){

                if (player.getName()!=null){

                    j.setName(player.getName());

                }

                if (j.getPosition()!=null){

                    j.setPosition(player.getPosition());
                }

                if (j.getAge()!=null){

                    j.setAge(player.getAge());

                }

                return j;


            }


        }
        return null;


}


}
