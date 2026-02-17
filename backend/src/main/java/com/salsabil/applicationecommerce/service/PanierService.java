package com.salsabil.applicationecommerce.service;

import com.salsabil.applicationecommerce.entity.Panier;
import com.salsabil.applicationecommerce.entity.PanierItem;
import com.salsabil.applicationecommerce.repository.PanierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class PanierService {

    @Autowired
    private PanierRepository panierRepository;

    /**
     * Récupère tous les paniers
     * @return une liste de tous les paniers
     */
    public List<Panier> getAllPaniers() {
        return panierRepository.findAll();
    }

    /**
     * Récupère un panier par son ID
     * @param id l'identifiant du panier
     * @return un Optional contenant le panier
     */
    public Optional<Panier> getPanierById(Long id) {
        return panierRepository.findById(id);
    }

    /**
     * Crée un nouveau panier
     * @param panier le panier à créer
     * @return le panier créé
     */
    public Panier createPanier(Panier panier) {
        if (panier.getUtilisateur() == null) {
            throw new IllegalArgumentException("Un utilisateur doit être associé au panier");
        }
        return panierRepository.save(panier);
    }

    /**
     * Modifie un panier existant
     * @param id l'identifiant du panier
     * @param panierDetails les nouvelles données
     * @return le panier modifié
     */
    public Panier updatePanier(Long id, Panier panierDetails) {
        Optional<Panier> panier = panierRepository.findById(id);
        if (panier.isPresent()) {
            Panier p = panier.get();
            
            if (panierDetails.getUtilisateur() != null) {
                p.setUtilisateur(panierDetails.getUtilisateur());
            }
            if (panierDetails.getItems() != null) {
                p.setItems(panierDetails.getItems());
            }
            
            return panierRepository.save(p);
        } else {
            throw new IllegalArgumentException("Panier non trouvé avec l'ID: " + id);
        }
    }

    /**
     * Supprime un panier
     * @param id l'identifiant du panier
     */
    public void deletePanier(Long id) {
        if (!panierRepository.existsById(id)) {
            throw new IllegalArgumentException("Panier non trouvé avec l'ID: " + id);
        }
        panierRepository.deleteById(id);
    }

    /**
     * Vide le panier en supprimant tous les items
     * @param id l'identifiant du panier
     * @return le panier vidé
     */
    public Panier viderPanier(Long id) {
        Optional<Panier> panier = panierRepository.findById(id);
        if (panier.isPresent()) {
            Panier p = panier.get();
            p.setItems(null);
            return panierRepository.save(p);
        } else {
            throw new IllegalArgumentException("Panier non trouvé avec l'ID: " + id);
        }
    }

    /**
     * Calcule le montant total du panier
     * @param id l'identifiant du panier
     * @return le montant total
     */
    public double calculateTotal(Long id) {
        Optional<Panier> panier = panierRepository.findById(id);
        if (panier.isPresent()) {
            List<PanierItem> items = panier.get().getItems();
            if (items != null && !items.isEmpty()) {
                return items.stream()
                        .mapToDouble(item -> item.getProduit().getPrix().doubleValue() * item.getQuantite())
                        .sum();
            }
            return 0.0;
        } else {
            throw new IllegalArgumentException("Panier non trouvé avec l'ID: " + id);
        }
    }

    /**
     * Compte le nombre total d'articles dans le panier
     * @param id l'identifiant du panier
     * @return le nombre d'articles
     */
    public int countItems(Long id) {
        Optional<Panier> panier = panierRepository.findById(id);
        if (panier.isPresent()) {
            List<PanierItem> items = panier.get().getItems();
            if (items != null) {
                return items.stream()
                        .mapToInt(PanierItem::getQuantite)
                        .sum();
            }
            return 0;
        } else {
            throw new IllegalArgumentException("Panier non trouvé avec l'ID: " + id);
        }
    }
}
