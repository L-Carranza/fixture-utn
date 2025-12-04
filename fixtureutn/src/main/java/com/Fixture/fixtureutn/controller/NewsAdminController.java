package com.Fixture.fixtureutn.controller;

import com.Fixture.fixtureutn.model.News;
import com.Fixture.fixtureutn.service.I_FixtureService;
import com.Fixture.fixtureutn.service.ImageStoreageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/admin")
public class NewsAdminController {
    @Autowired
    private I_FixtureService fixtureService;
    @Autowired private ImageStoreageService imageStorageService;

    @GetMapping ("/news")
    public String list (Model model) {
        model.addAttribute("news", fixtureService.findAllNews());
        return "admin/noticias-list";
    }

    @GetMapping("/news/nuevo")
    public String form(Model model) {
        model.addAttribute("news", new News());
        return "admin/noticias-form";
    }

    @GetMapping("/editar/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("noticia", fixtureService.getNewsId(id));
        return "admin/noticias-form";
    }

    // CREAR (POST)
    @PostMapping ("/news/guardar")
    public String save(@ModelAttribute News news, @RequestParam("imageFile") MultipartFile imageFile) {
        saveLogic(news, imageFile);
        return "redirect:/admin/news";
    }

    // ACTUALIZAR (PUT)
    @PutMapping("/news/actualizar")
    public String update(@ModelAttribute News news, @RequestParam("imageFile") MultipartFile imageFile) {
        // Recuperar imagen anterior si no sube nueva
        News old = fixtureService.getNewsId(news.getId());
        if(news.getImagePath() == null || news.getImagePath().isEmpty()) {
            news.setImagePath(old.getImagePath());
        }
        saveLogic(news, imageFile);
        return "redirect:/admin/news";
    }

    // BORRAR (DELETE)
    @DeleteMapping("/news/borrar/{id}")
    public String delete(@PathVariable Integer id) {
        fixtureService.deleteNewsId(id);
        return "redirect:/admin/news";
    }

    private void saveLogic(News news, MultipartFile file) {
        try {
            if (!file.isEmpty()) {
                news.setImagePath(imageStorageService.storeImage(file, "noticias"));
            }
            if (news.getId() != null) fixtureService.putNews(news);
            else fixtureService.postNews(news);
        } catch (IOException e) { e.printStackTrace(); }
    }
}

