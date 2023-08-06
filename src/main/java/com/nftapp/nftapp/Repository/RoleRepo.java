package com.nftapp.nftapp.Repository;

import com.nftapp.nftapp.Model.ERole;
import com.nftapp.nftapp.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
