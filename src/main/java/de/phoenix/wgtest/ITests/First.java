package de.phoenix.wgtest.ITests;

public class First implements A {

    private Long id;

    public First(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
