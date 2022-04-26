package com.achcode.patientmvc.repository;

import com.achcode.patientmvc.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<Roles,Long> {
    Roles findByRoleName(String role);
}
