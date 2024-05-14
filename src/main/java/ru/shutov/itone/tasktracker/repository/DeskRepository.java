package ru.shutov.itone.tasktracker.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shutov.itone.tasktracker.entity.Desk;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DeskRepository extends JpaRepository<Desk, UUID> {
    @EntityGraph(value = "desk-with-tasks")
    Optional<Desk> findById(UUID id);
}
