package com.training.lab.repository;

import com.training.lab.entity.NiftyStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NiftyStockRepository extends JpaRepository<NiftyStock, Long> {
}
