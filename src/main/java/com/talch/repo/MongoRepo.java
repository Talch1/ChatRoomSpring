package com.talch.repo;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.talch.beans.ChattingMessage;

@Repository
public interface MongoRepo extends MongoRepository<ChattingMessage, Long>{
	
}
