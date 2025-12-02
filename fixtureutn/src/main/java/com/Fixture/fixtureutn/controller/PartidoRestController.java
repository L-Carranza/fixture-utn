package com.Fixture.fixtureutn.controller;

import com.Fixture.fixtureutn.model.Partido;
import com.Fixture.fixtureutn.service.I_FixtureService;
import com.Fixture.fixtureutn.service.ImageStoreageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.security.PublicKey;
import java.util.List;

@RestController
@RequestMapping ("/api/fixture/partido")
public class PartidoRestController {

    @Autowired
private I_FixtureService iFixtureService;
    @Autowired
    private ImageStoreageService imageStoreageService;

@GetMapping
public ResponseEntity <List<Partido>>findAllPartidos (){

List<Partido> partidos = iFixtureService.findAllPartidos();


return ResponseEntity.ok(partidos); // codigo http 200


}
@GetMapping ("{id}")
public ResponseEntity <Partido> getPartidoId (@PathVariable Integer id){

Partido verPartidoId = iFixtureService.getPartidoId(id);

if (verPartidoId==null){

    return ResponseEntity.notFound().build();//codigo http 404 recurso no encontrado

}

return ResponseEntity.ok(verPartidoId); // codigo de respuesta 200
}

@PostMapping
public ResponseEntity<?> postPartido (
        @RequestParam String home,
        @RequestParam String away,
        @RequestParam String date,
        @RequestParam String time,
        @RequestParam String stadium,
        @RequestParam (required = false)MultipartFile flagHome, // imagen bandera local
        @RequestParam (required = false)MultipartFile flagAway // imagen bandera visitante

        ) {

    Partido partido = new Partido();
    partido.setHome(home);
    partido.setAway(away);
    partido.setDate(date);
    partido.setTime(time);
    partido.setStadium(stadium);


    try {
        if (flagHome!=null && !flagHome.isEmpty()){

            String flagHomePath = imageStoreageService.storeImage(flagHome, "partidos");
            partido.setFlagHome(flagHomePath);
        }

        if (flagAway!=null && ! flagAway.isEmpty()){

            String flagAwayPath = imageStoreageService.storeImage(flagAway,"partidos");
            partido.setFlagAway(flagAwayPath);
        }
    } catch (IOException e){

        ResponseEntity.internalServerError().body("ERROR AL GUARDAR IMAGEN DE BANDERA " + e.getMessage());
    }

    Partido addPartido = iFixtureService.postPartido(partido);

    URI location = ServletUriComponentsBuilder
            .fromCurrentRequest().path("{id}")
            .buildAndExpand(addPartido.getId())
            .toUri();

    return ResponseEntity.created(location).body(addPartido);

}
   @PutMapping ("{id}")
    public ResponseEntity <?> putPartido (
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
       Partido partidoExistente = iFixtureService.getPartidoId(id);

       if (partidoExistente==null){

           return ResponseEntity.notFound().build();
       }

       partidoExistente.setHome(home);
       partidoExistente.setAway(away);
       partidoExistente.setDate(date);
       partidoExistente.setTime(time);
       partidoExistente.setStadium(stadium);

       try {

           if (flagHome!=null&& !flagHome.isEmpty()){


               String flagHomePath = imageStoreageService.storeImage(flagHome, "partidos");
               partidoExistente.setFlagHome(flagHomePath);

           }

           if (flagAway!=null && !flagAway.isEmpty()){

               String flagAwayPath = imageStoreageService.storeImage(flagAway,"partidos");
               partidoExistente.setFlagAway(flagAwayPath);
           }


       } catch (IOException e){

           return ResponseEntity.internalServerError().body("ERROR AL GUARDAR LA IMAGEN DE BANDERA" + e.getMessage());
       }

       Partido partidoActualizado = iFixtureService.putPartido(partidoExistente);

       if (partidoActualizado==null){

           return ResponseEntity.notFound().build();
       }

       return ResponseEntity.ok(partidoActualizado);

   }

@DeleteMapping ("{id}")
public ResponseEntity <?> deletePartidoId (@PathVariable Integer id ){

    Partido deletePartido = iFixtureService.deletePartidoId(id);

    if (deletePartido==null){

        return ResponseEntity.notFound().build(); //recurso no encontrado 404

    }

    return ResponseEntity.noContent().build(); // codigo de respuesta exitoso sin contenido

}
@PatchMapping
public ResponseEntity <?> patchPartido (@RequestBody Partido partido){

    Partido modificarParcialPartido = iFixtureService.patchProduct(partido);

    if (partido.getId()== null){

        return  ResponseEntity.notFound().build(); // recurso no encontrado  404

    }

    return ResponseEntity.ok(partido);
}


}
