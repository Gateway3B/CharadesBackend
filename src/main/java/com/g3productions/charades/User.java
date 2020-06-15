package com.g3productions.charades;

import java.util.Random;

public class User {

    private String username;

    private boolean team;
    
    private boolean owner;

    User(String username, boolean owner) {
        this.username = username;
        this.owner = owner;
        team = new Random().nextBoolean();
    }

    public void setTeam(boolean team) {
        this.team = team;
    }

    public boolean getTeam() {
        return this.team;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }

    public boolean getOwner() {
        return this.owner;
    }

    public String getUsername() {
        return this.username;
    }
    
}