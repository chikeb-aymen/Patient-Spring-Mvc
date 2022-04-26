package com.achcode.patientmvc.service;

import com.achcode.patientmvc.model.Roles;
import com.achcode.patientmvc.model.Users;
import com.achcode.patientmvc.repository.RolesRepository;
import com.achcode.patientmvc.repository.UsersRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import javax.transaction.Transactional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class SecurityServiceImpl implements SecurityService {

    private UsersRepository usersRepository;

    private RolesRepository rolesRepository;

    private PasswordEncoder passwordEncoder;


    @Override
    public Users saveNewUser(String username, String password, String rePassword) {
        if(!password.equals(rePassword)) throw new RuntimeException("Passwords not match");
        String hashPass = passwordEncoder.encode(password);

        Users user = new Users();
        user.setUsername(username);
        user.setPassword(hashPass);
        user.setActive(true);

        return usersRepository.save(user);


    }

    @Override
    public Roles saveNewRole(String roleName, String description){

        if(rolesRepository.findByRoleName(roleName)!=null) throw new RuntimeException("Role already found");

        Roles role = new Roles();
        role.setRoleName(roleName);
        role.setRoleDescription(description);

        return  rolesRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        Users user = usersRepository.findByUsername(username);

        if(user==null) throw new RuntimeException("User not found");

        Roles role = rolesRepository.findByRoleName(roleName);

        if(role==null) throw new RuntimeException("Role not found");

        // TODO = why you don't check if user has role
        if(user.getRolesList().contains(role)) throw new RuntimeException("User Already has this role");

        user.getRolesList().add(role);

        //usersRepository.save(user);

    }

    @Override
    public void removeRoleFromUser(String username, String roleName) {
        Users user = usersRepository.findByUsername(username);

        if(user==null) throw new RuntimeException("User not found");

        Roles role = rolesRepository.findByRoleName(roleName);

        if(role==null) throw new RuntimeException("Role not found");

        user.getRolesList().remove(role);
    }

    @Override
    public Users loadUserByUserName(String username) {
        return usersRepository.findByUsername(username);
    }
}
