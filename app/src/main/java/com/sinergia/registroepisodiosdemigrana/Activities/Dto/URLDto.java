package com.sinergia.registroepisodiosdemigrana.Activities.Dto;

/**
 * Created by juan on 9/07/17.
 */

public class URLDto {
    private int Id;
    private  String URL;

    public URLDto(){}


    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
}
