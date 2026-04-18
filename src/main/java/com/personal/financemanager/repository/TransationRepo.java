package com.personal.financemanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.personal.financemanager.entity.Transation;

@Repository
public interface TransationRepo extends JpaRepository<Transation,Long>{

}
