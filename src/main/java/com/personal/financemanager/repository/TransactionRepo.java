package com.personal.financemanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.personal.financemanager.entity.Transaction;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction,Long>{

}
