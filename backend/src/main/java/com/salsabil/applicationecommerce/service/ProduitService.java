package com.salsabil.applicationecommerce.service;

import com.salsabil.applicationecommerce.entity.Produit;
import com.salsabil.applicationecommerce.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProduitService {

    @Autowired
    private ProduitRepository produitRepository;

    /**
     * Récupère tous les produits
     * @return une liste de tous les produits
     */
    public List<Produit> getAllProduits() {
        return produitRepository.findAll();
    }

    /**
     * Récupère un produit par son ID
     * @param id l'identifiant du produit
     * @return un Optional contenant le produit s'il existe
     */
    public Optional<Produit> getProduitById(Long id) {
        return produitRepository.findById(id);
    }

    /**
     * Crée un nouveau produit
     * @param produit le produit à créer
     * @return le produit créé
     */
    public Produit createProduit(Produit produit) {
        if (produit.getStock() < 0) {
            throw new IllegalArgumentException("Le stock ne peut pas être négatif");
        }
        if (produit.getPrix() == null || produit.getPrix().signum() < 0) {
            throw new IllegalArgumentException("Le prix doit être positif");
        }
        return produitRepository.save(produit);
    }

    /**
     * Modifie un produit existant
     * @param id l'identifiant du produit à modifier
     * @param produitDetails les nouvelles données du produit
     * @return le produit modifié
     */
    public Produit updateProduit(Long id, Produit produitDetails) {
        Optional<Produit> produit = produitRepository.findById(id);
        if (produit.isPresent()) {
            Produit p = produit.get();
            
            if (produitDetails.getNom() != null) {
                p.setNom(produitDetails.getNom());
            }
            if (produitDetails.getDescription() != null) {
                p.setDescription(produitDetails.getDescription());
            }
            if (produitDetails.getPrix() != null) {
                if (produitDetails.getPrix().signum() < 0) {
                    throw new IllegalArgumentException("Le prix doit être positif");
                }
                p.setPrix(produitDetails.getPrix());
            }
            if (produitDetails.getStock() >= 0) {
                p.setStock(produitDetails.getStock());
            }
            if (produitDetails.getCategorie() != null) {
                p.setCategorie(produitDetails.getCategorie());
            }
            
            return produitRepository.save(p);
        } else {
            throw new IllegalArgumentException("Produit non trouvé avec l'ID: " + id);
        }
    }

    /**
     * Supprime un produit par son ID
     * @param id l'identifiant du produit à supprimer
     */
    public void deleteProduit(Long id) {
        if (!produitRepository.existsById(id)) {
            throw new IllegalArgumentException("Produit non trouvé avec l'ID: " + id);
        }
        produitRepository.deleteById(id);
    }

    /**
     * Réduit le stock d'un produit
     * @param id l'identifiant du produit
     * @param quantite la quantité à réduire
     * @return le produit mis à jour
     */
    public Produit reduireStock(Long id, int quantite) {
        Optional<Produit> produit = produitRepository.findById(id);
        if (produit.isPresent()) {
            Produit p = produit.get();
            if (p.getStock() < quantite) {
                throw new IllegalArgumentException("Stock insuffisant pour le produit: " + p.getNom());
            }
            p.setStock(p.getStock() - quantite);
            return produitRepository.save(p);
        } else {
            throw new IllegalArgumentException("Produit non trouvé avec l'ID: " + id);
        }
    }

    /**
     * Augmente le stock d'un produit
     * @param id l'identifiant du produit
     * @param quantite la quantité à ajouter
     * @return le produit mis à jour
     */
    public Produit augmenterStock(Long id, int quantite) {
        Optional<Produit> produit = produitRepository.findById(id);
        if (produit.isPresent()) {
            Produit p = produit.get();
            if (quantite < 0) {
                throw new IllegalArgumentException("La quantité à ajouter doit être positive");
            }
            p.setStock(p.getStock() + quantite);
            return produitRepository.save(p);
        } else {
            throw new IllegalArgumentException("Produit non trouvé avec l'ID: " + id);
        }
    }

    /**
     * Vérifie si un produit a suffisamment de stock
     * @param id l'identifiant du produit
     * @param quantite la quantité demandée
     * @return true si le stock est suffisant, false sinon
     */
    public boolean estDisponible(Long id, int quantite) {
        Optional<Produit> produit = produitRepository.findById(id);
        return produit.isPresent() && produit.get().getStock() >= quantite;
    }
}
