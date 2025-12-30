package com.projet.Chapi.repository;

import org.springframework.data.repository.CrudRepository;
import com.projet.Chapi.models.Message;

public interface MessageRepository extends CrudRepository<Message, Long>{

}
