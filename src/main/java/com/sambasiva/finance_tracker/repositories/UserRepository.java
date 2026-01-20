package com.sambasiva.finance_tracker.repositories;

import com.sambasiva.finance_tracker.models.User;
import org.springframework.data.repository.Repository;

public interface UserRepository extends Repository<User, Integer> {
    User save(User user);
    User findByUsername(String username);
}
