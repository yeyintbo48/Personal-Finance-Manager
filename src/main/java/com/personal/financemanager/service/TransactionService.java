package com.personal.financemanager.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.personal.financemanager.entity.Account;
import com.personal.financemanager.entity.Transaction;
import com.personal.financemanager.dtos.TransactionRequest;
import com.personal.financemanager.entity.User;
import com.personal.financemanager.repository.AccountRepo;
import com.personal.financemanager.repository.TransactionRepo;
import com.personal.financemanager.repository.UserRepo;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Data
public class TransactionService {
    private final TransactionRepo transactionRepo;
    private final UserRepo userRepo;
    private final AccountRepo accountRepo;

    public Transaction createTransaction(TransactionRequest request){
        User user = userRepo.findById(request.userId()).orElseThrow(()-> new RuntimeException("User not found!"));
        Account account = accountRepo.findById(request.accountId()).orElseThrow(() -> new RuntimeException("Account not found"));
        Transaction transaction = new Transaction();
        transaction.setAmount(request.amount());
        transaction.setDescription(request.description());
        transaction.setDate(LocalDateTime.now());
        transaction.setType(request.type());
        transaction.setUser(user);
        transaction.setAccount(account);
        return transactionRepo.save(transaction);
    }

    public List<Transaction> getAllTransaction(){
        return transactionRepo.findAll();
    }

    public Optional<Transaction> getTransactionById(Long id){
        return transactionRepo.findById(id);
    }

    public Transaction updateTransaction(Long id,TransactionRequest request){
        Transaction existing  = transactionRepo.findById(id).orElseThrow(()->new RuntimeException("Transcation not found!"));

        existing.setAmount(request.amount());
        existing.setDescription(request.description());
        existing.setType(request.type());
        return transactionRepo.save(existing);
    }

    public void deleteTransaction(Long id){
        transactionRepo.deleteById(id);
    }
}
