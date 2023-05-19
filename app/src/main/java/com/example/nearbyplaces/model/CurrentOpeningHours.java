package com.example.nearbyplaces.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CurrentOpeningHours {
    @SerializedName("open_now")
    @Expose
    private Boolean openNow;
//    @SerializedName("periods")
//    @Expose
//    private List<Period> periods;
    @SerializedName("weekday_text")
    @Expose
    private List<String> weekdayText;

    public Boolean getOpenNow() {
        return openNow;
    }

    public void setOpenNow(Boolean openNow) {
        this.openNow = openNow;
    }

//    public List<Period> getPeriods() {
//        return periods;
//    }

//    public void setPeriods(List<Period> periods) {
//        this.periods = periods;
//    }

    public List<String> getWeekdayText() {
        return weekdayText;
    }

    public void setWeekdayText(List<String> weekdayText) {
        this.weekdayText = weekdayText;
    }
}
