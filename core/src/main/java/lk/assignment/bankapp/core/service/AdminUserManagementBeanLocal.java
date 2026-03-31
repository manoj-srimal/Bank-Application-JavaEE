package lk.assignment.bankapp.core.service;

import jakarta.ejb.Remote;
import lk.assignment.bankapp.core.model.User;

import java.util.List;

@Remote
public interface AdminUserManagementBeanLocal {
    List<User> getAllUsers();
    void toggleUserStatus(Long userIdToToggle, Long adminUserId) throws Exception;

    long getTotalUserCount();
    long getTotalAccountCount();
}