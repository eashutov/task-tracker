package ru.shutov.itone.tasktracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shutov.itone.tasktracker.entity.Membership;
import ru.shutov.itone.tasktracker.entity.User;

import java.util.List;
import java.util.UUID;

@Repository
public interface MembershipRepository extends JpaRepository<Membership, UUID> {
    List<Membership> findByMemberAndDeskId(User user, UUID deskId);

    void deleteAllByDeskIdAndMemberId(UUID deskId, UUID memberId);

    List<Membership> findByDeskId(UUID deskId);
}
