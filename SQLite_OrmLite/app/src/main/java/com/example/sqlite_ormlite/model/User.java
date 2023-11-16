package com.example.sqlite_ormlite.model;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable( tableName = "T_Users" )
public class User {

    @DatabaseField( columnName = "idUser", generatedId = true )
    private int idUser;

    @DatabaseField
    private String firstName;

    @DatabaseField
    private String lastName;

    @DatabaseField(unique = true)
    private String email;

    @DatabaseField
    private String pays;

    @DatabaseField
    private String motdepasse;

    public User() {}

    public User(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.motdepasse = getMotdepasse();
        this.pays = getPays();
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getMotdepasse() {
        return motdepasse;
    }

    public void setMotdepasse(String motdepasse) {
        this.motdepasse = motdepasse;
    }

    @Override
    public String toString() {
        return "(" + firstName + " " + lastName +" " + pays+ ")";
    }
}
