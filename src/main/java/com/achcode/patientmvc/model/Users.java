package com.achcode.patientmvc.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    private boolean active;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Roles> rolesList = new ArrayList<>();

    public Users(String username, String password, boolean active) {
        this.username = username;
        this.password = password;
        this.active = active;
    }

}
