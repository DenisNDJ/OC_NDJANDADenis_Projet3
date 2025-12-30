package com.projet.Chapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.projet.Chapi.dto.ResponseMessageDto;
import com.projet.Chapi.models.Message;
import com.projet.Chapi.services.DtoService;
import com.projet.Chapi.services.MessageService;

@RestController
@RequestMapping("api")
public class MessageController {
	
	@Autowired
	public MessageService messageService; 
	@Autowired
	public DtoService dtoService;
	
	@PostMapping("messages")
	public ResponseEntity<ResponseMessageDto> saveMessage(@RequestBody Message message){
		Message messageDb = messageService.saveMessage(message);
		if(messageDb.equals(message)) {
			return ResponseEntity.ok(dtoService.convertResponceMessageToDto("Message send with succes")); 
		} else {
			return ResponseEntity.badRequest().body(null);
		}
	}

}
