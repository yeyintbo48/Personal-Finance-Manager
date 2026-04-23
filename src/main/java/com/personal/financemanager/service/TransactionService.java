package com.personal.financemanager.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.personal.financemanager.entity.Account;
import com.personal.financemanager.entity.Budget;
import com.personal.financemanager.entity.Expense;
import com.personal.financemanager.entity.PaymentRequest;
import com.personal.financemanager.entity.Transaction;
import com.personal.financemanager.dtos.TransactionRequest;
import com.personal.financemanager.entity.User;
import com.personal.financemanager.repository.AccountRepo;
import com.personal.financemanager.repository.BudgetRepo;
import com.personal.financemanager.repository.TransactionRepo;
import com.personal.financemanager.repository.UserRepo;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepo transactionRepo;
    private final AuthenticationManager authenticationManager;
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AccountRepo accountRepo;
    private final BudgetRepo budgetRepo;

    public void processPayment(String userEmail,PaymentRequest request){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userEmail,request.password()));
        }catch(Exception e){
            throw new RuntimeException("Invalid Passwrod,Password Denied!");
        }
        System.out.println("Processing payment for" + request.amount());
    }

    public void processExpense(Expense expense){
        Optional<Budget> budgetOpt = budgetRepo.findByCategoryAndDate(expense.category(),LocalDate.now());
        budgetOpt.ifPresent(budget -> {
            double newSpending = budget.getCurrentSpending() + expense.amount();
            budget.setCurrentSpending(newSpending);
            budgetRepo.save(budget);
        });
    }

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
