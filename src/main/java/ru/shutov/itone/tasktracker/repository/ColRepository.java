package ru.shutov.itone.tasktracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shutov.itone.tasktracker.entity.Col;
import ru.shutov.itone.tasktracker.entity.Desk;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ColRepository extends JpaRepository<Col, UUID> {
    Optional<Col> findColByDeskAndPosition(Desk desk, Integer position);

    List<Col> findByDeskId(UUID deskId);
}
