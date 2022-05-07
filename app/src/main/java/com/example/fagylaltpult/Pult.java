package com.example.fagylaltpult;

import java.util.Date;

public class Pult {
    private String id;
    private String fagyiNeve;

    public Pult(){}

    public Pult(String fagyiNeve) {
        this.fagyiNeve = fagyiNeve;
    }


    public String _getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFagyiNeve() {
        return fagyiNeve;
    }

    public void setFagyiNeve(String fagyiNeve) {
        this.fagyiNeve = fagyiNeve;
    }
}
