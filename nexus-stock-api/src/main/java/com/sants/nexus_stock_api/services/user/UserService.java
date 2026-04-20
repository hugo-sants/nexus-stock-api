package com.sants.nexus_stock_api.services.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sants.nexus_stock_api.domain.user.User;
import com.sants.nexus_stock_api.domain.user.UserRole;
import com.sants.nexus_stock_api.dto.user.UserCreateDTO;
import com.sants.nexus_stock_api.dto.user.UserResponseDTO;
import com.sants.nexus_stock_api.dto.user.UserUpdateDTO;
import com.sants.nexus_stock_api.repositories.user.UserRepository;
import static com.sants.nexus_stock_api.repositories.user.UserSpecification.hasActive;
import static com.sants.nexus_stock_api.repositories.user.UserSpecification.hasEmail;
import static com.sants.nexus_stock_api.repositories.user.UserSpecification.hasName;
import static com.sants.nexus_stock_api.repositories.user.UserSpecification.hasRole;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDTO create(UserCreateDTO dto) {

        if (userRepository.existsByEmail(dto.email())) {
            throw new RuntimeException("Email already exists");
        }

        if (userRepository.existsByCpf(dto.cpf())) {
            throw new RuntimeException("CPF already exists");
        }

        User user = new User();
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setCpf(dto.cpf());
        user.setRole(dto.role());
        user.setActive(true);

        User saved = userRepository.save(user);

        return toDTO(saved);
    }

    public Page<UserResponseDTO> findAll(String name, String email, UserRole role, Boolean active, Pageable pageable) {
        Specification<User> spec = Specification
                .where(hasName(name))
                .and(hasEmail(email))
                .and(hasRole(role))
                .and(hasActive(active));
    
        return userRepository.findAll(spec, pageable)
                .map(this::toDTO);
    }

    public UserResponseDTO findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return toDTO(user);
    }

    public UserResponseDTO update(Long id, UserUpdateDTO dto) {
        User loggedUser = getAuthenticatedUser();
    
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!loggedUser.getRole().equals(UserRole.ADMIN) &&
            !loggedUser.getId().equals(user.getId())) {
            throw new RuntimeException("You cannot update another user");
        }
        if (userRepository.existsByEmail(dto.email()) && !user.getEmail().equals(dto.email())) {
                throw new RuntimeException("Email already exists");
            }

        user.setName(dto.name());
        user.setEmail(dto.email());
    
        return toDTO(userRepository.save(user));
    }

    public void delete(Long id) {
        User loggedUser = getAuthenticatedUser();
    
        if (!loggedUser.getRole().equals(UserRole.ADMIN)) {
            throw new RuntimeException("Only admins can delete users");
        }
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
    
        userRepository.deleteById(id);
    }

    public void activate(Long id) {
        User loggedUser = getAuthenticatedUser();
    
        if (!loggedUser.getRole().equals(UserRole.ADMIN)) {
            throw new RuntimeException("Only admins can deactivate users");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setActive(true);
        userRepository.save(user);
    }

    public void deactivate(Long id) {
        User loggedUser = getAuthenticatedUser();
    
        if (!loggedUser.getRole().equals(UserRole.ADMIN)) {
            throw new RuntimeException("Only admins can deactivate users");
        }
    
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    
        user.setActive(false);
        userRepository.save(user);
    }

    private UserResponseDTO toDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getRole(),
                user.getEmail(),
                user.getName(),
                user.getCpf(),
                user.getActive()
        );
    }

    private User getAuthenticatedUser() {
        return (User) org.springframework.security.core.context.SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }
}
