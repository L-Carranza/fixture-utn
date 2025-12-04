package com.Fixture.fixtureutn.model;

public class Match {
    private Integer id;
    private String home;
    private String away;
    private String date;
    private String time;
    private String stadium;
    private String flagHome;
    private String flagAway;

    public Match() {


    }

   public Match(Integer id, String home, String away, String date,
                String time, String stadium, String flagHome, String flagAway){
        this.id=id;
        this.home=home;
        this.away=away;
        this.date=date;
        this.time=time;
        this.stadium=stadium;
        this.flagHome=flagHome;
        this.flagAway=flagAway;


   }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getAway() {
        return away;
    }

    public void setAway(String away) {
        this.away = away;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStadium() {
        return stadium;
    }

    public void setStadium(String stadium) {
        this.stadium = stadium;
    }

    public String getFlagHome() {
        return flagHome;
    }

    public void setFlagHome(String flagHome) {
        this.flagHome = flagHome;
    }

    public String getFlagAway() {
        return flagAway;
    }

    public void setFlagAway(String flagAway) {
        this.flagAway = flagAway;
    }
}
