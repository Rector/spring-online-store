package ru.kir.online.store.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kir.online.store.error_handling.ResourceNotFoundException;
import ru.kir.online.store.models.Role;
import ru.kir.online.store.repositories.RoleRepository;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role findRoleByName(String name) {
        Role role = roleRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Role '%s' not found", name)));
        return role;
    }

}
