package com.Fixture.fixtureutn.controller;

import com.Fixture.fixtureutn.model.Partido;
import com.Fixture.fixtureutn.service.I_FixtureService;
import com.Fixture.fixtureutn.service.ImageStoreageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping ("/admin/partidos")
public class PartidoAdminController {
    @Autowired
    private I_FixtureService fixtureService;
    @Autowired private ImageStoreageService imageStorageService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("partidos", fixtureService.findAllPartidos());
        return "admin/partidos-list";
    }

    @GetMapping("/nuevo")
    public String formulario(Model model) {
        model.addAttribute("partido", new Partido());
        return "admin/partidos-form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        model.addAttribute("partido", fixtureService.getPartidoId(id));
        return "admin/partidos-form";
    }

    @PostMapping
    public String guardar(@ModelAttribute Partido partido,
                          @RequestParam("fileHome") MultipartFile fileHome,
                          @RequestParam("fileAway") MultipartFile fileAway) {
        guardarLogica(partido, fileHome, fileAway);
        return "redirect:/admin/partidos";
    }

    @PutMapping("/actualizar")
    public String actualizar(@ModelAttribute Partido partido,
                             @RequestParam("fileHome") MultipartFile fileHome,
                             @RequestParam("fileAway") MultipartFile fileAway) {
        Partido viejo = fixtureService.getPartidoId(partido.getId());
        if(partido.getFlagHome() == null) partido.setFlagHome(viejo.getFlagHome());
        if(partido.getFlagAway() == null) partido.setFlagAway(viejo.getFlagAway());

        guardarLogica(partido, fileHome, fileAway);
        return "redirect:/admin/partidos";
    }

    @DeleteMapping("/borrar/{id}")
    public String borrar(@PathVariable Integer id) {
        fixtureService.deletePartidoId(id);
        return "redirect:/admin/partidos";
    }

    private void guardarLogica(Partido partido, MultipartFile f1, MultipartFile f2) {
        try {
            if (!f1.isEmpty()) partido.setFlagHome(imageStorageService.storeImage(f1, "partidos"));
            if (!f2.isEmpty()) partido.setFlagAway(imageStorageService.storeImage(f2, "partidos"));

            if (partido.getId() != null) fixtureService.putPartido(partido);
            else fixtureService.postPartido(partido);
        } catch (IOException e) { e.printStackTrace(); }
    }
}

