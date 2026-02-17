package com.salsabil.applicationecommerce.service;

import com.salsabil.applicationecommerce.entity.PanierItem;
import com.salsabil.applicationecommerce.repository.PanierItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PanierItemService {

    @Autowired
    private PanierItemRepository panierItemRepository;

    /**
     * Récupère tous les items du panier
     * @return une liste de tous les items
     */
    public List<PanierItem> getAllPanierItems() {
        return panierItemRepository.findAll();
    }

    /**
     * Récupère un item du panier par son ID
     * @param id l'identifiant de l'item
     * @return un Optional contenant l'item
     */
    public Optional<PanierItem> getPanierItemById(Long id) {
        return panierItemRepository.findById(id);
    }

    /**
     * Crée un nouvel item dans le panier
     * @param item l'item à créer
     * @return l'item créé
     */
    public PanierItem createPanierItem(PanierItem item) {
        if (item.getPanier() == null) {
            throw new IllegalArgumentException("Un panier doit être associé à cet item");
        }
        if (item.getProduit() == null) {
            throw new IllegalArgumentException("Un produit doit être associé à cet item");
        }
        if (item.getQuantite() <= 0) {
            throw new IllegalArgumentException("La quantité doit être positive");
        }
        return panierItemRepository.save(item);
    }

    /**
     * Modifie un item du panier
     * @param id l'identifiant de l'item
     * @param itemDetails les nouvelles données
     * @return l'item modifié
     */
    public PanierItem updatePanierItem(Long id, PanierItem itemDetails) {
        Optional<PanierItem> item = panierItemRepository.findById(id);
        if (item.isPresent()) {
            PanierItem pi = item.get();
            
            if (itemDetails.getQuantite() > 0) {
                pi.setQuantite(itemDetails.getQuantite());
            }
            if (itemDetails.getProduit() != null) {
                pi.setProduit(itemDetails.getProduit());
            }
            
            return panierItemRepository.save(pi);
        } else {
            throw new IllegalArgumentException("Item du panier non trouvé avec l'ID: " + id);
        }
    }

    /**
     * Supprime un item du panier
     * @param id l'identifiant de l'item
     */
    public void deletePanierItem(Long id) {
        if (!panierItemRepository.existsById(id)) {
            throw new IllegalArgumentException("Item du panier non trouvé avec l'ID: " + id);
        }
        panierItemRepository.deleteById(id);
    }

    /**
     * Augmente la quantité d'un item
     * @param id l'identifiant de l'item
     * @param quantite la quantité à ajouter
     * @return l'item mis à jour
     */
    public PanierItem increaseQuantity(Long id, int quantite) {
        Optional<PanierItem> item = panierItemRepository.findById(id);
        if (item.isPresent()) {
            PanierItem pi = item.get();
            if (quantite <= 0) {
                throw new IllegalArgumentException("La quantité à ajouter doit être positive");
            }
            pi.setQuantite(pi.getQuantite() + quantite);
            return panierItemRepository.save(pi);
        } else {
            throw new IllegalArgumentException("Item du panier non trouvé avec l'ID: " + id);
        }
    }

    /**
     * Diminue la quantité d'un item
     * @param id l'identifiant de l'item
     * @param quantite la quantité à retirer
     * @return l'item mis à jour
     */
    public PanierItem decreaseQuantity(Long id, int quantite) {
        Optional<PanierItem> item = panierItemRepository.findById(id);
        if (item.isPresent()) {
            PanierItem pi = item.get();
            if (quantite <= 0) {
                throw new IllegalArgumentException("La quantité à retirer doit être positive");
            }
            if (pi.getQuantite() < quantite) {
                throw new IllegalArgumentException("La quantité à retirer est supérieure à la quantité disponible");
            }
            pi.setQuantite(pi.getQuantite() - quantite);
            return panierItemRepository.save(pi);
        } else {
            throw new IllegalArgumentException("Item du panier non trouvé avec l'ID: " + id);
        }
    }

    /**
     * Calcule le prix total d'un item
     * @param id l'identifiant de l'item
     * @return le prix total
     */
    public double calculateItemTotal(Long id) {
        Optional<PanierItem> item = panierItemRepository.findById(id);
        if (item.isPresent()) {
            PanierItem pi = item.get();
            return pi.getProduit().getPrix().doubleValue() * pi.getQuantite();
        } else {
            throw new IllegalArgumentException("Item du panier non trouvé avec l'ID: " + id);
        }
    }
}
