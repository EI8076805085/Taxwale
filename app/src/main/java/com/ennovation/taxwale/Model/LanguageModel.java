package com.ennovation.taxwale.Model;

public class LanguageModel {
    String languageCode;
    String langName;
    int image;

    public LanguageModel(String languageCode, String langName, int image) {
        this.languageCode = languageCode;
        this.langName = langName;
        this.image = image;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getLangName() {
        return langName;
    }

    public void setLangName(String langName) {
        this.langName = langName;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
