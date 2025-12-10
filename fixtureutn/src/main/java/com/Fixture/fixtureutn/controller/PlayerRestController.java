package com.Fixture.fixtureutn.controller;

import com.Fixture.fixtureutn.model.Player;
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
@RequestMapping("/api/fixture/data/players")
public class PlayerRestController {


        @Autowired
        private I_FixtureService fixtureService;

        @Autowired
        private ImageStoreageService imageStorageService;


        @GetMapping
        public ResponseEntity<List<Player>> findAllPlayers (){

            List<Player> players = fixtureService.findAllPlayers();


            return ResponseEntity.ok(players); // codigo http 200


        }
        @GetMapping ("{id}")
        public ResponseEntity <Player> getPlayerId (@PathVariable Integer id){

            Player verPlayerId = fixtureService.getPlayerId(id);

            if (verPlayerId ==null){

                return ResponseEntity.notFound().build();//codigo http 404 recurso no encontrado

            }

            return ResponseEntity.ok(verPlayerId); // codigo de respuesta 200
        }

        @PostMapping
        public ResponseEntity<?> postPlayer (

                  @RequestParam String name
                , @RequestParam String position
                , @RequestParam Integer age
                , @RequestParam(required = false)MultipartFile countryFlagFile // archivo para la bandera
                , @RequestParam (required = false) MultipartFile photoFile  // archivo para la foto del jugador

                                              ) {
            Player player = new Player();
            player.setAge(age);
            player.setName(name);
            player.setPosition(position);

            try {
                if (countryFlagFile != null && !countryFlagFile.isEmpty()) {
                    String countryFlagPath = imageStorageService.storeImage(countryFlagFile,"jugadores");
                    player.setCountryFlag(countryFlagPath);

                }

                if (photoFile!= null && !photoFile.isEmpty()){
                    String photoPath = imageStorageService.storeImage(photoFile, "jugadores");
                    player.setPhotoPath(photoPath);
                }

            } catch (IOException e){

                    return ResponseEntity.internalServerError().body("ERROR AL GUARDAR LA IMAGEN " + e.getMessage ());
                }


            Player addPlayer = fixtureService.postPlayer(player);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("{id}")
                    .buildAndExpand(addPlayer.getId())
                    .toUri();

            return ResponseEntity.created(location).body(addPlayer);

        }
        @PutMapping ("{id}")
        public ResponseEntity <?> putPlayer (
                @PathVariable Integer id,
                @RequestParam String name,
                @RequestParam String position,
                @RequestParam Integer age,
                @RequestParam (required = false) MultipartFile countryFlag,
                @RequestParam (required = false) MultipartFile photoPath
        ){
            Player playerExisting = fixtureService.getPlayerId(id);


            if (playerExisting==null){

                return  ResponseEntity.notFound().build();

            }

            playerExisting.setName(name);
            playerExisting.setPosition(position);
            playerExisting.setAge(age);


            try {
                if (countryFlag !=null && !countryFlag.isEmpty()){

                    String countryFlagPath = imageStorageService.storeImage(countryFlag, "jugadores");
                    playerExisting.setCountryFlag(countryFlagPath);


                }

                if (photoPath!=null && !photoPath.isEmpty()){

                    String photoFile = imageStorageService.storeImage(photoPath,"jugadores");
                    playerExisting.setPhotoPath(photoFile);
                }



            } catch (IOException e){

                return ResponseEntity.internalServerError().body("NO SE ENCONTRO LA IMAGEN DE JUGADORES" + e.getMessage());

            }

            Player playerUpdate = fixtureService.putPlayer(playerExisting);


            if (playerUpdate ==null){

                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(playerUpdate);










        }
        @DeleteMapping ("{id}")
        public ResponseEntity <?> deletePlayerId (@PathVariable Integer id ){

            Player deletePlayer = fixtureService.deletePlayerId(id);

            if (deletePlayer ==null){

                return ResponseEntity.notFound().build(); //recurso no encontrado 404

            }

            return ResponseEntity.noContent().build(); // codigo de respuesta exitoso sin contenido

        }
    // PATCH: Permite actualizar datos parciales (incluyendo fotos)
    // OJO: En Postman usar "form-data".
    @PatchMapping("/{id}") // Agregamos el ID en la URL para ser RESTful
    public ResponseEntity<?> patchPlayer(
            @PathVariable Integer id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String position,
            @RequestParam(required = false) Integer age,
            @RequestParam(required = false) MultipartFile countryFlagFile,
            @RequestParam(required = false) MultipartFile photoFile
    ) {
        // 1. Buscar el jugador existente
        Player playerExisting = fixtureService.getPlayerId(id);
        if (playerExisting == null) {
            return ResponseEntity.notFound().build();
        }

        // 2. Actualizar solo lo que no sea nulo (Lógica PATCH)
        if (name != null) playerExisting.setName(name);
        if (position != null) playerExisting.setPosition(position);
        if (age != null) playerExisting.setAge(age);

        try {
            // 3. Lógica de Imágenes
            if (countryFlagFile != null && !countryFlagFile.isEmpty()) {
                String path = imageStorageService.storeImage(countryFlagFile, "jugadores");
                playerExisting.setCountryFlag(path);
            }
            if (photoFile != null && !photoFile.isEmpty()) {
                String path = imageStorageService.storeImage(photoFile, "jugadores");
                playerExisting.setPhotoPath(path);
            }
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Error guardando imagen: " + e.getMessage());
        }

        // 4. Guardar cambios
        Player updated = fixtureService.putPlayer(playerExisting); // Reutilizamos putPlayer para guardar
        return ResponseEntity.ok(updated);
    }


    }




