package com.fixbugnft.fixbugnft.Repository;

//import com.nftapp.nftapp.Model.ERole;
//import com.nftapp.nftapp.Model.Role;
import com.fixbugnft.fixbugnft.Model.ERole;
import com.fixbugnft.fixbugnft.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
