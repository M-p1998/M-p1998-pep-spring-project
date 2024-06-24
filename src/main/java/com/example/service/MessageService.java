package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;



@Service
public class MessageService {

    @Autowired
    private MessageRepository msgRepo;
    @Autowired
    private AccountRepository accRepo;

    public Message createMessage(Message msg){
        if(msg.getMessageText() == null || msg.getMessageText().trim().isEmpty() || msg.getMessageText().length() > 255){
            return null;
        }
        Optional<Account> account = accRepo.findById(msg.getPostedBy());
        if(account.isEmpty()){
            return null;
        }

        try {
            return msgRepo.save(msg);
        } catch (Exception e) {
            e.printStackTrace(); 
            return null; 
        }

    }
    

}
//  // Check if postedBy (account) exists
//  Optional<Account> accountOptional = accRepo.findById(msg.getPostedBy());
//  if (accountOptional.isEmpty()) {
//      return null; // Return null if account does not exist
//  }

// public Message postMessage(Message message){
//     if (message.getMessage_text().trim().equals("") || message.getMessage_text().length()>254){
//         return null;
//     }
//     List<Account> account = accountRepository.findAll();
//     for (Account checkedAcount : account){
//         if (checkedAcount.getAccount_id().equals(message.getPosted_by())){
//             return messageRepository.save(message);
//         }
//     }
    
//         return null;
    

// }