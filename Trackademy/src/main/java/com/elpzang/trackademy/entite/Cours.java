package com.elpzang.trackademy.entite;

import jakarta.persistence.*;

@Entity
@Table(name = "cours")
public class Cours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "professeur")
    private String professeur;

    @Column(name = "horaire")
    private String horaire;

    @Column(name = "salle")
    private String salle;

    @Column(name = "credits")
    private int credits;

    @Column(name = "couleur")
    private String couleur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "etudiant_id")
    private Etudiant etudiant;

    public Cours() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getProfesseur() { return professeur; }
    public void setProfesseur(String professeur) { this.professeur = professeur; }

    public String getHoraire() { return horaire; }
    public void setHoraire(String horaire) { this.horaire = horaire; }

    public String getSalle() { return salle; }
    public void setSalle(String salle) { this.salle = salle; }

    public int getCredits() { return credits; }
    public void setCredits(int credits) { this.credits = credits; }

    public String getCouleur() { return couleur; }
    public void setCouleur(String couleur) { this.couleur = couleur; }

    public Etudiant getEtudiant() { return etudiant; }
    public void setEtudiant(Etudiant etudiant) { this.etudiant = etudiant; }
}
