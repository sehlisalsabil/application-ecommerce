package com.salsabil.applicationecommerce.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.salsabil.applicationecommerce.entity.Commande; // Ajout de l'import manquant

@Entity
public class Paiement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double montant;

    private LocalDateTime datePaiement;

    private String methode; // ex: carte, paypal, etc.

    @OneToOne
    @JoinColumn(name = "commande_id")
    private Commande commande;

    private String statut;

    // Getters et setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public double getMontant() { return montant; }
    public void setMontant(double montant) { this.montant = montant; }

    public LocalDateTime getDatePaiement() { return datePaiement; }
    public void setDatePaiement(LocalDateTime datePaiement) { this.datePaiement = datePaiement; }

    public String getMethode() { return methode; }
    public void setMethode(String methode) { this.methode = methode; }

    public Commande getCommande() { return commande; }
    public void setCommande(Commande commande) { this.commande = commande; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
}
