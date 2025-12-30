package com.projet.Chapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projet.Chapi.models.Message;
import com.projet.Chapi.repository.MessageRepository;

@Service
public class MessageService {

	@Autowired
	private MessageRepository messageRepository;
	
    public Message saveMessage(Message message) {
    	return messageRepository.save(message);
    }
}
