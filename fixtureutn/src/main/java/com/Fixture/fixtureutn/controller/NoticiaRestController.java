package com.Fixture.fixtureutn.controller;

import com.Fixture.fixtureutn.model.Jugador;
import com.Fixture.fixtureutn.model.Noticia;
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
@RequestMapping("/api/fixture/noticia")

public class NoticiaRestController {
    @Autowired
    private I_FixtureService iFixtureService;
    @Autowired
    private ImageStoreageService imageStoreageService;

    @GetMapping
    public ResponseEntity<List<Noticia>> findAllNoticia (){

        List<Noticia> noticias = iFixtureService.findAllNoticias();


        return ResponseEntity.ok(noticias); // codigo http 200


    }
    @GetMapping ("{id}")
    public ResponseEntity <Noticia> getJugadorId (@PathVariable Integer id){

        Noticia verNoticiaId = iFixtureService.getNoticiaId(id);

        if (verNoticiaId==null){

            return ResponseEntity.notFound().build();//codigo http 404 recurso no encontrado

        }

        return ResponseEntity.ok(verNoticiaId); // codigo de respuesta 200
    }

    @PostMapping
    public ResponseEntity<?> postNoticia (

//parametros https
            @RequestParam String title,
            @RequestParam String summary,
            @RequestParam String sourse,
            @RequestParam String timeAgo,
            @RequestParam (required = false)MultipartFile imageFile // el archivo para la imagen de la noticia

            ) {
        Noticia noticia = new Noticia();
        noticia.setTitle(title);
        noticia.setTimeAgo(timeAgo);
        noticia.setSummary(summary);
        noticia.setSource(sourse);


        try {
            if (imageFile!=null && !imageFile.isEmpty()){
                String imagePath = imageStoreageService.storeImage(imageFile,"noticias");
                noticia.setImagePath(imagePath);
            }

        } catch (IOException e) {
           return ResponseEntity.internalServerError().body("ERORR AL GUARDAR LA IMAGEN DE LA NOTICIA" + e.getMessage());
        }


        Noticia addNoticia = iFixtureService.postNoticia(noticia);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("{id}")
                .buildAndExpand(addNoticia.getId())
                .toUri();

        return ResponseEntity.created(location).body(addNoticia);

    }
    @PutMapping ("{id}")
    public ResponseEntity <?> putNoticia (

            @PathVariable Integer id,
            @RequestParam String title,
            @RequestParam String sourse,
            @RequestParam String timeAgo,
            @RequestParam (required = false)MultipartFile imageFile
    ){

        Noticia noticiaExistente = iFixtureService.getNoticiaId(id);

        if (noticiaExistente==null){

            ResponseEntity.notFound().build();

        }

        noticiaExistente.setTitle(title);
        noticiaExistente.setSource(sourse);
        noticiaExistente.setTimeAgo(timeAgo);


        try{

            if (imageFile!=null&& !imageFile.isEmpty()){

                String imageFilePath = imageStoreageService.storeImage(imageFile, "noticias");

                noticiaExistente.setImagePath(imageFilePath);

            }




        } catch (IOException e){


            return ResponseEntity.internalServerError().body("NO SE ENCONTRO LA IMAGEN CARGADA DE NOTICIAS" + e.getMessage());

        }

        Noticia noticiaActualizada = iFixtureService.putNoticia(noticiaExistente);

        if (noticiaExistente==null){

            return ResponseEntity.notFound().build();

        }

        return ResponseEntity.ok(noticiaActualizada);

    }
    @DeleteMapping ("{id}")
    public ResponseEntity <?> deleteNoticiaId (@PathVariable Integer id ){

        Noticia deleteNoticia = iFixtureService.deleteNoticiaId(id);

        if (deleteNoticia==null){

            return ResponseEntity.notFound().build(); //recurso no encontrado 404

        }

        return ResponseEntity.noContent().build(); // codigo de respuesta exitoso sin contenido

    }
    @PatchMapping
    public ResponseEntity <?> patchNoticia (@RequestBody Noticia noticia){

        Noticia modificarParcialNoticia = iFixtureService.patchNoticia(noticia);

        if (noticia.getId()== null){

            return  ResponseEntity.notFound().build(); // recurso no encontrado  404

        }

        return ResponseEntity.ok(noticia);
    }


}



