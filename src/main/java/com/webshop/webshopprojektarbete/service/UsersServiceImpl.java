package com.webshop.webshopprojektarbete.service;

import com.webshop.webshopprojektarbete.Status;

import com.webshop.webshopprojektarbete.entity.Confirmation;
import com.webshop.webshopprojektarbete.entity.Users;
import com.webshop.webshopprojektarbete.repository.ConfirmationRepo;
import com.webshop.webshopprojektarbete.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UsersServiceImpl implements UserService{

    @Autowired
    UserRepo userRepository;
    @Autowired
    ConfirmationRepo confirmationRepository;
    @Autowired
    public EmailService emailService;


    @Override
    public Status saveUser(Users users) {
        Status checkStatus = checkIfExist(users.getEmail());
        if (checkStatus == Status.USER_IS_NOT_ENABLED){
            Users usero = userRepository.findByEmailIgnoreCase(users.getEmail());
            String userId = usero.getEmail();
            sendNewToken(users.getEmail(), userId);
            System.out.println("inte verifierad");
            return Status.USER_IS_ENABLED;
        } else if (checkStatus == Status.USER_NOT_REGISTERED) {
            users.setEnabled((byte) 0);
            users.setIsAdmin((byte) 0);
            userRepository.save(users);

            Confirmation confirmation = new Confirmation(users);
            confirmationRepository.save(confirmation);
            sendConfirmationKey(users.getEmail(), confirmation.getToken());
            System.out.println("inte registrerad");
            return Status.USER_NOT_REGISTERED;
        } else {
            System.out.println("verifierad");
            return Status.USER_IS_ENABLED;
        }

        /* SEND EMAIL TO USER WITH TOKEN*/

    }
    @Override
    public boolean checkEmailFormat(String email){
        boolean matchfound;
        Pattern pattern = Pattern.compile("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = pattern.matcher(email);
        matchfound = matcher.find();
        if (matchfound){
            return true;
        } else {
            return false;
        }
    }
    private void sendNewToken(String email, String userId){
        Confirmation c = confirmationRepository.findByUserId(userId);
        c.setToken(generateRandomNumber());
        confirmationRepository.save(c);
        sendConfirmationKey(email, c.getToken());
    }
    @Override
    public Status validateLogin(String email, String password){
        Status status = null;
        Users a = userRepository.findByEmailIgnoreCase(email);
        if (a!= null){

            byte enabledStatus = a.getEnabled();
            if (enabledStatus == (byte) 1){
                if (password.equals(a.getPassword())){
                    status = Status.USER_IS_ENABLED;
                } else {
                    status = Status.WRONG_DETAILS;
                }
            } else {
                sendNewToken(a.getEmail(), a.getEmail());
                status = Status.USER_IS_NOT_ENABLED;
            }

        } else {
            status = Status.USER_NOT_REGISTERED;
        }
        return status;
    }
    private void sendConfirmationKey(String mail, String token){
        emailService.sendSimpleMailMessage(mail, token);
    }
    @Override
    public Status checkIfExist(String email){
        Status status = null;
        if (userRepository.existsByEmail(email)){
            byte isEnabled = userRepository.findByEmailIgnoreCase(email).getEnabled();
            if (isEnabled == (byte) 0){
                status = Status.USER_IS_NOT_ENABLED;
            }
            if (isEnabled == (byte) 1){
                status = Status.USER_IS_ENABLED;
            }
        } else {
            status = Status.USER_NOT_REGISTERED;
        }
        return status;
    }
    private String generateRandomNumber(){
        Random rand = new Random();
        return String.valueOf(rand.nextInt(10000));
    }

    @Override
    public Boolean verifyToken(String token) {
        //TODO FIXA HÄR IFALL MAN SKRIVER FEL KOD
        Optional<Confirmation> confirmation = confirmationRepository.findByToken(token);
        if (confirmation.isPresent()){
            Confirmation foundConfirmation = confirmation.get();
            String foundToken = foundConfirmation.getToken();
            Users user = userRepository.findByEmailIgnoreCase(foundConfirmation.getUsersByUserId().getEmail());
            if (token.equals(foundToken)){
                user.setEnabled((byte) 1);
                userRepository.save(user);
                System.out.println("Rätt kod");
                return Boolean.TRUE;
            } else {
                user.setEnabled((byte) 0);
                userRepository.save(user);
                System.out.println("Fel kod!");
                return Boolean.FALSE;
            }
        } else {
            return Boolean.FALSE;
        }


    }
}
