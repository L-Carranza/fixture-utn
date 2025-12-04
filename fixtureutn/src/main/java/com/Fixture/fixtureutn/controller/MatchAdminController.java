package com.Fixture.fixtureutn.controller;

import com.Fixture.fixtureutn.model.Match;
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
public class MatchAdminController {
    @Autowired
    private I_FixtureService fixtureService;
    @Autowired private ImageStoreageService imageStorageService;

    @GetMapping ("/matches")
    public String list(Model model) {
        model.addAttribute("matches", fixtureService.findAllMatches());
        return "admin/partidos-list";
    }

    @GetMapping("/matches/nuevo")
    public String form (Model model) {
        model.addAttribute("match", new Match());
        return "admin/partidos-form";
    }

    @GetMapping("/matches/editar/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("match", fixtureService.getMatchId(id));
        return "admin/partidos-form";
    }

    @PostMapping ("/matches/guardar")
    public String save (@ModelAttribute Match match,
                          @RequestParam("fileHome") MultipartFile fileHome,
                          @RequestParam("fileAway") MultipartFile fileAway) {
        saveLogic (match, fileHome, fileAway);
        return "redirect:/admin/matches";
    }

    @PutMapping("/matches/actualizar")
    public String update(@ModelAttribute Match match,
                             @RequestParam("fileHome") MultipartFile fileHome,
                             @RequestParam("fileAway") MultipartFile fileAway) {
        Match old = fixtureService.getMatchId(match.getId());
        if(match.getFlagHome() == null) match.setFlagHome(old.getFlagHome());
        if(match.getFlagAway() == null) match.setFlagAway(old.getFlagAway());

        saveLogic (match, fileHome, fileAway);
        return "redirect:/admin/matches";
    }

    @DeleteMapping("/matches/borrar/{id}")
    public String delete(@PathVariable Integer id) {
        fixtureService.deleteMatchId(id);
        return "redirect:/admin/matches";
    }

    private void saveLogic(Match match, MultipartFile f1, MultipartFile f2) {
        try {
            if (!f1.isEmpty()) match.setFlagHome(imageStorageService.storeImage(f1, "partidos"));
            if (!f2.isEmpty()) match.setFlagAway(imageStorageService.storeImage(f2, "partidos"));

            if (match.getId() != null) fixtureService.putMatch(match);
            else fixtureService.postMatch(match);
        } catch (IOException e) { e.printStackTrace(); }
    }
}

