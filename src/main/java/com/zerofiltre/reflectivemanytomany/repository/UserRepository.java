package com.zerofiltre.reflectivemanytomany.repository;

import com.zerofiltre.reflectivemanytomany.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
