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

    public List<Message> getAllMessages(Message msg){
        return msgRepo.findAll();
    }
    
    public Message getMessageById(int msgId){
        Optional<Message> getMsg = msgRepo.findById(msgId);
        if(!getMsg.isEmpty()){
            return getMsg.get();
        }
        else{
            return null;
        }
        
    }

    public Message deleteMessage(int msgId){
        Optional<Message> msg = msgRepo.findById(msgId);
        if(msg.isPresent()){
            Message msgToDelete = msg.get();
            msgRepo.deleteById(msgId);
            return msgToDelete;
        }
        return null;
    }

    public Message updatedMessage(int msgId, Message MessageText){
        if(MessageText.getMessageText() == null || MessageText.getMessageText().trim().isEmpty() || MessageText.getMessageText().length() > 255 ){
            return null;
        }
        Optional<Message> message = msgRepo.findById(msgId);
        if(message.isPresent()){
            Message msg = message.get();
            msg.setMessageText(MessageText.getMessageText());
            return msgRepo.save(msg);
        }else{
            return null;
        }
    }

}
