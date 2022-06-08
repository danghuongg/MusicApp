package com.danghuong.musicapp.data.model;

public class Language {

    private String codeLocale;
    private String nameLanguage;

    public Language() {
    }


    public Language(String codeLocale, String nameLanguage) {
        this.codeLocale = codeLocale;
        this.nameLanguage = nameLanguage;
    }

    public String getCodeLocale() {
        return codeLocale;
    }

    public void setCodeLocale(String codeLocale) {
        this.codeLocale = codeLocale;
    }

    public String getNameLanguage() {
        return nameLanguage;
    }

    public void setNameLanguage(String nameLanguage) {
        this.nameLanguage = nameLanguage;
    }
}
