package com.Fixture.fixtureutn.controller;

import com.Fixture.fixtureutn.model.Jugador;
import com.Fixture.fixtureutn.service.I_FixtureService;
import com.Fixture.fixtureutn.service.ImageStoreageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping ("/admin")
public class JugadorAdminController {
    @Autowired
    private I_FixtureService fixtureService;

    @Autowired
    private ImageStoreageService imageStoreageService;

    @GetMapping ("/dashboard")

    public String dashboard () {

        return "admin/dashboard";
    }
    //Listar
    @GetMapping ("/jugadores")
    public String listarJugadores (Model model){
        model.addAttribute("players", fixtureService.findAllJugadores());
        return "admin/jugadores-list";

    }
    // MOSTRAR FORMULARIO DE AGREGAR (CREATE - VISTA)
    @GetMapping ("/jugadores/nuevo")
    public String mostrarFormularioNuevo (Model model) {
        model.addAttribute("jugador", new Jugador()); //objeto vacio para el form

        return "admin/jugadores-form";
    }
// GUARDAR JUGADOR (CREATE/UPDATE-ACCION)
    @PostMapping ("/jugadores/guardar")

    public String crearJugador (@ModelAttribute Jugador jugador,
                                  @RequestParam ("photoFile")MultipartFile photoFile,
                                  @RequestParam ("flagFile") MultipartFile flagFile) {
        guardarLogica (jugador,photoFile,flagFile);
        return "redirect:/admin/jugadores";
    }
    @PutMapping ("/jugadores/actualizar")
    public String actualizarJugador (@ModelAttribute Jugador jugador,
                                     @RequestParam ("photoFile")MultipartFile photoFile,
                                     @RequestParam ("flagFile")MultipartFile flagFile) {


        // Recuperamos los datos viejos para no perder las fotos si no se suben nuevas
        Jugador jugadorViejo = fixtureService.getJugadorId(jugador.getId());

        if (jugador.getPhotoPath() == null|| jugador.getPhotoPath().isEmpty()){
            jugador.setPhotoPath(jugadorViejo.getPhotoPath());
        }
        if (jugador.getCountryFlag()==null||jugador.getCountryFlag().isEmpty()){
            jugador.setCountryFlag(jugadorViejo.getCountryFlag());
        }

        guardarLogica (jugador,photoFile,flagFile);

        return "redirect:/admin/jugadores";

    }

    @DeleteMapping ("/jugadores/borrar/{id}")
    public String borrarJugador (@PathVariable Integer id){
        fixtureService.deleteJugadorId(id);
        return "redirect:/admin/jugadores";

    }
    // Metodo auxiliar para no repetir codigo de guardar imagenes

    private void guardarLogica (Jugador jugador, MultipartFile photo , MultipartFile flag){

        try {
            if (!photo.isEmpty()) {
                jugador.setPhotoPath(imageStoreageService.storeImage(photo, "jugadores"));
            }
            if (!flag.isEmpty()) {
                jugador.setCountryFlag(imageStoreageService.storeImage(flag, "jugadores"));
            }

            if (jugador.getId() != null) {
                fixtureService.putJugador(jugador);

            } else {
                fixtureService.postJugador(jugador);
            }

            } catch(IOException e){
                e.printStackTrace();


            }


        }
    }











