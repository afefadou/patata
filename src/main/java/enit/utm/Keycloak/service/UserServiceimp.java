package enit.utm.Keycloak.service;

import enit.utm.Keycloak.Repository.UserRepository;
import enit.utm.Keycloak.entity.User_Entity;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserServiceimp implements IuserService {

    @Value("${keycloak.realm}")
    private String realm;
    @Autowired
    private Keycloak keycloak;
    @Autowired
    private UserRepository userRepository;


    public List<UserRepresentation> getAllUsers() {
        return keycloak.realm(realm).users().list();
    }

    private UsersResource getUsersResource() {
        RealmResource realm1 = keycloak.realm(realm);
        return realm1.users();
    }

    @Override
    public UserRepresentation getUserById(String userId) {
        return getUsersResource().get(userId).toRepresentation();
    }

    @Override
    public void deleteUserById(String userId) {
        getUsersResource().delete(userId);
        this.userRepository.deleteById(userId);
    }


    @Override
    public void emailVerification(String userId) {
        UsersResource usersResource = getUsersResource();
        usersResource.get(userId).sendVerifyEmail();
    }

    public UserResource getUserResource(String userId) {
        UsersResource usersResource = getUsersResource();
        return usersResource.get(userId);
    }

    @Override
    public void updatePassword(String userId) {
        UserResource userResource = getUserResource(userId);
        List<String> actions = new ArrayList<>();
        actions.add("UPDATE_PASSWORD");
        userResource.executeActionsEmail(actions);
    }

    public void synchronizeUsers() {
        List<UserRepresentation> keycloakUsers = getAllUsers();
        List<String> keycloakUsernames = keycloakUsers.stream()
                .map(UserRepresentation::getUsername)
                .collect(Collectors.toList());
        List<User_Entity> dbUsers = userRepository.findAll();
        for (User_Entity dbUser : dbUsers) {
            if (!keycloakUsernames.contains(dbUser.getUsername())) {
                userRepository.delete(dbUser);
            }
        }

        for (UserRepresentation keycloakUser : keycloakUsers) {
            Optional<User_Entity> existingUser = userRepository.findUserByUsername(keycloakUser.getUsername());
            if (existingUser.isPresent()) {
                User_Entity User_Entity = existingUser.get();
                User_Entity.setFirstName(keycloakUser.getFirstName());
                User_Entity.setLastName(keycloakUser.getLastName());
                User_Entity.setEmail(keycloakUser.getEmail());

                userRepository.save(User_Entity);
            } else {
                User_Entity newUser = new User_Entity();
                newUser.setId(keycloakUser.getId());
                newUser.setUsername(keycloakUser.getUsername());
                newUser.setEmail(keycloakUser.getEmail());
                newUser.setFirstName(keycloakUser.getFirstName());
                newUser.setLastName(keycloakUser.getLastName());
                userRepository.save(newUser);
            }
        }
    }

    public User_Entity getUserByUserName(String username){
        Optional<User_Entity> optionalUserEntity = this.userRepository.findUserByUsername(username);
        if(optionalUserEntity.isPresent()){
            return optionalUserEntity.get();
        }
        return null;
    }

    public List<User_Entity> getAllUser(){

        return this.userRepository.findAll();
    }


}