package com.Register.Registration.User;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document("userToken")
public class userToken {
    
        @Id
        private String tokenId;

        private String token;
        private userData userData;
        private LocalDateTime expireDateTime;

        public userToken(String token,userData userData){
            this.token = token;
            this.userData = userData;
            this.expireDateTime = LocalDateTime.now().plusHours(24);
        }

        public String gettoken(){return token;}
        public void settoken(String token){this.token = token;}

        public userData getuserData(){return userData;}
        public void setuserData(userData userData){this.userData = userData;}

        public LocalDateTime getexpireDateTime(){return expireDateTime;}
        public void setexpireDateTime(LocalDateTime expireDateTime){this.expireDateTime = expireDateTime;}



        
}
