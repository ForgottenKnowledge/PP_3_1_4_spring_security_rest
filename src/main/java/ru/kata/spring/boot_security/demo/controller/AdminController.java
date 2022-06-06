package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.ArrayList;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String showAllUsers(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("usersList", userService.getAllUsers());
        model.addAttribute("authenticatedUser", user);
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @PostMapping("/edit")
    public String update(@ModelAttribute("user") User user, @RequestParam("listRoles") ArrayList<Long> roles) {
        userService.updateUser(user, roleService.findRoles(roles));
        return "redirect:/admin";
    }

    @PostMapping("/new")
    public String create(User user, @RequestParam("listRoles") ArrayList<Long> roles) {
        userService.saveNewUser(user, roleService.findRoles(roles));
        return "redirect:/admin";
    }
}