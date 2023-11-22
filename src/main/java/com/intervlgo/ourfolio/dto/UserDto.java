package com.intervlgo.ourfolio.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class UserDto {

    private String userId;

    private String userPassword;

    private String username;

    private String region;

    private String occupation;

    private Boolean isHavingJob;
}
