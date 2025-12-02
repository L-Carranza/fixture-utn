package com.Fixture.fixtureutn.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;
@Service
public class ImageStoreageService {

    public ImageStoreageService (){}

// DEFINE EL DIRECTORIO BASE DONDE SE GUARDARAN LAS IMAGENES
    private final String UPLOAD_DIR_SRC = "C:/IntelIdea/Proyectos/fixtureutn/fixtureutn/src/main/resources/static/img/";
    private final String UPLOAD_DIR_TARGET = "C:/IntelIdea/Proyectos/fixtureutn/fixtureutn/target/classes/static/img/";


    public String storeImage (MultipartFile file , String subfolder) throws IOException{

        if (file.isEmpty()){


            return null; // o lanzar excepcion
        }

        //obtenemos el nombre original del archivo
        String originalFileName = file.getOriginalFilename();

        //obtenemos la extension del archivo

        String fileExtension = Objects.requireNonNull(originalFileName).substring(originalFileName.lastIndexOf("."));

        // generamos uin nombre de archivo unico para enviar colisiones

        String uniqueFileName = UUID.randomUUID().toString() + fileExtension;

        // construimos la riuta completa donde se va aguardar el archivo

        Path uploadPathSrc = Paths.get(UPLOAD_DIR_SRC , subfolder);

        // crear el subdirectorio si no existe

        if (!Files.exists(uploadPathSrc)){
            Files.createDirectories(uploadPathSrc);

        }

        Path filePathSrc = uploadPathSrc.resolve(uniqueFileName);

        // guardar el archivo en el sistema de archivo

        Files.copy(file.getInputStream(), filePathSrc);

        // devolver la ruta relativa que se guardara en la base de datos
        // y el frontend usara para acceder a la imagen

        Path uploadPathTarget = Paths.get(UPLOAD_DIR_TARGET , subfolder);

        if (!Files.exists(uploadPathTarget)){

            Files.createDirectories(uploadPathTarget);
        }

       Path filePathTarget = uploadPathTarget.resolve(uniqueFileName);

        try (InputStream inputStream = file.getInputStream()){
            Files.copy(inputStream,filePathTarget);
        }

        return "/img/" +subfolder + "/" + uniqueFileName;



    }


}
