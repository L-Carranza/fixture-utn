package com.Fixture.fixtureutn.controller;

import com.Fixture.fixtureutn.service.FixtureServiceImpl;
import com.Fixture.fixtureutn.service.I_FixtureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping ("/api/fixture")

public class HomeController {
@Autowired
    private I_FixtureService fixtureService;

@GetMapping ("/")
public String root (Model model) {

    model.addAttribute("matches",fixtureService.findAllMatches());
    model.addAttribute("news",fixtureService.findAllNews());
    model.addAttribute("players",fixtureService.findAllPlayers());

    return "home";
}

@GetMapping ("/home")

public String homePage (Model model){
    model.addAttribute("matches", fixtureService.findAllMatches());
    model.addAttribute("news",fixtureService.findAllNews());
    model.addAttribute("players", fixtureService.findAllPlayers());

    return "home"; // html

}
@GetMapping ("/matches")
public String matches (Model model){

    model.addAttribute("matches" , fixtureService.findAllMatches());

return "partidos";
}

      @GetMapping ("/news")
        public String news (Model model){

            model.addAttribute("news",fixtureService.findAllNews());

            return "noticias";
        }
    @GetMapping("/players")
    public String players (Model model) {
        var players = fixtureService.findAllPlayers();

        System.out.println("=== DEBUG MEJORADO ===");
        System.out.println("Total jugadores: " + players.size());

        String basePath = "C:/IntelIdea/Proyectos/fixtureutn/fixtureutn/src/main/resources/static";

        for (var player : players) {
            System.out.println("--- " + player.getName() + " ---");

            try {
                // Verificar en SRC
                Path srcPhotoPath = Paths.get(basePath + player.getPhotoPath());
                Path srcFlagPath = Paths.get(basePath + player.getCountryFlag());

                System.out.println("SRC Photo: " + srcPhotoPath + " → " + Files.exists(srcPhotoPath));
                System.out.println("SRC Flag: " + srcFlagPath + " → " + Files.exists(srcFlagPath));

                // Verificar en TARGET
                Path targetPhotoPath = Paths.get("target/classes/static" + player.getPhotoPath());
                Path targetFlagPath = Paths.get("target/classes/static" + player.getCountryFlag());

                System.out.println("TARGET Photo: " + targetPhotoPath + " → " + Files.exists(targetPhotoPath));
                System.out.println("TARGET Flag: " + targetFlagPath + " → " + Files.exists(targetFlagPath));

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        model.addAttribute("players", players);
        return "jugadores";
    }
    }



