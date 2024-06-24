package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;


@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepo;

    // an alternative way of importing accountRepository instead of @Autowired
    // public AccountService(AccountRepository accountRepo) {
    //     this.accountRepo = accountRepo;
    // }

    public Account registerUser(Account acc){
        List<Account>AllAccounts = accountRepo.findAll();
        for(Account checkIfAccExist : AllAccounts){
            if(checkIfAccExist.getUsername().equals(acc.getUsername())){
                return null; // username already exists
            }
        }
        return accountRepo.save(acc);
    }

    public Account findById(Integer id) {
        return accountRepo.findById(id).orElse(null);
    }

    public Account findByUsername(String username) {
        return accountRepo.findByUsername(username);
    }

    public Account save(Account account) {
        return accountRepo.save(account);
    }

    public Account loginUser(String username, String password){
        Account account = accountRepo.findByUsername(username);
        if(account != null && account.getPassword().equals(password)){
            return account;
        }else{
            return null;
        }

    }
}


