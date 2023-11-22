package com.intervlgo.ourfolio.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class UserIdPasswordDto {
    private String newId;

    @NotNull
    private String userPassword;
    private String newPassword;
}
