package com.elpzang.trackademy.entite;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "evenement")
public class Evenement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titre", nullable = false)
    private String titre;

    @Column(name = "date_evenement")
    private LocalDate date;

    // COURS, EXAMEN, DEVOIR, AUTRE
    @Column(name = "type")
    private String type;

    @Column(name = "description")
    private String description;

    public Evenement() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
