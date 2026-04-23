package com.personal.financemanager.repository;

import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.personal.financemanager.entity.Budget;

public interface BudgetRepo extends JpaRepository<Budget,Long>{
    Optional<Budget> findByCategoryAndDate(String category, LocalDate now);
}
