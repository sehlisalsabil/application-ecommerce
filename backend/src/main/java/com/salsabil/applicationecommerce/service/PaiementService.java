package com.salsabil.applicationecommerce.service;

import com.salsabil.applicationecommerce.entity.Paiement;
import com.salsabil.applicationecommerce.repository.PaiementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PaiementService {

    @Autowired
    private PaiementRepository paiementRepository;

    /**
     * Récupère tous les paiements
     * @return une liste de tous les paiements
     */
    public List<Paiement> getAllPaiements() {
        return paiementRepository.findAll();
    }

    /**
     * Récupère un paiement par son ID
     * @param id l'identifiant du paiement
     * @return un Optional contenant le paiement
     */
    public Optional<Paiement> getPaiementById(Long id) {
        return paiementRepository.findById(id);
    }

    /**
     * Crée un nouveau paiement
     * @param paiement le paiement à créer
     * @return le paiement créé
     */
    public Paiement createPaiement(Paiement paiement) {
        if (paiement.getCommande() == null) {
            throw new IllegalArgumentException("Une commande doit être associée au paiement");
        }
        if (paiement.getMontant() <= 0) {
            throw new IllegalArgumentException("Le montant doit être positif");
        }
        if (paiement.getMethode() == null || paiement.getMethode().trim().isEmpty()) {
            throw new IllegalArgumentException("La méthode de paiement est obligatoire");
        }
        
        if (paiement.getDatePaiement() == null) {
            paiement.setDatePaiement(LocalDateTime.now());
        }
        if (paiement.getStatut() == null) {
            paiement.setStatut("en attente");
        }
        
        return paiementRepository.save(paiement);
    }

    /**
     * Modifie un paiement existant
     * @param id l'identifiant du paiement
     * @param paiementDetails les nouvelles données
     * @return le paiement modifié
     */
    public Paiement updatePaiement(Long id, Paiement paiementDetails) {
        Optional<Paiement> paiement = paiementRepository.findById(id);
        if (paiement.isPresent()) {
            Paiement p = paiement.get();
            
            if (paiementDetails.getMontant() > 0) {
                p.setMontant(paiementDetails.getMontant());
            }
            if (paiementDetails.getMethode() != null && !paiementDetails.getMethode().trim().isEmpty()) {
                p.setMethode(paiementDetails.getMethode());
            }
            if (paiementDetails.getStatut() != null && !paiementDetails.getStatut().trim().isEmpty()) {
                p.setStatut(paiementDetails.getStatut());
            }
            
            return paiementRepository.save(p);
        } else {
            throw new IllegalArgumentException("Paiement non trouvé avec l'ID: " + id);
        }
    }

    /**
     * Supprime un paiement
     * @param id l'identifiant du paiement
     */
    public void deletePaiement(Long id) {
        if (!paiementRepository.existsById(id)) {
            throw new IllegalArgumentException("Paiement non trouvé avec l'ID: " + id);
        }
        paiementRepository.deleteById(id);
    }

    /**
     * Confirme un paiement
     * @param id l'identifiant du paiement
     * @return le paiement confirmé
     */
    public Paiement confirmPaiement(Long id) {
        Optional<Paiement> paiement = paiementRepository.findById(id);
        if (paiement.isPresent()) {
            Paiement p = paiement.get();
            p.setStatut("confirmé");
            return paiementRepository.save(p);
        } else {
            throw new IllegalArgumentException("Paiement non trouvé avec l'ID: " + id);
        }
    }

    /**
     * Annule un paiement
     * @param id l'identifiant du paiement
     * @return le paiement annulé
     */
    public Paiement cancelPaiement(Long id) {
        Optional<Paiement> paiement = paiementRepository.findById(id);
        if (paiement.isPresent()) {
            Paiement p = paiement.get();
            p.setStatut("annulé");
            return paiementRepository.save(p);
        } else {
            throw new IllegalArgumentException("Paiement non trouvé avec l'ID: " + id);
        }
    }
}
