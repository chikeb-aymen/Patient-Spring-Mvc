package com.achcode.patientmvc.repository;

import com.achcode.patientmvc.model.Patient;
import com.achcode.patientmvc.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users,Long> {
    Users findByUsername(String username);
}
