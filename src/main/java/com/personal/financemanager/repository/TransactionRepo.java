package com.personal.financemanager.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.personal.financemanager.dtos.Category;
import com.personal.financemanager.entity.Transaction;
import java.util.List;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction,Long>{
     List<Transaction> findByUserIdAndCategory(Long userId,Category category);
     List<Transaction> findByUserId(Long userId);
     Page<Transaction> findByUserId(Long useId,PageRequest pageable);
}
