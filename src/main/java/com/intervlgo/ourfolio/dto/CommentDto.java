package com.intervlgo.ourfolio.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentDto {
    private Long id;
    private UserDto writer;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
