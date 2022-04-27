package com.achcode.patientmvc.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.lang.Nullable;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

    //@NotEmpty
    @Size(min = 4)
    private String username;

    //@NotEmpty
    //@Size(min = 5,max = 20) TODO : max=20 because password encoder > 20
    @Size(min = 5)
    private String password;

    //@NotEmpty
    @Transient
    private String repeatPassword;

    @Transient
    private String role;

    private boolean active;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Roles> rolesList = new ArrayList<>();

    public Users(String username, String password, boolean active) {
        this.username = username;
        this.password = password;
        this.active = active;
    }

}
