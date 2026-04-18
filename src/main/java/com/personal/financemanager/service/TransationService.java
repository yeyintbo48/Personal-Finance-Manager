package com.personal.financemanager.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.personal.financemanager.TransationRequest;
import com.personal.financemanager.entity.Transation;
import com.personal.financemanager.repository.TransationRepo;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransationService {
    @Autowired
    private TransationRepo transationRepo;

    public Transation createTransation(TransationRequest request){
        Transation transation = new Transation();
        transation.setAmount(request.amount());
        transation.setDescription(request.description());
        transation.setDate(LocalDateTime.now());
        transation.setType(request.type());
        return transationRepo.save(transation);
    }

    public List<Transation> getAllTransation(){
        return transationRepo.findAll();
    }

    public Optional<Transation> getTransationById(Long id){
        return transationRepo.findById(id);
    }

    public Transation updateTransation(Long id,TransationRequest request){
        Transation existing  = transationRepo.findById(id).orElseThrow(()->new RuntimeException("Transcation not found!"));

        existing.setAmount(request.amount());
        existing.setDescription(request.description());
        existing.setType(request.type());
        return transationRepo.save(existing);
    }

    public void deleteTransation(Long id){
        transationRepo.deleteById(id);
    }
}
