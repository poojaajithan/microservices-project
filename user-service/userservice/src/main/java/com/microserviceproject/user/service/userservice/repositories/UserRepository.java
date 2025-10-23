package com.microserviceproject.user.service.userservice.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.microserviceproject.user.service.userservice.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

}
