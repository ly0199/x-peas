package com.lijq.xp.user.server.repository;

import com.lijq.xp.user.server.repository.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Lijq
 */
@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
}
