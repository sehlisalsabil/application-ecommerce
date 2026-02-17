package com.salsabil.applicationecommerce.service;

import com.salsabil.applicationecommerce.entity.Commande;
import com.salsabil.applicationecommerce.entity.CommandeItem;
import com.salsabil.applicationecommerce.repository.CommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommandeService {

    @Autowired
    private CommandeRepository commandeRepository;

    /**
     * Récupère toutes les commandes
     * @return une liste de toutes les commandes
     */
    public List<Commande> getAllCommandes() {
        return commandeRepository.findAll();
    }

    /**
     * Récupère une commande par son ID
     * @param id l'identifiant de la commande
     * @return un Optional contenant la commande
     */
    public Optional<Commande> getCommandeById(Long id) {
        return commandeRepository.findById(id);
    }

    /**
     * Crée une nouvelle commande
     * @param commande la commande à créer
     * @return la commande créée
     */
    public Commande createCommande(Commande commande) {
        if (commande.getUtilisateur() == null) {
            throw new IllegalArgumentException("Un utilisateur doit être associé à la commande");
        }
        if (commande.getDateCommande() == null) {
            commande.setDateCommande(LocalDateTime.now());
        }
        if (commande.getStatut() == null) {
            commande.setStatut("en attente");
        }
        return commandeRepository.save(commande);
    }

    /**
     * Modifie une commande existante
     * @param id l'identifiant de la commande
     * @param commandeDetails les nouvelles données
     * @return la commande modifiée
     */
    public Commande updateCommande(Long id, Commande commandeDetails) {
        Optional<Commande> commande = commandeRepository.findById(id);
        if (commande.isPresent()) {
            Commande c = commande.get();
            
            if (commandeDetails.getStatut() != null && !commandeDetails.getStatut().trim().isEmpty()) {
                c.setStatut(commandeDetails.getStatut());
            }
            if (commandeDetails.getUtilisateur() != null) {
                c.setUtilisateur(commandeDetails.getUtilisateur());
            }
            if (commandeDetails.getItems() != null) {
                c.setItems(commandeDetails.getItems());
            }
            
            return commandeRepository.save(c);
        } else {
            throw new IllegalArgumentException("Commande non trouvée avec l'ID: " + id);
        }
    }

    /**
     * Supprime une commande
     * @param id l'identifiant de la commande
     */
    public void deleteCommande(Long id) {
        if (!commandeRepository.existsById(id)) {
            throw new IllegalArgumentException("Commande non trouvée avec l'ID: " + id);
        }
        commandeRepository.deleteById(id);
    }

    /**
     * Modifie le statut d'une commande
     * @param id l'identifiant de la commande
     * @param statut le nouveau statut
     * @return la commande modifiée
     */
    public Commande updateStatut(Long id, String statut) {
        Optional<Commande> commande = commandeRepository.findById(id);
        if (commande.isPresent()) {
            Commande c = commande.get();
            c.setStatut(statut);
            return commandeRepository.save(c);
        } else {
            throw new IllegalArgumentException("Commande non trouvée avec l'ID: " + id);
        }
    }

    /**
     * Calcule le montant total d'une commande
     * @param id l'identifiant de la commande
     * @return le montant total
     */
    public double calculateTotal(Long id) {
        Optional<Commande> commande = commandeRepository.findById(id);
        if (commande.isPresent()) {
            List<CommandeItem> items = commande.get().getItems();
            if (items != null) {
                return items.stream()
                        .mapToDouble(item -> item.getSousTotal().doubleValue())
                        .sum();
            }
            return 0.0;
        } else {
            throw new IllegalArgumentException("Commande non trouvée avec l'ID: " + id);
        }
    }
}
