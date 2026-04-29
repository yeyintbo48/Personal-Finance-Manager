package com.personal.financemanager.service;

import com.personal.financemanager.repository.UserRepo;
import java.util.List;
import org.springframework.stereotype.Service;
import com.personal.financemanager.dtos.AccountRequest;
import com.personal.financemanager.entity.Account;
import com.personal.financemanager.entity.User;
import com.personal.financemanager.exception.BusinessException;
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
        account.setAccountName(request.getAccountName());
        account.setBalance(request.getBalance());
        User user = userRepo.findById(request.getUserId()).orElseThrow(()->new BusinessException("User not found!"));
        account.setUser(user);
        return accountRepo.save(account);
    }

    public Account getAccountById(Long id){
        return accountRepo.findById(id).orElseThrow(()->new BusinessException("Account not found with id:" +" " + id));
    }

    public Account updateAccount(Long id,AccountRequest request){
        Account account = accountRepo.findById(id).orElseThrow(()-> new BusinessException("Account id not found!"));
        account.setAccountName(request.getAccountName());
        account.setBalance(request.getBalance());
        if(request.getUserId() != null){
            User user = userRepo.findById(request.getUserId()).orElseThrow(()->new BusinessException("User not found"));
            account.setUser(user);
        }
        return accountRepo.save(account);
    }

    public void deleteAccount(Long id){
        if(!accountRepo.existsById(id)){
            throw new BusinessException("Account not found,can't delete!");
        }
        accountRepo.deleteById(id);
    }
}
