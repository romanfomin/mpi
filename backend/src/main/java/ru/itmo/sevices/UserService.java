package ru.itmo.sevices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itmo.exceptions.ResourceNotFoundException;
import ru.itmo.models.Role;
import ru.itmo.models.User;
import ru.itmo.repositories.RoleRepository;
import ru.itmo.repositories.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getById(Long id) throws ResourceNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id=" + id + "not found"));
    }

    public User updateUser(User user) throws ResourceNotFoundException {
        Set<Role> newRoles = user.getRoles();
        Set<Role> roles = new HashSet<>();
        for(Role role: newRoles) {
            Role roleByName = roleRepository.findByName(role.getName())
                    .orElseThrow(() -> new ResourceNotFoundException("Error: Role is not found."));
            roles.add(roleByName);
        }
        user.setRoles(roles);
        return userRepository.save(user);
    }
}
