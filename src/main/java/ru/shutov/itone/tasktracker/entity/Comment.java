package ru.shutov.itone.tasktracker.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.shutov.itone.tasktracker.model.BaseEntity;

import java.sql.Timestamp;

@Entity
@Table(name = "comments")
@Getter
@Setter
@Builder
public class Comment extends BaseEntity {
    @Column(name = "comment_text")
    @NotBlank
    private String text;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author", referencedColumnName = "id")
    private User author;
}
