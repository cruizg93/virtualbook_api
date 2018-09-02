package com.virtualbook.api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.virtualbook.api.models.Role;
import com.virtualbook.api.models.RoleName;

@Repository
public interface RolesRepository extends JpaRepository<Role, Long>{

	Optional<Role> findByName(RoleName roleName);
}
