package com.Register.Registration.Request;

public class Register {
    
       private String userEmail;
       private String userName;
       private String userRole;
       private String userPassword;

        public String getuserEmail(){return userEmail;}
        public void setuserEmail(String userEmail){this.userEmail = userEmail;}

        public String getuserName(){return userName;}
        public void setuserName(String userName){this.userName = userName;}

        public String getuserRole(){return userRole;}
        public void setuserRole(String userRole){this.userRole= userRole;}

        public String getuserPassword(){return userPassword;}
        public void setuserPassword(String userPassword){this.userPassword = userPassword;}


}
