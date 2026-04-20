package com.personal.financemanager.service;

import com.personal.financemanager.repository.UserRepo;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.personal.financemanager.dtos.AccountRequest;
import com.personal.financemanager.entity.Account;
import com.personal.financemanager.entity.User;
import com.personal.financemanager.repository.AccountRepo;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final UserRepo userRepo;
    private final AccountRepo accountRepo;

    public List<Account> getAllAccounts(){
        return accountRepo.findAll();
    }

    public Account createAccount(AccountRequest request){
        Account account = new Account();
        account.setAccountname(request.getAccountname());
        account.setBalance(request.getBalance());
        User user = userRepo.findById(request.getUserId()).orElseThrow(()->new RuntimeException("User not found!"));
        account.setUser(user);
        return accountRepo.save(account);
    }

    public Account getAccountById(Long id){
        return accountRepo.findById(id).orElseThrow(()->new RuntimeException("Account not found with id:" + id));
    }

    public Account updateAccount(Long id,AccountRequest request){
        Account account = accountRepo.findById(id).orElseThrow(()-> new RuntimeException("Account id not found!"));
        account.setAccountname(request.getAccountname());
        account.setBalance(request.getBalance());
        if(request.getUserId() != null){
            User user = userRepo.findById(request.getUserId()).orElseThrow(()->new RuntimeException("User not found"));
            account.setUser(user);
        }
        return accountRepo.save(account);
    }

    public void deleteAccount(Long id){
        if(!accountRepo.existsById(id)){
            throw new RuntimeException("Account not found,can't delete!");
        }
        accountRepo.deleteById(id);
    }
}
