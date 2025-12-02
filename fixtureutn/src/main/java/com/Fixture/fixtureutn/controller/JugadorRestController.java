package com.Fixture.fixtureutn.controller;

import com.Fixture.fixtureutn.model.Jugador;
import com.Fixture.fixtureutn.model.Partido;
import com.Fixture.fixtureutn.service.I_FixtureService;
import com.Fixture.fixtureutn.service.ImageStoreageService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/fixture/jugador")
public class JugadorRestController {


        @Autowired
        private I_FixtureService fixtureService;
        @Autowired
        private ImageStoreageService imageStorageService;


        @GetMapping
        public ResponseEntity<List<Jugador>> findAllPartidos (){

            List<Jugador> jugadores = fixtureService.findAllJugadores();


            return ResponseEntity.ok(jugadores); // codigo http 200


        }
        @GetMapping ("{id}")
        public ResponseEntity <Jugador> getJugadorId (@PathVariable Integer id){

            Jugador verJugadorId = fixtureService.getJugadorId(id);

            if (verJugadorId==null){

                return ResponseEntity.notFound().build();//codigo http 404 recurso no encontrado

            }

            return ResponseEntity.ok(verJugadorId); // codigo de respuesta 200
        }

        @PostMapping
        public ResponseEntity<?> postJugador (

                  @RequestParam String name
                , @RequestParam String position
                , @RequestParam Integer age
                , @RequestParam(required = false)MultipartFile countryFlagFile // archivo para la bandera
                , @RequestParam (required = false) MultipartFile photoFile  // archivo para la foto del jugador


                                              ) {

            Jugador jugador = new Jugador ();
            jugador.setAge(age);
            jugador.setName(name);
            jugador.setPosition(position);

            try {
                if (countryFlagFile != null && !countryFlagFile.isEmpty()) {
                    String countryFlagPath = imageStorageService.storeImage(countryFlagFile,"jugadores");
                    jugador.setCountryFlag(countryFlagPath);

                }

                if (photoFile!= null && !photoFile.isEmpty()){
                    String photoPath = imageStorageService.storeImage(photoFile, "jugadores");
                    jugador.setPhotoPath(photoPath);
                }

            } catch (IOException e){

                    return ResponseEntity.internalServerError().body("ERROR AL GUARDAR LA IMAGEN " + e.getMessage ());
                }


            Jugador addJugador = fixtureService.postJugador(jugador);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("{id}")
                    .buildAndExpand(addJugador.getId())
                    .toUri();

            return ResponseEntity.created(location).body(addJugador);

        }
        @PutMapping ("{id}")
        public ResponseEntity <?> putJugador (
                @PathVariable Integer id,
                @RequestParam String name,
                @RequestParam String position,
                @RequestParam Integer age,
                @RequestParam (required = false) MultipartFile countryFlag,
                @RequestParam (required = false) MultipartFile photoPath
        ){
            Jugador jugadorExistente = fixtureService.getJugadorId(id);


            if (jugadorExistente==null){

                return  ResponseEntity.notFound().build();

            }
            jugadorExistente.setName(name);
            jugadorExistente.setPosition(position);
            jugadorExistente.setAge(age);


            try {
                if (countryFlag !=null && !countryFlag.isEmpty()){

                    String countryFlagPath = imageStorageService.storeImage(countryFlag, "jugadores");
                    jugadorExistente.setCountryFlag(countryFlagPath);


                }

                if (photoPath!=null && !photoPath.isEmpty()){

                    String photoFile = imageStorageService.storeImage(photoPath,"jugadores");
                    jugadorExistente.setPhotoPath(photoFile);
                }



            } catch (IOException e){

                return ResponseEntity.internalServerError().body("NO SE ENCONTRO LA IMAGEN DE JUGADORES" + e.getMessage());

            }

            Jugador  jugadorActualizado = fixtureService.putJugador(jugadorExistente);


            if (jugadorActualizado==null){

                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(jugadorActualizado);










        }
        @DeleteMapping ("{id}")
        public ResponseEntity <?> deleteJugadorId (@PathVariable Integer id ){

            Jugador deleteJugador = fixtureService.deleteJugadorId(id);

            if (deleteJugador==null){

                return ResponseEntity.notFound().build(); //recurso no encontrado 404

            }

            return ResponseEntity.noContent().build(); // codigo de respuesta exitoso sin contenido

        }
        @PatchMapping
        public ResponseEntity <?> patchJugador (@RequestBody Jugador jugador){

            Jugador modificarParcialJugador = fixtureService.patchJugador(jugador);

            if (jugador.getId()== null){

                return  ResponseEntity.notFound().build(); // recurso no encontrado  404

            }

            return ResponseEntity.ok(jugador);
        }


    }




