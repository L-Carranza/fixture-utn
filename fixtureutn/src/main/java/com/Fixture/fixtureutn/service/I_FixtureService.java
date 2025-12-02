package com.Fixture.fixtureutn.service;

import com.Fixture.fixtureutn.model.Jugador;
import com.Fixture.fixtureutn.model.Noticia;
import com.Fixture.fixtureutn.model.Partido;

import java.util.List;

public interface I_FixtureService {

    public List<Partido> findAllPartidos ();
    public Partido getPartidoId (Integer id);
    public Partido postPartido (Partido partido);
    public Partido putPartido (Partido partido);
    public Partido deletePartidoId (Integer id);
    public Partido patchProduct (Partido partido);


    public List <Noticia> findAllNoticias ();
    public Noticia getNoticiaId (Integer id);
    public Noticia postNoticia (Noticia noticia);
    public Noticia putNoticia (Noticia noticia);
    public Noticia deleteNoticiaId (Integer id);
    public Noticia patchNoticia (Noticia noticia);





    public List <Jugador> findAllJugadores ();
    public Jugador getJugadorId (Integer id);
    public Jugador postJugador (Jugador jugador);
    public Jugador putJugador (Jugador jugador);
    public Jugador deleteJugadorId (Integer id);
    public Jugador patchJugador (Jugador jugador);
}
