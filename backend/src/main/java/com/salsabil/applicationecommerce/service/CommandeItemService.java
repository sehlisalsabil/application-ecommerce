package com.salsabil.applicationecommerce.service;

import com.salsabil.applicationecommerce.entity.CommandeItem;
import com.salsabil.applicationecommerce.repository.CommandeItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CommandeItemService {

    @Autowired
    private CommandeItemRepository commandeItemRepository;

    /**
     * Récupère tous les items de commande
     * @return une liste de tous les items
     */
    public List<CommandeItem> getAllCommandeItems() {
        return commandeItemRepository.findAll();
    }

    /**
     * Récupère un item de commande par son ID
     * @param id l'identifiant de l'item
     * @return un Optional contenant l'item
     */
    public Optional<CommandeItem> getCommandeItemById(Long id) {
        return commandeItemRepository.findById(id);
    }

    /**
     * Crée un nouvel item de commande
     * @param item l'item à créer
     * @return l'item créé
     */
    public CommandeItem createCommandeItem(CommandeItem item) {
        if (item.getCommande() == null) {
            throw new IllegalArgumentException("Une commande doit être associée à cet item");
        }
        if (item.getProduit() == null) {
            throw new IllegalArgumentException("Un produit doit être associé à cet item");
        }
        if (item.getQuantite() == null || item.getQuantite() <= 0) {
            throw new IllegalArgumentException("La quantité doit être positive");
        }
        if (item.getPrixUnitaire() == null || item.getPrixUnitaire().signum() < 0) {
            throw new IllegalArgumentException("Le prix unitaire doit être positif");
        }
        
        // Calculer le sous-total
        BigDecimal sousTotal = item.getPrixUnitaire().multiply(new BigDecimal(item.getQuantite()));
        item.setSousTotal(sousTotal);
        
        return commandeItemRepository.save(item);
    }

    /**
     * Modifie un item de commande
     * @param id l'identifiant de l'item
     * @param itemDetails les nouvelles données
     * @return l'item modifié
     */
    public CommandeItem updateCommandeItem(Long id, CommandeItem itemDetails) {
        Optional<CommandeItem> item = commandeItemRepository.findById(id);
        if (item.isPresent()) {
            CommandeItem ci = item.get();
            
            if (itemDetails.getQuantite() != null && itemDetails.getQuantite() > 0) {
                ci.setQuantite(itemDetails.getQuantite());
            }
            if (itemDetails.getPrixUnitaire() != null && itemDetails.getPrixUnitaire().signum() >= 0) {
                ci.setPrixUnitaire(itemDetails.getPrixUnitaire());
            }
            
            // Recalculer le sous-total
            if (ci.getQuantite() != null && ci.getPrixUnitaire() != null) {
                BigDecimal sousTotal = ci.getPrixUnitaire().multiply(new BigDecimal(ci.getQuantite()));
                ci.setSousTotal(sousTotal);
            }
            
            return commandeItemRepository.save(ci);
        } else {
            throw new IllegalArgumentException("Item de commande non trouvé avec l'ID: " + id);
        }
    }

    /**
     * Supprime un item de commande
     * @param id l'identifiant de l'item
     */
    public void deleteCommandeItem(Long id) {
        if (!commandeItemRepository.existsById(id)) {
            throw new IllegalArgumentException("Item de commande non trouvé avec l'ID: " + id);
        }
        commandeItemRepository.deleteById(id);
    }

    /**
     * Récalcule le sous-total d'un item
     * @param id l'identifiant de l'item
     * @return l'item mis à jour
     */
    public CommandeItem recalculateSousTotal(Long id) {
        Optional<CommandeItem> item = commandeItemRepository.findById(id);
        if (item.isPresent()) {
            CommandeItem ci = item.get();
            if (ci.getQuantite() != null && ci.getPrixUnitaire() != null) {
                BigDecimal sousTotal = ci.getPrixUnitaire().multiply(new BigDecimal(ci.getQuantite()));
                ci.setSousTotal(sousTotal);
                return commandeItemRepository.save(ci);
            }
            return ci;
        } else {
            throw new IllegalArgumentException("Item de commande non trouvé avec l'ID: " + id);
        }
    }
}
