package com.salsabil.applicationecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.salsabil.applicationecommerce.entity.Panier;

public interface PanierRepository extends JpaRepository<Panier, Long> {
}