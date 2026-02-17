package com.salsabil.applicationecommerce.service;

import com.salsabil.applicationecommerce.entity.Categorie;
import com.salsabil.applicationecommerce.repository.CategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CategorieService {

    @Autowired
    private CategorieRepository categorieRepository;

    /**
     * Récupère toutes les catégories
     * @return une liste de toutes les catégories
     */
    public List<Categorie> getAllCategories() {
        return categorieRepository.findAll();
    }

    /**
     * Récupère une catégorie par son ID
     * @param id l'identifiant de la catégorie
     * @return un Optional contenant la catégorie
     */
    public Optional<Categorie> getCategorieById(Long id) {
        return categorieRepository.findById(id);
    }

    /**
     * Crée une nouvelle catégorie
     * @param categorie la catégorie à créer
     * @return la catégorie créée
     */
    public Categorie createCategorie(Categorie categorie) {
        if (categorie.getNom() == null || categorie.getNom().trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom de la catégorie ne peut pas être vide");
        }
        return categorieRepository.save(categorie);
    }

    /**
     * Modifie une catégorie existante
     * @param id l'identifiant de la catégorie
     * @param categorieDetails les nouvelles données
     * @return la catégorie modifiée
     */
    public Categorie updateCategorie(Long id, Categorie categorieDetails) {
        Optional<Categorie> categorie = categorieRepository.findById(id);
        if (categorie.isPresent()) {
            Categorie c = categorie.get();
            if (categorieDetails.getNom() != null && !categorieDetails.getNom().trim().isEmpty()) {
                c.setNom(categorieDetails.getNom());
            }
            return categorieRepository.save(c);
        } else {
            throw new IllegalArgumentException("Catégorie non trouvée avec l'ID: " + id);
        }
    }

    /**
     * Supprime une catégorie
     * @param id l'identifiant de la catégorie
     */
    public void deleteCategorie(Long id) {
        if (!categorieRepository.existsById(id)) {
            throw new IllegalArgumentException("Catégorie non trouvée avec l'ID: " + id);
        }
        categorieRepository.deleteById(id);
    }
}
