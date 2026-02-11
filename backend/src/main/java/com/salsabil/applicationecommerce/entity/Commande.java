package com.salsabil.applicationecommerce.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import com.salsabil.applicationecommerce.entity.CommandeItem; // Ajout de l'import manquant
import com.salsabil.applicationecommerce.entity.Utilisateur; // Ajout de l'import manquant

@Entity
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateCommande;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL)
    private List<CommandeItem> items;

    private String statut;

    // Getters et setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getDateCommande() { return dateCommande; }
    public void setDateCommande(LocalDateTime dateCommande) { this.dateCommande = dateCommande; }

    public Utilisateur getUtilisateur() { return utilisateur; }
    public void setUtilisateur(Utilisateur utilisateur) { this.utilisateur = utilisateur; }

    public List<CommandeItem> getItems() { return items; }
    public void setItems(List<CommandeItem> items) { this.items = items; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
}
