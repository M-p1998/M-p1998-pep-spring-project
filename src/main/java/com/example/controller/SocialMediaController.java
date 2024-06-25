package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @GetMapping("/messages")
    public @ResponseBody ResponseEntity<List<Message>> getAllMessageHandler(){
        List<Message> allMsg = messageService.getAllMessages();
        return ResponseEntity.status(200).body(allMsg);
    }
    

    @GetMapping("/messages/{messageId}")
    public @ResponseBody ResponseEntity<Message> getMessageByItsIdHandler(@PathVariable int messageId){
        Message getMsg = messageService.getMessageById(messageId);
        if(getMsg != null){
            return ResponseEntity.status(200).body(getMsg);
        }
        return ResponseEntity.status(200).body(getMsg);

    }

    @DeleteMapping("/messages/{messageId}")
    public @ResponseBody ResponseEntity<Message> deleteMessageByIdHandler(@PathVariable int messageId){
        Message msg = messageService.deleteMessage(messageId);
        if(msg != null){
            return ResponseEntity.status(200).body(msg);
        }
        else{
            return ResponseEntity.ok(msg);
        }
    }

    @PutMapping("/messages/{messageId}")
    public @ResponseBody ResponseEntity<Message> updateMessageByIdHandler(@PathVariable int messageId, @RequestBody Message message){
        Message msg = messageService.updatedMessage(messageId, message);
        if(msg != null){
            return ResponseEntity.status(200).body(msg);
        }
        else{
            return ResponseEntity.status(400).body(null);
        }
    }

    @GetMapping("/accounts/{accountId}/messages")
    public @ResponseBody ResponseEntity<List<Message>> getMessageByAccIdHandler(@PathVariable int accountId){
        List<Message> messages = messageService.getMessageByAccountId(accountId);
        return ResponseEntity.ok(messages);
        
    }

}