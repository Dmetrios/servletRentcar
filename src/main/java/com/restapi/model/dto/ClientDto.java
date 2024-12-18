package com.restapi.model.dto;

public class ClientDto {
    private Long id;
    private String firstname;
    private String surname;
    private String number;
    private String pass;

    public ClientDto(Long id, String firstname, String surname, String number, String pass) {
        this.id = id;
        this.firstname = firstname;
        this.surname = surname;
        this.number = number;
        this.pass = pass;
    }

    public ClientDto(Long id) {
        this.id = id;
    }

    public ClientDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
