package com.Fixture.fixtureutn.controller;

import com.Fixture.fixtureutn.model.Match;
import com.Fixture.fixtureutn.service.I_FixtureService;
import com.Fixture.fixtureutn.service.ImageStoreageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping ("/api/fixture/data/matches")
public class MatchRestController {

    @Autowired
private I_FixtureService iFixtureService;
    @Autowired
    private ImageStoreageService imageStoreageService;

@GetMapping
public ResponseEntity <List<Match>>findAllMatches (){
List<Match> matches = iFixtureService.findAllMatches();
return ResponseEntity.ok(matches); // codigo http 200


}
@GetMapping ("{id}")
public ResponseEntity <Match> getMatchId (@PathVariable Integer id){

Match seeMatchId = iFixtureService.getMatchId(id);

if (seeMatchId ==null){

    return ResponseEntity.notFound().build();//codigo http 404 recurso no encontrado

}

return ResponseEntity.ok(seeMatchId); // codigo de respuesta 200
}

@PostMapping
public ResponseEntity<?> postMatch (
        @RequestParam String home,
        @RequestParam String away,
        @RequestParam String date,
        @RequestParam String time,
        @RequestParam String stadium,
        @RequestParam (required = false)MultipartFile flagHome, // imagen bandera local
        @RequestParam (required = false)MultipartFile flagAway // imagen bandera visitante

        ) {

    Match match = new Match();
    match.setHome(home);
    match.setAway(away);
    match.setDate(date);
    match.setTime(time);
    match.setStadium(stadium);


    try {
        if (flagHome!=null && !flagHome.isEmpty()){

            String flagHomePath = imageStoreageService.storeImage(flagHome, "partidos");
            match.setFlagHome(flagHomePath);
        }

        if (flagAway!=null && ! flagAway.isEmpty()){

            String flagAwayPath = imageStoreageService.storeImage(flagAway,"partidos");
            match.setFlagAway(flagAwayPath);
        }
    } catch (IOException e){

        ResponseEntity.internalServerError().body("ERROR AL GUARDAR IMAGEN DE BANDERA " + e.getMessage());
    }

    Match addMatch = iFixtureService.postMatch(match);

    URI location = ServletUriComponentsBuilder
            .fromCurrentRequest().path("{id}")
            .buildAndExpand(addMatch.getId())
            .toUri();

    return ResponseEntity.created(location).body(addMatch);

}
   @PutMapping ("{id}")
    public ResponseEntity <?> putMatch (
            //parametros http
            @PathVariable Integer id,
            @RequestParam String home,
            @RequestParam String away,
            @RequestParam String date,
            @RequestParam String time,
            @RequestParam String stadium,
            @RequestParam (required = false) MultipartFile flagHome,
            @RequestParam (required = false) MultipartFile flagAway
   ){

    //Obtenemos el partido existente por id
       Match matchExisting=iFixtureService.getMatchId(id);

       if (matchExisting ==null){

           return ResponseEntity.notFound().build();
       }

       matchExisting.setHome(home);
       matchExisting.setAway(away);
       matchExisting.setDate(date);
       matchExisting.setTime(time);
       matchExisting.setStadium(stadium);

       try {

           if (flagHome!=null&& !flagHome.isEmpty()){


               String flagHomePath = imageStoreageService.storeImage(flagHome, "partidos");
               matchExisting.setFlagHome(flagHomePath);

           }

           if (flagAway!=null && !flagAway.isEmpty()){

               String flagAwayPath = imageStoreageService.storeImage(flagAway,"partidos");
               matchExisting.setFlagAway(flagAwayPath);
           }


       } catch (IOException e){

           return ResponseEntity.internalServerError().body("ERROR AL GUARDAR LA IMAGEN DE BANDERA" + e.getMessage());
       }

       Match matchUpdate = iFixtureService.putMatch(matchExisting);

       if (matchUpdate ==null){

           return ResponseEntity.notFound().build();
       }

       return ResponseEntity.ok(matchUpdate);

   }

@DeleteMapping ("{id}")
public ResponseEntity <?> deleteMatchId (@PathVariable Integer id ){

    Match deleteMatch = iFixtureService.deleteMatchId(id);

    if (deleteMatch==null){

        return ResponseEntity.notFound().build(); //recurso no encontrado 404

    }

    return ResponseEntity.noContent().build(); // codigo de respuesta exitoso sin contenido

}
    // PATCH: Actualización parcial de Partidos (incluyendo escudos)
    @PatchMapping("/{id}")
    public ResponseEntity<?> patchMatch(
            @PathVariable Integer id,
            @RequestParam(required = false) String home,
            @RequestParam(required = false) String away,
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String time,
            @RequestParam(required = false) String stadium,
            @RequestParam(required = false) MultipartFile flagHome,
            @RequestParam(required = false) MultipartFile flagAway
    ) {
        // 1. Buscar partido existente
        Match matchExisting = iFixtureService.getMatchId(id);
        if (matchExisting == null) {
            return ResponseEntity.notFound().build();
        }

        // 2. Actualizar campos de texto si no son nulos
        if (home != null) matchExisting.setHome(home);
        if (away != null) matchExisting.setAway(away);
        if (date != null) matchExisting.setDate(date);
        if (time != null) matchExisting.setTime(time);
        if (stadium != null) matchExisting.setStadium(stadium);

        // 3. Actualizar imágenes si se envían nuevas
        try {
            if (flagHome != null && !flagHome.isEmpty()) {
                String pathHome = imageStoreageService.storeImage(flagHome, "partidos");
                matchExisting.setFlagHome(pathHome);
            }
            if (flagAway != null && !flagAway.isEmpty()) {
                String pathAway = imageStoreageService.storeImage(flagAway, "partidos");
                matchExisting.setFlagAway(pathAway);
            }
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Error al guardar imagen: " + e.getMessage());
        }

        // 4. Guardar
        Match matchUpdated = iFixtureService.putMatch(matchExisting);
        return ResponseEntity.ok(matchUpdated);
    }


}
