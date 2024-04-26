package enit.utm.Keycloak.service;

import enit.utm.Keycloak.entity.User_Entity;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IuserService {

    UserRepresentation getUserById(String userId);
    void deleteUserById(String userId);
    void emailVerification(String userId);
    UserResource getUserResource(String userId);
    void updatePassword(String userId);
    public List<UserRepresentation> getAllUsers();

    public void synchronizeUsers();

    public User_Entity getUserByUserName(String username);

    public List<User_Entity> getAllUser();


}
