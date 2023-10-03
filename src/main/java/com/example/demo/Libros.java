package com.example.demo;

public class Libros {
    public String code;
    public String authorname;
    public String namebook;
    public String date;
    public String bussines;

    public Libros(String code, String authorname, String namebook, String date, String bussines) {
        this.code = code;
        this.authorname = authorname;
        this.namebook = namebook;
        this.date = date;
        this.bussines = bussines;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAuthorname() {
        return authorname;
    }

    public void setAuthorname(String authorname) {
        this.authorname = authorname;
    }

    public String getNamebook() {
        return namebook;
    }

    public void setNamebook(String namebook) {
        this.namebook = namebook;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBussines() {
        return bussines;
    }

    public void setBussines(String bussines) {
        this.bussines = bussines;
    }
}
