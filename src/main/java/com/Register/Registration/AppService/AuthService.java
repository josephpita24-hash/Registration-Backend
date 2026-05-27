package com.Register.Registration.AppService;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Register.Registration.Repository.userDataRepository;
import com.Register.Registration.Repository.userTokenRepository;
import com.Register.Registration.User.userData;
import com.Register.Registration.User.userToken;

@Service
public class AuthService {

    private emailServices EmailSender;
    private PasswordEncoder Encoder;
    private userDataRepository userDataRepository;
    private userTokenRepository userTokenRepository;

    public AuthService(emailServices EmailSender, PasswordEncoder Encoder, userDataRepository userDataRepository,
            userTokenRepository userTokenRepository) {
        this.EmailSender = EmailSender;
        this.Encoder = Encoder;
        this.userDataRepository = userDataRepository;
        this.userTokenRepository = userTokenRepository;
    }

    public String Register(String userEmail, String userName, String userPassword, String userRole) throws Exception {
        if (userEmail == null || userName == null || userPassword == null || userRole == null) {
            throw new Exception(
                    "UserEmail & userName & userPassword One of a null, Please ensure you put all input correct");
        }
        if (userDataRepository.findByUsermail(userEmail).isPresent()) {
            throw new Exception(
                    "This Email Already Exist!, Please to login");

        }
        userData data = new userData();
        data.setuserEmail(userEmail);
        data.setuserName(userName);
        data.setuserRole(userRole);
        data.setuserPassword(Encoder.encode(userPassword));
        data.setisEnable(false);
        String sessionToken = UUID.randomUUID().toString();
        data.setuserSession_id(sessionToken);

        userDataRepository.save(data);

        String tokens = UUID.randomUUID().toString();
        userToken tokenRegister = new userToken(tokens, data);

        userTokenRepository.save(tokenRegister);

        EmailSender.sendMail(userEmail, tokens);

        return sessionToken;

    }

    public String ComfimEmail(String token) throws Exception {
        try {
            // Find user By Token
            userToken UserToken = userTokenRepository.findByuserVerificationToken(token)
                    .orElseThrow(() -> new UsernameNotFoundException(" User Verification token not found"));

            // Check null value
            if (UserToken == null) {
                return "User Verification token not found";
            }

            // Check Expire
            if (UserToken.getexpireDateTime().isBefore(LocalDateTime.now())) {
                return "Token already Expire";
            }
            // Save user
            userData data = UserToken.getuserData();
            data.setisEnable(true);

            userDataRepository.save(data);

            // Delete Token
            return "Success";
        } catch (Exception e) {
            // Return if there is error
            return e.getMessage() + "  " + e.getCause() + "  " + e.getLocalizedMessage() + "  " + e.getStackTrace();
        }

    }

    public String updatePassword(String userEmail, String CurrentPassword, String newPassoword) throws Exception {
        userData user = userDataRepository.findByUsermail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException(" User  Email not found"));

        if (!Encoder.matches(CurrentPassword, user.getuserPassword())) {
            throw new Exception("Current password is incorrect");
        }

        user.setuserPassword(newPassoword);
        userDataRepository.save(user);

        return "Password of " + user.getuserEmail() + " Updated successfully";
    }

    public userData getuserData(String userEmail) {
        userData user = userDataRepository.findByUsermail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException(" User with : " + userEmail + " Does not Exist"));
        return user;
    }

}
