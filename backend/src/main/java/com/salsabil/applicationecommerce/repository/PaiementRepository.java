package com.salsabil.applicationecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.salsabil.applicationecommerce.entity.Paiement;

public interface PaiementRepository extends JpaRepository<Paiement, Long> {
}