package com.Fixture.fixtureutn.service;


import com.Fixture.fixtureutn.model.Jugador;
import com.Fixture.fixtureutn.model.Noticia;
import com.Fixture.fixtureutn.model.Partido;
import jakarta.servlet.http.Part;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Primary
public class FixtureServiceImpl implements I_FixtureService {

    private final List<Partido> partidos = new ArrayList<>();
    private final List<Noticia> noticias = new ArrayList<>();
    private final List<Jugador> jugadores = new ArrayList<>();

    // Contadores atomicos para generar id unicos
    private final AtomicInteger partidoIdCounter = new AtomicInteger(0);
    private final AtomicInteger noticiaIdCounter = new AtomicInteger(0);
    private final AtomicInteger jugadorIdCounter = new AtomicInteger(0);


    public FixtureServiceImpl() {

// Inicializar los contadores con el ID más alto existente en los datos iniciales
        // o simplemente empezar desde 1 si siempre vas a agregar.
        // Por simplicidad, los inicializamos en 0 y el nextId será 1.

        // PARTIDOS
        // Aquí deberías crear imágenes reales en static/img/partidos_flags/
        // y usar esas rutas. Por ahora, las rutas que tienes funcionarán si las imágenes existen.

//PARTIDOS
        partidos.add(new Partido(partidoIdCounter.incrementAndGet(),"Brazil","Bolivia","Vie., 14/6","21:30","Estadio X","/img/partidos/brazil.png","/img/partidos/bolivia.png"));
        partidos.add(new Partido(partidoIdCounter.incrementAndGet(),"Argentina","Colombia","Sab., 13/4","20:00","Estadio A","/img/partidos/argentina.jfif","/img/partidos/colombia.jpg"));
        partidos.add(new Partido(partidoIdCounter.incrementAndGet(),"Talleres","Belgrano","Lun., 24/7","22:00","Estadio Mario Alberto Kempes","/img/partidos/talleres.jfif","/img/partidos/belgrano.png"));
        partidos.add(new Partido(partidoIdCounter.incrementAndGet(),"Boca","River","Mar., 31/7","23:00","Estadio Z","/img/partidos/boca.png","/img/partidos/river.png"));
        partidos.add(new Partido(partidoIdCounter.incrementAndGet(),"Instituto","Belgrano","Mie., 14/6","21:30","Estadio X","/img/partidos/instituto.png","/img/partidos/belgrano.png"));


        //NOTICIAS

        noticias.add(new Noticia (noticiaIdCounter.incrementAndGet(), "Elegí los 11 para el debut de la Selección", "Resumen o bajada corta.", "Olé", "hace 31 minutos", "/img/noticias/noticia1.png"));
        noticias.add(new Noticia(noticiaIdCounter.incrementAndGet(), "Las duplas en las habitaciones...", "Bajada breve.", "Infobae", "hace 17 horas", "/img/noticias/noticia2.png"));
        noticias.add(new Noticia(noticiaIdCounter.incrementAndGet(), "\"Queremos traer la Copa\"", "Entrevista breve.", "Olé", "hace 49 minutos", "/img/noticias/noticia3.png"));

        //Jugadores

        jugadores.add(new Jugador (jugadorIdCounter.incrementAndGet(), "Lionel Messi", "Delantero", 36, "/img/jugadores/messi.png", "/img/jugadores/messi.png"));
        jugadores.add(new Jugador(jugadorIdCounter.incrementAndGet(), "Carlos Tevez", "Delantero", 32, "/img/jugadores/tevez.png", "/img/jugadores/tevez.png"));
        jugadores.add(new Jugador(jugadorIdCounter.incrementAndGet(), "Luis Angulo", "Delantero", 27, "/img//jugadores/angulo.png", "/img/jugadores/angulo.png"));
        jugadores.add(new Jugador(jugadorIdCounter.incrementAndGet(), "Juan Portilla", "Centrocampista", 29, "/img//jugadores/portilla.png", "/img/jugadores/portilla.png"));
        jugadores.add(new Jugador (jugadorIdCounter.incrementAndGet(), "Emanuel Reynoso", "Centrocampista", 30, "/img/jugadores/reynoso.png", "/img/jugadores/reynoso.png"));
        jugadores.add(new Jugador(jugadorIdCounter.incrementAndGet(), "Zelarayan", "Delantero", 33, "/img/jugadores/escudobelgrano.png", "/img/jugadores/zelarayan.jpg"));

    }
    @Override
    public List <Partido> findAllPartidos () {

        return partidos;
    }

    @Override
    public Partido getPartidoId(Integer id) {
        for (Partido p : partidos){

            if (p.getId()==id){

                return p;


            }
        }
        return null;

    }

    @Override
    public Partido postPartido (Partido partido){
        partido.setId(partidoIdCounter.incrementAndGet());

       partidos.add(partido);

       return partido;

        }
        @Override
        public Partido putPartido (Partido partido){

        for (Partido p : partidos){

            if (p.getId()==partido.getId()){
                p.setAway(partido.getAway());
                p.setDate(partido.getDate());
                p.setFlagAway(partido.getFlagAway());
                p.setHome(partido.getHome());
                p.setFlagHome(partido.getFlagHome());
                p.setTime(partido.getTime());
                p.setStadium(partido.getStadium());

                return p;

            }
        }
        return null;


    }
@Override
    public Partido deletePartidoId (Integer id){

        for (Partido p : partidos){

            if (p.getId()==id){

                partidos.remove(p);

                return p;
            }
        }

        return null;
    }
    @Override
    public Partido patchProduct (Partido partido) {

        for (Partido p : partidos) {

            if (p.getId() == partido.getId()) {

                if (partido.getHome() != null) {

                    p.setHome(partido.getHome());

                }
                if (partido.getAway() != null) {

                    p.setAway(partido.getAway());
                }
                if (partido.getDate() != null) {

                    p.setDate(partido.getDate());
                }

                if (partido.getTime() != null) {

                    p.setTime(partido.getTime());
                }

                if (partido.getStadium() != null) {

                    p.setStadium(partido.getStadium());
                }

                return p;

            }

        }
        return null;
    }
@Override
    public List <Noticia> findAllNoticias () {

        return noticias;
    }
@Override
    public Noticia getNoticiaId(Integer id) {
        for (Noticia n : noticias){

            if (n.getId()==id){

                return n;


            }
        }
        return null;

    }
    @Override
    public Noticia postNoticia (Noticia noticia){
        noticia.setId(noticiaIdCounter.incrementAndGet());

        noticias.add(noticia);

        return noticia;

    }
    @Override
    public Noticia putNoticia (Noticia noticia){

        for (Noticia n : noticias){

            if (n.getId()==noticia.getId()){

                n.setTitle(noticia.getTitle());
                n.setSummary(noticia.getSummary());
                n.setSource(noticia.getSource());
                n.setTimeAgo(noticia.getTimeAgo());
                n.setImagePath(noticia.getImagePath());

                return n;
            }

        }

        return null;
    }

    @Override
    public Noticia deleteNoticiaId (Integer id){

        for (Noticia n : noticias){

            if (n.getId()==id){

                noticias.remove(n);

                return n;
            }
        }

        return null;
    }
    public Noticia patchNoticia (Noticia noticia){

        for (Noticia n : noticias){

            if (n.getId()==noticia.getId()){

                if (n.getTitle()!=null){

                    n.setTitle(noticia.getTitle());
                }

                if (n.getSummary()!=null){

                    n.setSummary(noticia.getSummary());
                }

                if (n.getSource()!=null){

                    n.setSource(noticia.getSource());
                }
                if (n.getTimeAgo()!=null){

                    n.setTimeAgo(noticia.getTimeAgo());
                }


                return n;

            }
        }
        return null;

    }




@Override
    public List <Jugador> findAllJugadores () {

        return jugadores;
    }

@Override
    public Jugador getJugadorId(Integer id) {
        for (Jugador j : jugadores){

            if (j.getId()==id){

                return j;


            }
        }
        return null;

    }

    @Override
    public Jugador postJugador (Jugador jugador){
        jugador.setId(jugadorIdCounter.incrementAndGet());
        jugadores.add(jugador);

        return jugador;

    }
    @Override
    public Jugador putJugador (Jugador jugador){

        for (Jugador j : jugadores){
            if (j.getId()==jugador.getId()){
                j.setName(jugador.getName());
                j.setPosition(jugador.getPosition());
                j.setAge(jugador.getAge());
                j.setCountryFlag(jugador.getCountryFlag());
                j.setPhotoPath(jugador.getPhotoPath());


                return j;
            }

        }
        return null;

    }

    @Override
    public Jugador deleteJugadorId (Integer id){

        for (Jugador j : jugadores){

            if (j.getId()==id){

                jugadores.remove(j);

                return j;
            }
        }

        return null;
    }
@Override
    public Jugador patchJugador (Jugador jugador){

        for (Jugador j : jugadores){

            if (j.getId()==jugador.getId()){

                if (jugador.getName()!=null){

                    j.setName(jugador.getName());

                }

                if (j.getPosition()!=null){

                    j.setPosition(jugador.getPosition());
                }

                if (j.getAge()!=null){

                    j.setAge(jugador.getAge());

                }

                return j;


            }


        }
        return null;


}


}
