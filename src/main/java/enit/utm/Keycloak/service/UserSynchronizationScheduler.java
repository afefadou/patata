package enit.utm.Keycloak.service;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class UserSynchronizationScheduler {

    private final UserServiceimp keycloakUserService;
    @Scheduled(fixedRate = 300000)
    public void synchronizeUsers() {
        keycloakUserService.synchronizeUsers();
    }


}