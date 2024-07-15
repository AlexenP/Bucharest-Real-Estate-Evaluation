package com.licenta.rapoarteimobiliare.controllers;

import com.licenta.rapoarteimobiliare.entities.UserEntity;
import com.licenta.rapoarteimobiliare.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/modify-accounts")
    public String showModifyAccountsPage(Model model, Authentication authentication) {
        List<UserEntity> users = userRepository.findAll();
        model.addAttribute("users", users);
        model.addAttribute("loggedInUser", authentication.getName());
        return "modify-accounts";
    }

    @PostMapping("/modify-user/{id}")
    @ResponseBody
    public ResponseEntity<?> modifyUser(@PathVariable Integer id, @RequestBody ModifyUserRequest request) {
        Optional<UserEntity> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            if (passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
                if ("username".equals(request.getAction())) {
                    user.setUsername(request.getNewValue());
                } else if ("password".equals(request.getAction())) {
                    user.setPassword(passwordEncoder.encode(request.getNewValue()));
                }
                userRepository.save(user);
                return ResponseEntity.ok().body(new ModifyUserResponse("Modification successful."));
            } else {
                return ResponseEntity.badRequest().body(new ModifyUserResponse("Old password is incorrect."));
            }
        } else {
            return ResponseEntity.badRequest().body(new ModifyUserResponse("User not found."));
        }
    }

    @PostMapping("/delete-user/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        Optional<UserEntity> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            userRepository.delete(userOptional.get());
            return ResponseEntity.ok().body(new ModifyUserResponse("User deleted successfully."));
        } else {
            return ResponseEntity.badRequest().body(new ModifyUserResponse("User not found."));
        }
    }

    public static class ModifyUserRequest {
        private String oldPassword;
        private String action;
        private String newValue;

        // Getters and setters

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

    public static class ModifyUserResponse {
        private String message;

        public ModifyUserResponse(String message) {
            this.message = message;
        }

        // Getter
        public String getMessage() {
            return message;
        }
    }
}
