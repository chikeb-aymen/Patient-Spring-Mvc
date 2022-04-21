package com.achcode.patientmvc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min = 4,max = 40)
    private String username;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private Date dateOfBirth;

    private boolean sick;

    @DecimalMin("100")
    private int score;

    public Patient(String username, Date dateOfBirth, boolean sick, int score) {
        this.username = username;
        this.dateOfBirth = dateOfBirth;
        this.sick = sick;
        this.score = score;
    }
}
