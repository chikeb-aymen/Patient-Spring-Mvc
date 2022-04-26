package com.achcode.patientmvc.service;

import com.achcode.patientmvc.model.Roles;
import com.achcode.patientmvc.model.Users;

public interface SecurityService {
    Users saveNewUser(String username,String password,String rePassword);
    Roles saveNewRole(String roleName, String description);
    void addRoleToUser(String username,String roleName);
    void removeRoleFromUser(String username,String roleName);
    Users loadUserByUserName(String username);
}
