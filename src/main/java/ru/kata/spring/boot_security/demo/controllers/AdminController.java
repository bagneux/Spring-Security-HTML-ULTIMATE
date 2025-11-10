package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.UserServiceInterface;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserServiceInterface userService;
    private final RoleRepository roleRepository;


    public AdminController(UserServiceInterface userService,
                           RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    // Главная страница админа (одна страница admin)
    @GetMapping
    public String adminPage(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        model.addAttribute("allRoles", roleRepository.findAll());

        // текущий залогиненный пользователь (для секции "My info")
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userService.findByUsername(currentUsername);
        model.addAttribute("currentUser", currentUser);

        return "admin";
    }

    // Создать пользователя
    @PostMapping("/createUser")
    public String createUser(@RequestParam("username") String username,
                             @RequestParam("password") String password,
                             @RequestParam(value = "roleIds", required = false) List<Long> roleIds) {
        Set<Role> roles = new HashSet<>(roleRepository.findAllById(
                roleIds == null ? Collections.emptyList() : roleIds
        ));
        userService.saveUser(username, password, roles);
        return "redirect:/admin";
    }

    // Обновить пользователя
    @PostMapping("/updateUser")
    public String updateUser(@RequestParam("id") Long id,
                             @RequestParam("username") String username,
                             @RequestParam(value = "password", required = false) String password,
                             @RequestParam(value = "roleIds", required = false) List<Long> roleIds) {
        Set<Role> roles = new HashSet<>(roleRepository.findAllById(
                roleIds == null ? Collections.emptyList() : roleIds
        ));
        userService.updateUser(id, username, password, roles);
        return "redirect:/admin";
    }

    // Удалить пользователя
    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }
}
