package ru.shutov.itone.tasktracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.shutov.itone.tasktracker.entity.Col;
import ru.shutov.itone.tasktracker.entity.Desk;

import java.util.List;
import java.util.UUID;

@Repository
public interface ColRepository extends JpaRepository<Col, UUID> {
    List<Col> findByDeskId(UUID deskId);

    List<Col> findByDeskOrderByPositionAsc(Desk desk);

    @Query("SELECT MAX(c.position) FROM Col c WHERE c.desk = :desk")
    Integer findMaxPositionByDeskId(@Param("desk") Desk desk);
}
