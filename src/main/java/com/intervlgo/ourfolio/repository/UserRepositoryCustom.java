package com.intervlgo.ourfolio.repository;

import com.intervlgo.ourfolio.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepositoryCustom {
    Page<User> searchUser(Pageable pageable, String username, String region, String occupation);
}
