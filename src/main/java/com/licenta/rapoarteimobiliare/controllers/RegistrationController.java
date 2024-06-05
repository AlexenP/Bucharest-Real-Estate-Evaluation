package com.licenta.rapoarteimobiliare.controllers;

import com.licenta.rapoarteimobiliare.entities.AuthorityEntity;
import com.licenta.rapoarteimobiliare.entities.UserEntity;
import com.licenta.rapoarteimobiliare.repositories.AuthorityRepository;
import com.licenta.rapoarteimobiliare.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashSet;
import java.util.Set;

@Controller
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                               @RequestParam String password,
                               @RequestParam String authority,
                               @RequestParam(required = false) String adminPassword,
                               RedirectAttributes redirectAttributes) {

        if ("ROLE_ADMIN".equals(authority) && !"Licenta2024".equals(adminPassword)) {
            redirectAttributes.addAttribute("error", "true");
            return "redirect:/register";
        }

        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));

        AuthorityEntity authorityEntity = authorityRepository.findByAuthority(authority);
        if (authorityEntity == null) {
            redirectAttributes.addAttribute("error", "true");
            return "redirect:/register";
        }

        Set<AuthorityEntity> authorities = new HashSet<>();
        authorities.add(authorityEntity);
        user.setAuthorities(authorities);

        userRepository.save(user);
        redirectAttributes.addFlashAttribute("success", "User created successfully!");
        return "redirect:/login";
    }

}
