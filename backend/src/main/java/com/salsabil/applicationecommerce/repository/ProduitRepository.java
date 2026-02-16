package com.salsabil.applicationecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.salsabil.applicationecommerce.entity.Produit;

public interface ProduitRepository extends JpaRepository<Produit, Long> {
}