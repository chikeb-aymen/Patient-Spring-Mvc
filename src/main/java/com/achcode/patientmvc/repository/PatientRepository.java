package com.achcode.patientmvc.repository;

import com.achcode.patientmvc.model.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PatientRepository extends JpaRepository<Patient,Long> {
    Page<Patient> findByUsernameContaining(String keyword, Pageable pageable);
}
