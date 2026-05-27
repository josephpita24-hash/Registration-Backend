package com.Register.Registration.Repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.Register.Registration.User.userData;

@Repository
public interface userDataRepository extends MongoRepository<userData,String>{
    
    @Query("{'userEmail': ?0}")
    Optional<userData> findByUsermail(String userEmail);

    @Query("{'userName': ?0}")
    Optional<userData> findByUserName(String userName);


}
