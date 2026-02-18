package com.nursultan.dao;

import com.nursultan.entity.Purchase;
import com.nursultan.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

}
