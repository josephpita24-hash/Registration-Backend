package com.Register.Registration.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.Register.Registration.User.MongoPersistentRememberMeToken;
import java.util.List;


public interface MongoTokenRepository extends MongoRepository<MongoPersistentRememberMeToken, String> {
    List<MongoPersistentRememberMeToken> findByUsername(String username);
}