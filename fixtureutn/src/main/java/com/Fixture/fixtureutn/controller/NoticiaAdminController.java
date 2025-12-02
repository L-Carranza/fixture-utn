package com.Fixture.fixtureutn.controller;

import com.Fixture.fixtureutn.model.Noticia;
import com.Fixture.fixtureutn.service.I_FixtureService;
import com.Fixture.fixtureutn.service.ImageStoreageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/admin/noticias")
public class NoticiaAdminController {
    @Autowired
    private I_FixtureService fixtureService;
    @Autowired private ImageStoreageService imageStorageService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("noticias", fixtureService.findAllNoticias());
        return "admin/noticias-list";
    }

    @GetMapping("/nuevo")
    public String formulario(Model model) {
        model.addAttribute("noticia", new Noticia());
        return "admin/noticias-form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        model.addAttribute("noticia", fixtureService.getNoticiaId(id));
        return "admin/noticias-form";
    }

    // CREAR (POST)
    @PostMapping
    public String guardar(@ModelAttribute Noticia noticia, @RequestParam("imageFile") MultipartFile imageFile) {
        guardarLogica(noticia, imageFile);
        return "redirect:/admin/noticias";
    }

    // ACTUALIZAR (PUT)
    @PutMapping("/actualizar")
    public String actualizar(@ModelAttribute Noticia noticia, @RequestParam("imageFile") MultipartFile imageFile) {
        // Recuperar imagen anterior si no sube nueva
        Noticia vieja = fixtureService.getNoticiaId(noticia.getId());
        if(noticia.getImagePath() == null || noticia.getImagePath().isEmpty()) {
            noticia.setImagePath(vieja.getImagePath());
        }
        guardarLogica(noticia, imageFile);
        return "redirect:/admin/noticias";
    }

    // BORRAR (DELETE)
    @DeleteMapping("/borrar/{id}")
    public String borrar(@PathVariable Integer id) {
        fixtureService.deleteNoticiaId(id);
        return "redirect:/admin/noticias";
    }

    private void guardarLogica(Noticia noticia, MultipartFile file) {
        try {
            if (!file.isEmpty()) {
                noticia.setImagePath(imageStorageService.storeImage(file, "noticias"));
            }
            if (noticia.getId() != null) fixtureService.putNoticia(noticia);
            else fixtureService.postNoticia(noticia);
        } catch (IOException e) { e.printStackTrace(); }
    }
}

