package com.br.luccasdev.projectSpringSecurity.repository;

import com.br.luccasdev.projectSpringSecurity.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
