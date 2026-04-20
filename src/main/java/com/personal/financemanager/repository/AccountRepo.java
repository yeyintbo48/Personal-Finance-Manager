package com.personal.financemanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.personal.financemanager.entity.Account;
import java.util.List;
import java.util.Optional;


public interface AccountRepo extends JpaRepository<Account,Long>{
    
}
