package enit.utm.Keycloak.controller;


import enit.utm.Keycloak.entity.User_Entity;
import enit.utm.Keycloak.service.IuserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("Users/")
@AllArgsConstructor
public class controllerUser {

    private IuserService iuserService;


    @GetMapping("All")
    public List<User_Entity> getAll(){
        return this.iuserService.getAllUser();
    }

    @GetMapping("san")
    public void san(){
         this.iuserService.synchronizeUsers();
    }



}
