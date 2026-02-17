package com.salsabil.applicationecommerce.service;

import com.salsabil.applicationecommerce.entity.Utilisateur;
import com.salsabil.applicationecommerce.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    /**
     * Récupère tous les utilisateurs
     * @return une liste de tous les utilisateurs
     */
    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurRepository.findAll();
    }

    /**
     * Récupère un utilisateur par son ID
     * @param id l'identifiant de l'utilisateur
     * @return un Optional contenant l'utilisateur
     */
    public Optional<Utilisateur> getUtilisateurById(Long id) {
        return utilisateurRepository.findById(id);
    }

    /**
     * Crée un nouvel utilisateur
     * @param utilisateur l'utilisateur à créer
     * @return l'utilisateur créé
     */
    public Utilisateur createUtilisateur(Utilisateur utilisateur) {
        if (utilisateur.getNom() == null || utilisateur.getNom().trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom de l'utilisateur est obligatoire");
        }
        if (utilisateur.getEmail() == null || utilisateur.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("L'email de l'utilisateur est obligatoire");
        }
        if (!isValidEmail(utilisateur.getEmail())) {
            throw new IllegalArgumentException("L'email n'est pas valide");
        }
        if (utilisateur.getMotDePasse() == null || utilisateur.getMotDePasse().trim().isEmpty()) {
            throw new IllegalArgumentException("Le mot de passe est obligatoire");
        }
        return utilisateurRepository.save(utilisateur);
    }

    /**
     * Modifie un utilisateur existant
     * @param id l'identifiant de l'utilisateur
     * @param utilisateurDetails les nouvelles données
     * @return l'utilisateur modifié
     */
    public Utilisateur updateUtilisateur(Long id, Utilisateur utilisateurDetails) {
        Optional<Utilisateur> utilisateur = utilisateurRepository.findById(id);
        if (utilisateur.isPresent()) {
            Utilisateur u = utilisateur.get();
            
            if (utilisateurDetails.getNom() != null && !utilisateurDetails.getNom().trim().isEmpty()) {
                u.setNom(utilisateurDetails.getNom());
            }
            if (utilisateurDetails.getEmail() != null && !utilisateurDetails.getEmail().trim().isEmpty()) {
                if (!isValidEmail(utilisateurDetails.getEmail())) {
                    throw new IllegalArgumentException("L'email n'est pas valide");
                }
                u.setEmail(utilisateurDetails.getEmail());
            }
            if (utilisateurDetails.getMotDePasse() != null && !utilisateurDetails.getMotDePasse().trim().isEmpty()) {
                u.setMotDePasse(utilisateurDetails.getMotDePasse());
            }
            
            return utilisateurRepository.save(u);
        } else {
            throw new IllegalArgumentException("Utilisateur non trouvé avec l'ID: " + id);
        }
    }

    /**
     * Supprime un utilisateur
     * @param id l'identifiant de l'utilisateur
     */
    public void deleteUtilisateur(Long id) {
        if (!utilisateurRepository.existsById(id)) {
            throw new IllegalArgumentException("Utilisateur non trouvé avec l'ID: " + id);
        }
        utilisateurRepository.deleteById(id);
    }

    /**
     * Valide l'email et le mot de passe de l'utilisateur
     * @param email l'email de l'utilisateur
     * @param motDePasse le mot de passe de l'utilisateur
     * @return l'utilisateur s'il existe et les identifiants sont corrects
     */
    public Optional<Utilisateur> authenticate(String email, String motDePasse) {
        List<Utilisateur> utilisateurs = utilisateurRepository.findAll();
        return utilisateurs.stream()
                .filter(u -> u.getEmail().equals(email) && u.getMotDePasse().equals(motDePasse))
                .findFirst();
    }

    /**
     * Valide le format d'un email
     * @param email l'email à valider
     * @return true si l'email est valide
     */
    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    /**
     * Modifie le mot de passe d'un utilisateur
     * @param id l'identifiant de l'utilisateur
     * @param ancienMotDePasse l'ancien mot de passe
     * @param nouveauMotDePasse le nouveau mot de passe
     * @return l'utilisateur modifié
     */
    public Utilisateur changePassword(Long id, String ancienMotDePasse, String nouveauMotDePasse) {
        Optional<Utilisateur> utilisateur = utilisateurRepository.findById(id);
        if (utilisateur.isPresent()) {
            Utilisateur u = utilisateur.get();
            if (!u.getMotDePasse().equals(ancienMotDePasse)) {
                throw new IllegalArgumentException("L'ancien mot de passe est incorrect");
            }
            if (nouveauMotDePasse == null || nouveauMotDePasse.trim().isEmpty()) {
                throw new IllegalArgumentException("Le nouveau mot de passe ne peut pas être vide");
            }
            u.setMotDePasse(nouveauMotDePasse);
            return utilisateurRepository.save(u);
        } else {
            throw new IllegalArgumentException("Utilisateur non trouvé avec l'ID: " + id);
        }
    }
}
