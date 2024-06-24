package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@Controller
@RequestMapping("")
public class SocialMediaController  {

    @Autowired
    public AccountService accountService;
    @Autowired
    public MessageService messageService;

    @PostMapping("/register")
    public @ResponseBody ResponseEntity<Account> registerValidation(@RequestBody Account acc){
        if(acc.getUsername() == null || acc.getUsername().trim().isEmpty() 
            || acc.getPassword() == null || acc.getPassword().length() < 4){
            return ResponseEntity.status(400).build();
        }
        Account existingAccount = accountService.findByUsername(acc.getUsername());
        if(existingAccount != null){
            return ResponseEntity.status(409).body(null);
        }
        Account createAccount = accountService.registerUser(acc);
        if(createAccount == null){
            return ResponseEntity.status(409).body(null);
        }else{
            // return ResponseEntity.status(200).body(createAccount);
            return ResponseEntity.ok(createAccount);
        }
        
    }

    @PostMapping("/login")
    public @ResponseBody ResponseEntity<Account> loginValidation(@RequestBody Account account){
        if(account.getUsername() == null || account.getPassword() == null){
            return ResponseEntity.status(400).body(null);
        }
        Account loggedInAccount = accountService.loginUser(account.getUsername(), account.getPassword());
        if(loggedInAccount != null){
            return ResponseEntity.ok(loggedInAccount);
        }
        else{
            return ResponseEntity.status(401).body(null);
        }
    }

    @PostMapping("/messages")
    public @ResponseBody ResponseEntity<Message> createMessageHandler(@RequestBody Message message){

        Message createdMessage = messageService.createMessage(message);
        if(createdMessage != null){
            return ResponseEntity.ok(createdMessage);
        }
        else{
            return ResponseEntity.status(400).body(null);
        }
    }



}