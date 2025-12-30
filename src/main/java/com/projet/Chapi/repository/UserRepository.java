package com.projet.Chapi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;
import  com.projet.Chapi.models.Users;

@Repository
public interface UserRepository extends CrudRepository<Users, Long>, QueryByExampleExecutor<Users>{
	public Users findByEmail(String email);
}