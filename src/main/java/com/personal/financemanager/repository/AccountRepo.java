package com.personal.financemanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.personal.financemanager.entity.Account;

public interface AccountRepo extends JpaRepository<Account,Long>{
    
}
