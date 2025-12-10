package com.Fixture.fixtureutn.model;
import jakarta.persistence.*;

@Entity // 1. esto es una tabla
@Table (name="players") // Nombre de la tabla en plural
public class Player {
    @Id // esto es la Primarykey
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Hacemos la id autoIncremental
    private Integer id;
    private String name;
    private String position;
    private Integer age;
    private String countryFlag;
    private String photoPath;


    public Player() {


    }

    public Player(Integer id, String name, String position, Integer age, String countryFlag, String photoPath) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.age = age;
        this.countryFlag = countryFlag;
        this.photoPath = photoPath;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getCountryFlag() {
        return countryFlag;
    }

    public void setCountryFlag(String countryFlag) {
        this.countryFlag = countryFlag;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }
}
