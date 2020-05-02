package com.sphe.phonecodepicker.model;

public class Country {

    private final String iso;
    private final String phoneCode;
    private final String name;

    public Country(String iso, String phoneCode, String name) {
        this.iso = iso;
        this.phoneCode = phoneCode;
        this.name = name;
    }

    public String getIso() {
        return this.iso;
    }

   public String getPhoneCode() {
        return this.phoneCode;
    }

   public  String getName() {
        return this.name;
    }

    /**
     * If country have query word in name or name code or phone code, this will return true.
     */
    public boolean isEligibleForQuery(String query) {
        query = query.toLowerCase();
        return getName().toLowerCase().contains(query) || getIso().toLowerCase().contains(query);

    }
}
