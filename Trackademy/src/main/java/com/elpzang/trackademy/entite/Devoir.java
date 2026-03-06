package com.elpzang.trackademy.entite;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "devoir")
public class Devoir {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titre", nullable = false)
    private String titre;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cours_id")
    private Cours cours;

    @Column(name = "date_remise")
    private LocalDate dateRemise;

    @Column(name = "description")
    private String description;

    // PLANIFIE, EN_COURS, TERMINE
    @Column(name = "statut")
    private String statut = "PLANIFIE";

    public Devoir() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public Cours getCours() { return cours; }
    public void setCours(Cours cours) { this.cours = cours; }

    public LocalDate getDateRemise() { return dateRemise; }
    public void setDateRemise(LocalDate dateRemise) { this.dateRemise = dateRemise; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
}
