package enit.utm.Keycloak.Repository;

import enit.utm.Keycloak.entity.User_Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User_Entity, String> {
    Optional<User_Entity> findUserByUsername(String username);

}
