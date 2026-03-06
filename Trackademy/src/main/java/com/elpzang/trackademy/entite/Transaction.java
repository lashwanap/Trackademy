package com.elpzang.trackademy.entite;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "transaction_budget")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "montant", precision = 10, scale = 2)
    private BigDecimal montant;

    // REVENU, DEPENSE
    @Column(name = "type")
    private String type;

    // Loyer, Nourriture, Transport, Loisirs, Études, Autre
    @Column(name = "categorie")
    private String categorie;

    @Column(name = "date_transaction")
    private LocalDate date;

    public Transaction() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getMontant() { return montant; }
    public void setMontant(BigDecimal montant) { this.montant = montant; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getCategorie() { return categorie; }
    public void setCategorie(String categorie) { this.categorie = categorie; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
}
