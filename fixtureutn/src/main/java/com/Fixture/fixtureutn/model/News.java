package com.Fixture.fixtureutn.model;
import jakarta.persistence.*;
@Entity
@Table(name ="news")
public class News {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY) // AutoIncremental
    private Integer id;
    private String title;
    private String summary;
    private String source;
    private String timeAgo;
    private String imagePath;

    public News() {


    }

    public News(Integer id, String title, String summary, String source, String timeAgo, String imagePath) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.source = source;
        this.timeAgo = timeAgo;
        this.imagePath = imagePath;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTimeAgo() {
        return timeAgo;
    }

    public void setTimeAgo(String timeAgo) {
        this.timeAgo = timeAgo;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
