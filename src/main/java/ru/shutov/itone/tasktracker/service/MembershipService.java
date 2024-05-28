package ru.shutov.itone.tasktracker.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shutov.itone.tasktracker.dto.get.MemberDto;
import ru.shutov.itone.tasktracker.dto.post.MembershipPostDto;
import ru.shutov.itone.tasktracker.entity.Desk;
import ru.shutov.itone.tasktracker.entity.Membership;
import ru.shutov.itone.tasktracker.exception.BusinessException;
import ru.shutov.itone.tasktracker.exception.event.EventInfoImpl;
import ru.shutov.itone.tasktracker.mapper.MembershipMapper;
import ru.shutov.itone.tasktracker.repository.DeskRepository;
import ru.shutov.itone.tasktracker.repository.MembershipRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MembershipService {
    private final DeskRepository deskRepository;
    private final MembershipRepository membershipRepository;
    private final MembershipMapper membershipMapper;

    public List<MemberDto> findAllMembers(UUID deskId) {
        List<Membership> members = membershipRepository.findByDeskId(deskId);
        return membershipMapper.toDto(members);
    }

    @Transactional
    public void addMember(UUID deskId, MembershipPostDto membershipPostDto) {
        Membership membership = membershipMapper.toModel(membershipPostDto);
        membership.setDesk(deskRepository.getReferenceById(deskId));
        membershipRepository.save(membership);
    }

    @Transactional
    public void removeMember(UUID deskId, UUID memberId) {
        Desk desk = deskRepository.findById(deskId).orElseThrow(() ->
                new BusinessException(EventInfoImpl.NOT_FOUND, "Desk with id: " + deskId + " not found"));

        if (desk.getCreator().getId().equals(memberId)) {
            throw new UnsupportedOperationException("Not possible to delete the desk creator");
        }
        membershipRepository.deleteAllByDeskIdAndMemberId(deskId, memberId);
    }
}
