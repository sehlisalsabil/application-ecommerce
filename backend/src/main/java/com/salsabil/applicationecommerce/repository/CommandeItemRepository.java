package com.salsabil.applicationecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.salsabil.applicationecommerce.entity.CommandeItem;

public interface CommandeItemRepository extends JpaRepository<CommandeItem, Long> {
}