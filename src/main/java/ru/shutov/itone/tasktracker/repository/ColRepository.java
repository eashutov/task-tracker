package ru.shutov.itone.tasktracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shutov.itone.tasktracker.entity.Col;

import java.util.UUID;

@Repository
public interface ColRepository extends JpaRepository<Col, UUID> {
}
