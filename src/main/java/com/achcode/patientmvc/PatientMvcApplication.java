package com.achcode.patientmvc;

import com.achcode.patientmvc.model.Patient;
import com.achcode.patientmvc.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
public class PatientMvcApplication{

    @Autowired
    private PatientRepository patientRepository;

    public static void main(String[] args) {
        SpringApplication.run(PatientMvcApplication.class, args);
    }


    //Don't forget to implement : CommandLineRunner
    /*@Override
    public void run(String... args) throws Exception {
        //score decimal min is 100
        patientRepository.save(new Patient("John Wick",new Date(),false,19));
        patientRepository.save(new Patient("Johnny Depp",new Date(),false,7));
        patientRepository.save(new Patient("John Doe",new Date(),false,9));
        patientRepository.save(new Patient("Ach Code",new Date(),false,12));
        patientRepository.save(new Patient("John Wick",new Date(),false,10));
        patientRepository.save(new Patient("Johnny Depp",new Date(),false,20));
        patientRepository.save(new Patient("John Doe",new Date(),false,24));
        patientRepository.save(new Patient("Ach Code",new Date(),false,56));

        patientRepository.findAll().forEach(p-> System.out.println(p.getUsername()));
    }*/

}
