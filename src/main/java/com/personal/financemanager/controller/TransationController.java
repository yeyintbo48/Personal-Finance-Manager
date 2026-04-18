package com.personal.financemanager.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.personal.financemanager.TransationRequest;
import com.personal.financemanager.entity.Transation;
import com.personal.financemanager.service.TransationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/transations")
public class TransationController {
    @Autowired
    private TransationService transationService;

    @PostMapping("/add")
    public ResponseEntity<Transation> createTransation(@RequestBody @Valid TransationRequest request) {
        return ResponseEntity.ok(transationService.createTransation(request));
    } 
    
    @GetMapping
    public ResponseEntity <List<Transation>> getAllTransation() {
        return ResponseEntity.ok(transationService.getAllTransation());
    }

    @GetMapping("/{id}")
    public ResponseEntity <Optional<Transation>>getTransationById(@PathVariable Long id){
        return ResponseEntity.ok(transationService.getTransationById(id));
    }
    
    
    @PutMapping("/{id}")
    public ResponseEntity<Transation> updateTransation(@PathVariable Long id, @RequestBody @Valid TransationRequest request) {
        return ResponseEntity.ok(transationService.updateTransation(id,request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransation(@PathVariable Long id){
        transationService.deleteTransation(id);
        return ResponseEntity.noContent().build();
    }
}
