package com.Fixture.fixtureutn.controller;

import com.Fixture.fixtureutn.service.FixtureServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class PartidoController {

    public FixtureServiceImpl fixtureService;

    public PartidoController () {

    }

    @GetMapping("/partidos")
    public String partidos (Model model) {
        model.addAttribute("matches", fixtureService.findAllPartidos());

        return "partidos";

    }

}
