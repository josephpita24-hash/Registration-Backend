package com.Register.Registration.Repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.Register.Registration.User.userToken;

@Repository
public interface userTokenRepository extends MongoRepository<userToken,String> {
    
    @Query("{'token': ?0}")
    Optional<userToken> findByuserVerificationToken(String token);

}
