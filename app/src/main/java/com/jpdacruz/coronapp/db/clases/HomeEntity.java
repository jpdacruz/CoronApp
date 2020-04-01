package com.jpdacruz.coronapp.db.clases;

public class HomeEntity {

    private String cases;
    private String deaths;
    private String recovered;
    private String updated;
    private String active;
    private String affectedCountries;

    public HomeEntity() {
    }

    public String getCases() {
        return cases;
    }

    public void setCases(String cases) {
        this.cases = cases;
    }

    public String getDeaths() {
        return deaths;
    }

    public void setDeaths(String deaths) {
        this.deaths = deaths;
    }

    public String getRecovered() {
        return recovered;
    }

    public void setRecovered(String recovered) {
        this.recovered = recovered;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getAffectedCountries() {
        return affectedCountries;
    }

    public void setAffectedCountries(String affectedCountries) {
        this.affectedCountries = affectedCountries;
    }
}

