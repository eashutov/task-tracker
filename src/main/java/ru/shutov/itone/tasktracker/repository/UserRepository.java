package ru.shutov.itone.tasktracker.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shutov.itone.tasktracker.entity.User;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @EntityGraph(value = "user-with-memberships")
    Optional<User> findByUsername(String username);
}
