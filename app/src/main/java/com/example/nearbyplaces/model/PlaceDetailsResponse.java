package com.example.nearbyplaces.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlaceDetailsResponse {

    @SerializedName("html_attributions")
    @Expose
    private List<Object> htmlAttributions;
    @SerializedName("result")
    @Expose
    private PlaceDetails placeDetails;
    @SerializedName("status")
    @Expose
    private String status;

    public List<Object> getHtmlAttributions() {
        return htmlAttributions;
    }

    public void setHtmlAttributions(List<Object> htmlAttributions) {
        this.htmlAttributions = htmlAttributions;
    }

    public PlaceDetails getPlaceDeatails() {
        return placeDetails;
    }

    public void setResult(PlaceDetails details) {
        this.placeDetails = details;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
