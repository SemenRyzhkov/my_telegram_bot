package com.ryzhkov.telegram.dao;

import com.ryzhkov.telegram.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
