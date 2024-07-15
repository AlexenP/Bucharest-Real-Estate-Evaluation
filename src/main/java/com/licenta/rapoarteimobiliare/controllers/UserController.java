package com.licenta.rapoarteimobiliare.controllers;

import com.licenta.rapoarteimobiliare.entities.UserEntity;
import com.licenta.rapoarteimobiliare.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/modify-accounts")
    public String showModifyAccountsPage(Model model, Principal principal) {
        List<UserEntity> users = userRepository.findAll();
        String loggedInUsername = principal.getName();
        model.addAttribute("users", users);
        model.addAttribute("loggedInUser", loggedInUsername);
        return "modify-accounts";
    }

    @PostMapping("/modify-user/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> modifyUser(@PathVariable Integer id, @RequestBody ModifyUserRequest request) {
        Map<String, Object> response = new HashMap<>();
        try {
            UserEntity user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

            // Validate the old password
            if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
                response.put("success", false);
                response.put("message", "Username or password is incorrect.");
                return ResponseEntity.ok(response);
            }

            // Perform the update
            if ("username".equals(request.getAction())) {
                user.setUsername(request.getNewValue());
                response.put("message", "Username modified successfully.");
            } else if ("password".equals(request.getAction())) {
                user.setPassword(passwordEncoder.encode(request.getNewValue()));
                response.put("message", "Password modified successfully.");
            }
            userRepository.save(user);
            response.put("success", true);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/delete-user/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        try {
            UserEntity user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
            userRepository.delete(user);
            response.put("success", true);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    static class ModifyUserRequest {
        private String username;
        private String oldPassword;
        private String action;
        private String newValue;

        // Getters and setters

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getOldPassword() {
            return oldPassword;
        }

        public void setOldPassword(String oldPassword) {
            this.oldPassword = oldPassword;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public String getNewValue() {
            return newValue;
        }

        public void setNewValue(String newValue) {
            this.newValue = newValue;
        }
    }

    static class ModifyUserResponse {
        private boolean success;
        private String message;

        public ModifyUserResponse(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        // Getters and setters

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}

