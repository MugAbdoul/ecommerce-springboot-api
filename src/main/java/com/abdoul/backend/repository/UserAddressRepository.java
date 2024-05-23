package com.abdoul.backend.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abdoul.backend.entities.User;
import com.abdoul.backend.entities.UserAddress;

public interface UserAddressRepository extends JpaRepository<UserAddress, UUID>{
    List<UserAddress> findByUser(User user);
}
