package com.Fixture.fixtureutn.controller;

import com.Fixture.fixtureutn.model.News;
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
@RequestMapping("/api/fixture/data/news")

public class NewsRestController {
    @Autowired
    private I_FixtureService iFixtureService;
    @Autowired
    private ImageStoreageService imageStoreageService;

    @GetMapping
    public ResponseEntity<List<News>> findAllNews (){

        List<News> news = iFixtureService.findAllNews();


        return ResponseEntity.ok(news); // codigo http 200


    }
    @GetMapping ("{id}")
    public ResponseEntity <News> getNewsId (@PathVariable Integer id){

        News seeNewsId = iFixtureService.getNewsId(id);

        if (seeNewsId ==null){

            return ResponseEntity.notFound().build();//codigo http 404 recurso no encontrado

        }

        return ResponseEntity.ok(seeNewsId); // codigo de respuesta 200
    }

    @PostMapping
    public ResponseEntity<?> postNews (
//parametros https
            @RequestParam String title,
            @RequestParam String summary,
            @RequestParam String sourse,
            @RequestParam String timeAgo,
            @RequestParam (required = false)MultipartFile imageFile // el archivo para la imagen de la noticia

            ) {
        News news = new News();
        news.setTitle(title);
        news.setTimeAgo(timeAgo);
        news.setSummary(summary);
        news.setSource(sourse);


        try {
            if (imageFile!=null && !imageFile.isEmpty()){
                String imagePath = imageStoreageService.storeImage(imageFile,"noticias");
                news.setImagePath(imagePath);
            }

        } catch (IOException e) {
           return ResponseEntity.internalServerError().body("ERORR AL GUARDAR LA IMAGEN DE LA NOTICIA" + e.getMessage());
        }


        News addNews = iFixtureService.postNews(news);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("{id}")
                .buildAndExpand(addNews.getId())
                .toUri();

        return ResponseEntity.created(location).body(addNews);

    }
    @PutMapping ("{id}")
    public ResponseEntity <?> putNews (

            @PathVariable Integer id,
            @RequestParam String title,
            @RequestParam String sourse,
            @RequestParam String timeAgo,
            @RequestParam (required = false)MultipartFile imageFile
    ){

        News newsExisting = iFixtureService.getNewsId(id);

        if (newsExisting ==null){

            ResponseEntity.notFound().build();

        }

        newsExisting.setTitle(title);
        newsExisting.setSource(sourse);
        newsExisting.setTimeAgo(timeAgo);


        try{

            if (imageFile!=null&& !imageFile.isEmpty()){

                String imageFilePath = imageStoreageService.storeImage(imageFile, "noticias");

                newsExisting.setImagePath(imageFilePath);

            }




        } catch (IOException e){


            return ResponseEntity.internalServerError().body("NO SE ENCONTRO LA IMAGEN CARGADA DE NOTICIAS" + e.getMessage());

        }

        News newsUpdate = iFixtureService.putNews(newsExisting);

        if (newsExisting ==null){

            return ResponseEntity.notFound().build();

        }

        return ResponseEntity.ok(newsUpdate);

    }

    @DeleteMapping ("{id}")
    public ResponseEntity <?> deleteNewsId (@PathVariable Integer id ){

        News deleteNews = iFixtureService.deleteNewsId(id);

        if (deleteNews ==null){

            return ResponseEntity.notFound().build(); //recurso no encontrado 404

        }

        return ResponseEntity.noContent().build(); // codigo de respuesta exitoso sin contenido

    }
    // PATCH: Actualización parcial de Noticias (incluyendo imagen)
    @PatchMapping("/{id}")
    public ResponseEntity<?> patchNews(
            @PathVariable Integer id,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String summary,
            @RequestParam(required = false) String source,
            @RequestParam(required = false) String timeAgo,
            @RequestParam(required = false) MultipartFile imageFile
    ) {
        // 1. Buscar noticia existente
        News newsExisting = iFixtureService.getNewsId(id);
        if (newsExisting == null) {
            return ResponseEntity.notFound().build();
        }

        // 2. Actualizar campos de texto si no son nulos
        if (title != null) newsExisting.setTitle(title);
        if (summary != null) newsExisting.setSummary(summary);
        if (source != null) newsExisting.setSource(source);
        if (timeAgo != null) newsExisting.setTimeAgo(timeAgo);

        // 3. Actualizar imagen si se envía una nueva
        try {
            if (imageFile != null && !imageFile.isEmpty()) {
                String path = imageStoreageService.storeImage(imageFile, "noticias");
                newsExisting.setImagePath(path);
            }
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Error al guardar imagen: " + e.getMessage());
        }

        // 4. Guardar
        News newsUpdated = iFixtureService.putNews(newsExisting);
        return ResponseEntity.ok(newsUpdated);
    }
}



