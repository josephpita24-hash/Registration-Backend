package com.Register.Registration.Request;

public class UpdatePassword {
    
     private String currentPassword;
     private String newPassword;
     private String comfimPassowrd;

    public String getcurrentPassword(){    return currentPassword; }
    public void setcurrentPassword(String currentPassword){this.currentPassword = currentPassword;}

    public String getnewPassowrd(){    return newPassword; }
    public void setnewPassowrd(String newPassword){this.newPassword = newPassword;}

    public String getcomfimPassowrd(){    return comfimPassowrd; }
    public void setcomfimPassowrd(String comfimPassowrd){this.comfimPassowrd= comfimPassowrd;}

}