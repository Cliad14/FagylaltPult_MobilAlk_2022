package com.example.fagylaltpult;

public class Fagylalt {
    private String nev;
    private boolean cukormentes;
    private boolean laktozmentes;
    private boolean glutenmentes;
    private boolean gyumolcsfagylalt;
    private String alap; // tej/viz
    private String hozzavalok;
    private int imageResource;
    private String id;


    public Fagylalt() {
    }

    public Fagylalt(String nev, int imageResource) {
        this.nev = nev;
        this.imageResource = imageResource;

        this.cukormentes = false;
        this.laktozmentes = false;
        this.glutenmentes = false;
        this.gyumolcsfagylalt = false;
        this.alap = "PASS";
        this.hozzavalok = "";

    }

    public Fagylalt(String nev, boolean cukormentes, boolean laktozmentes, boolean glutenmentes, boolean gyumolcsfagylalt, String alap, String hozzavalok, int imageResource) {
        this.nev = nev;
        this.cukormentes = cukormentes;
        this.laktozmentes = laktozmentes;
        this.glutenmentes = glutenmentes;
        this.gyumolcsfagylalt = gyumolcsfagylalt;
        this.alap = alap;
        this.hozzavalok = hozzavalok;
        this.imageResource = imageResource;
    }

    public String getNev() {return nev;}
    public void setNev(String nev) {this.nev = nev;}

    public boolean isCukormentes() {return cukormentes;}
    public void setCukormentes(boolean cukormentes) {this.cukormentes = cukormentes;}

    public boolean isLaktozmentes() {return laktozmentes;}
    public void setLaktozmentes(boolean laktozmentes) {this.laktozmentes = laktozmentes;}

    public boolean isGlutenmentes() {return glutenmentes;}
    public void setGlutenmentes(boolean glutenmentes) {this.glutenmentes = glutenmentes;}

    public boolean isGyumolcsfagylalt() {return gyumolcsfagylalt;}
    public void setGyumolcsfagylalt(boolean gyumolcsfagylalt) {this.gyumolcsfagylalt = gyumolcsfagylalt;}

    public String getAlap() {return alap;}
    public void setAlap(String alap) {this.alap = alap;}

    public String getHozzavalok() {return hozzavalok;}
    public void setHozzavalok(String hozzavalok) {this.hozzavalok = hozzavalok;}

    public int getImageResource() {return imageResource;}
    public String _getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
