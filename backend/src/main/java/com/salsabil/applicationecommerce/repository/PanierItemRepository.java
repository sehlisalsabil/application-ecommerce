package com.salsabil.applicationecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.salsabil.applicationecommerce.entity.PanierItem;

public interface PanierItemRepository extends JpaRepository<PanierItem, Long> {
}