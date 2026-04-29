package com.personal.financemanager.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.personal.financemanager.entity.Account;
import com.personal.financemanager.entity.Budget;
import com.personal.financemanager.entity.Expense;
import com.personal.financemanager.entity.PaymentRequest;
import com.personal.financemanager.entity.Transaction;
import com.personal.financemanager.entity.TransactionType;
import com.personal.financemanager.dtos.TransactionRequest;
import com.personal.financemanager.entity.User;
import com.personal.financemanager.exception.BusinessException;
import com.personal.financemanager.repository.AccountRepo;
import com.personal.financemanager.repository.BudgetRepo;
import com.personal.financemanager.repository.TransactionRepo;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepo transactionRepo;
    private final AuthenticationManager authenticationManager;
    private final AccountRepo accountRepo;
    private final BudgetRepo budgetRepo;

    public void processPayment(String userEmail,PaymentRequest request){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userEmail,request.password()));
        }catch(Exception e){
            throw new BusinessException("Invalid Password,Password Denied!",HttpStatus.FORBIDDEN);
        }
        System.out.println("Processing payment for" + request.amount());
    }

    public void processExpense(Expense expense){
        Budget budget = budgetRepo.findByCategoryAndDate(expense.category(),LocalDate.now())
            .orElseGet(() -> {
            Budget newBudget = new Budget();
            newBudget.setCategory(expense.category());
            newBudget.setDate(LocalDate.now());
            newBudget.setAmountLimit(1000.0);
            newBudget.setCurrentSpending(0.0);
            return newBudget;
        });
            double newSpending = budget.getCurrentSpending() + expense.amount();
            budget.setCurrentSpending(newSpending);
            budgetRepo.save(budget);
    }

    @Transactional
    public Transaction createTransaction(TransactionRequest request,User currentUser){
        Account account = accountRepo.findById(request.accountId()).orElseThrow(() -> new BusinessException("Account not found",HttpStatus.NOT_FOUND));
        if(request.type()==TransactionType.INCOME){
            account.setBalance(account.getBalance().add(request.amount()));
        }else if(request.type()==TransactionType.EXPENSE){
            if(account.getBalance().compareTo(request.amount())<0){
                throw new BusinessException("Insufficient balance in your account!");
            }
            account.setBalance(account.getBalance().subtract(request.amount()));
        }
        accountRepo.save(account);

        Transaction transaction = new Transaction();
        transaction.setAmount(request.amount());
        transaction.setDescription(request.description());
        transaction.setDate(LocalDateTime.now());
        transaction.setType(request.type());
        transaction.setCategory(request.category());
        transaction.setUser(currentUser);
        transaction.setAccount(account);
        Transaction savedTransaction =  transactionRepo.save(transaction);

        if(request.type()==TransactionType.EXPENSE){
            Expense expense = new Expense(null,request.amount().doubleValue(),request.category().name(),request.description(),java.time.LocalDate.now(),"Online Payment");
            this.processExpense(expense);
        }
        return savedTransaction;
    }

    public List<Transaction> getAllTransaction(){
        return transactionRepo.findAll();
    }

    public Optional<Transaction> getTransactionById(Long id){
        return transactionRepo.findById(id);
    }

    public Transaction updateTransaction(Long id,TransactionRequest request){
        Transaction existing  = transactionRepo.findById(id).orElseThrow(()->new BusinessException("Transcation not found!"));

        existing.setAmount(request.amount());
        existing.setDescription(request.description());
        existing.setType(request.type());
        return transactionRepo.save(existing);
    }

    public void deleteTransaction(Long id){
        transactionRepo.deleteById(id);
    }
}
