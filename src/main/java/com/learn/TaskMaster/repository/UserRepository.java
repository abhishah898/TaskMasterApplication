package com.learn.TaskMaster.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.learn.TaskMaster.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String userName);
}
