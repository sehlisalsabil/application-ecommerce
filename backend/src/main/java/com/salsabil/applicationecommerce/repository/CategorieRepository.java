package com.salsabil.applicationecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.salsabil.applicationecommerce.entity.Categorie;

public interface CategorieRepository extends JpaRepository<Categorie, Long> {
}