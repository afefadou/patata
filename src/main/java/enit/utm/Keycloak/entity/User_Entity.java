package enit.utm.Keycloak.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User_Entity {

    @Id
    private String id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
}
