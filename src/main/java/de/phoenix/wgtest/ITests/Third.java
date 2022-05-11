package de.phoenix.wgtest.ITests;

import java.util.Date;

public class Third implements A {

    private Date date;

    public Third(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
