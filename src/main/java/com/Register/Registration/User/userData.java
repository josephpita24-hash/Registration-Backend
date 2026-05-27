package com.Register.Registration.User;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("userData")
public class userData {
         
         @Id
         private String userId;

         private String userEmail;
         private String userName;
         private String userSession_id;
         private String userRole;
         private String userPassword;
         private Boolean isEnable;

        //  Getter and setter
        public String getuserEmail(){return userEmail;}
        public void setuserEmail(String userEmail){this.userEmail = userEmail;}

        public String getuserName(){return userName;}
        public void setuserName(String userName){this.userName = userName;}

        public String getuserSession_id(){return userSession_id;}
        public void setuserSession_id(String userSession_id){this.userSession_id = userSession_id;}

        public String getuserPassword(){return userPassword;}
        public void setuserPassword(String userPassword){this.userPassword = userPassword;}

        public Boolean getisEnable(){return isEnable;}
        public void setisEnable(Boolean isEnable){this.isEnable= isEnable;}

        public String getuserRole(){return userRole;}
        public void setuserRole(String userRole){this.userRole= userRole;}










}
