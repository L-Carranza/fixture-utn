package com.Fixture.fixtureutn.controller;
import com.Fixture.fixtureutn.model.Player;
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
public class PlayerAdminController {
    @Autowired
    private I_FixtureService fixtureService;

    @Autowired
    private ImageStoreageService imageStoreageService;

    @GetMapping ("/dashboard")
    public String dashboard () {
        return "admin/dashboard";
    }
    //Listar
    @GetMapping ("/players")
    public String listPlayers (Model model){
        model.addAttribute("players", fixtureService.findAllPlayers());
        return "admin/jugadores-list";

    }
    // MOSTRAR FORMULARIO DE AGREGAR (CREATE - VISTA)
    @GetMapping ("/players/nuevo")
    public String showNewForm (Model model) {
        model.addAttribute("player", new Player()); //objeto vacio para el form

        return "admin/jugadores-form";
    }
    @GetMapping("/players/editar/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("players", fixtureService.getMatchId(id));
        return "admin/jugadores-form";

    }
// GUARDAR JUGADOR (CREATE/UPDATE-ACCION)
    @PostMapping ("/players/guardar")
    public String createPlayer (  @ModelAttribute Player player,
                                  @RequestParam ("photoFile")MultipartFile photoFile,
                                  @RequestParam ("flagFile") MultipartFile flagFile) {
        saveLogic (player,photoFile,flagFile);
        return "redirect:/admin/players";
    }
    @PutMapping ("/players/actualizar")
    public String updatePlayer      (@ModelAttribute Player player,
                                     @RequestParam ("photoFile")MultipartFile photoFile,
                                     @RequestParam ("flagFile")MultipartFile flagFile) {


        // Recuperamos los datos viejos para no perder las fotos si no se suben nuevas
        Player playerOld = fixtureService.getPlayerId(player.getId());

        if (player.getPhotoPath() == null|| player.getPhotoPath().isEmpty()){
            player.setPhotoPath(playerOld.getPhotoPath());
        }
        if (player.getCountryFlag()==null|| player.getCountryFlag().isEmpty()){
            player.setCountryFlag(playerOld.getCountryFlag());
        }

        saveLogic (player,photoFile,flagFile);

        return "redirect:/admin/players";

    }

    @DeleteMapping ("/players/borrar/{id}")
    public String deletePlayer (@PathVariable Integer id){
        fixtureService.deletePlayerId(id);
        return "redirect:/admin/players";

    }
    // Metodo auxiliar para no repetir codigo de guardar imagenes

    private void saveLogic (Player player, MultipartFile photo , MultipartFile flag){

        try {
            if (!photo.isEmpty()) {
                player.setPhotoPath(imageStoreageService.storeImage(photo, "jugadores"));
            }
            if (!flag.isEmpty()) {
                player.setCountryFlag(imageStoreageService.storeImage(flag, "jugadores"));
            }

            if (player.getId() != null) {
                fixtureService.putPlayer(player);

            } else {
                fixtureService.postPlayer(player);
            }

            } catch(IOException e){
                e.printStackTrace();


            }


        }
    }











