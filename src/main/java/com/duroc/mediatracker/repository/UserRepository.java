package com.duroc.mediatracker.repository;

import com.duroc.mediatracker.model.user.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {
    List<AppUser> findByUserToken(String userToken);
}
