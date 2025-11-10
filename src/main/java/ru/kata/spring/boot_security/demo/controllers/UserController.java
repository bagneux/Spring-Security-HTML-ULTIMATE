package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.UserServiceInterface;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserServiceInterface userService;

    public UserController(UserServiceInterface userService) {
        this.userService = userService;
    }


    // Страница пользователя
    @GetMapping
    public String UserProfile(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        if (user.getRoles() != null) {
            user.getRoles().size();
        }

        // Оба варианта: оставляем "user" и добавляем "currentUser" как алиас
        model.addAttribute("user", user);
        model.addAttribute("currentUser", user);

        return "user";
    }
}
