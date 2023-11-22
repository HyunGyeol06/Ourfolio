package com.intervlgo.ourfolio.entity;

import com.intervlgo.ourfolio.dto.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.util.Lazy;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Table(name = "comment")
public class Comment extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_pk")
    private User user;

    private String content;

    public CommentDto toDto() {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(id);
        commentDto.setWriter(user.toDto());
        commentDto.setContent(content);
        commentDto.setCreatedAt(getCreatedAt());
        commentDto.setModifiedAt(getModifiedAt());

        return commentDto;
    }

    public void update(String content) {
        this.content = content;
    }
}
