package com.intervlgo.ourfolio.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class PortfolioDto {
    private UserDto user;
    private String fileName;
    private String imgName;
    private String content;
    private String portFolioPageUrl;
    private Long viewCnt;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
