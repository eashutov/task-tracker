package ru.shutov.itone.tasktracker.mapper;

import org.mapstruct.Mapper;
import ru.shutov.itone.tasktracker.dto.get.MemberDto;
import ru.shutov.itone.tasktracker.dto.post.MembershipPostDto;
import ru.shutov.itone.tasktracker.entity.Membership;
import ru.shutov.itone.tasktracker.entity.User;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring",
        uses = {CommentMapper.class},
        config = UnmappedPolicyMapperConfig.class)
public interface MembershipMapper {
    Membership toModel(MembershipPostDto membershipPostDto);

    List<MemberDto> toDto(List<Membership> memberships);

    default UUID mapToUUID(User user) {
        return user.getId();
    }
}
