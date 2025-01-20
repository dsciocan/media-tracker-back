package com.duroc.mediatracker.repository;

import com.duroc.mediatracker.model.user.AppUser;
import com.duroc.mediatracker.model.user.UserShow;
import com.duroc.mediatracker.model.user.UserShowId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserShowRepository extends JpaRepository<UserShow, UserShowId> {

    List<UserShow> findByUserShowIdAppUser(AppUser user);
}
