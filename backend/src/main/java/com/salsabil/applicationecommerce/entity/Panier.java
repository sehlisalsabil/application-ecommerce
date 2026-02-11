package com.salsabil.applicationecommerce.entity;

import jakarta.persistence.*;
import java.util.List;
import com.salsabil.applicationecommerce.entity.PanierItem; // Ajout de l'import manquant
import com.salsabil.applicationecommerce.entity.Utilisateur; // Ajout de l'import manquant

@Entity
public class Panier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;

    @OneToMany(mappedBy = "panier", cascade = CascadeType.ALL)
    private List<PanierItem> items;

    // Getters et setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Utilisateur getUtilisateur() { return utilisateur; }
    public void setUtilisateur(Utilisateur utilisateur) { this.utilisateur = utilisateur; }

    public List<PanierItem> getItems() { return items; }
    public void setItems(List<PanierItem> items) { this.items = items; }
}
