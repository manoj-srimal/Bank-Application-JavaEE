package lk.assignment.bankapp.core.service;

import jakarta.ejb.Remote;
import lk.assignment.bankapp.core.model.User;

@Remote
public interface UserManagementBeanLocal {
    void registerUser(User user) throws Exception;

    // Add this new method signature
    boolean verifyUserAccount(String verificationCode);
    User loginUser(String email, String password);
    void createAndSendLoginOtp(User user);
    User validateLoginOtp(Long userId, String otp);
}