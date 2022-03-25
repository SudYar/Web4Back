package ru.itmo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="users")
public class User {

    @Id
    @GeneratedValue
    private Long id;
    private String login;
    private String password;
    @OneToMany(mappedBy = "user")
    private Collection<Dot> hits;

    public User(String login, String password){
        this.login = login;
        this.password = password;
    }
}
