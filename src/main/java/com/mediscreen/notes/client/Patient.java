package com.mediscreen.notes.client;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Patient {

    private Integer id;

    private String nom;

    private String prenom;

    private String dateDeNaissance;

    private String genre;

    private String adressePostale;

    private String numeroDeTelephone;
}
