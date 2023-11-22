package com.intervlgo.ourfolio.entity;

import com.intervlgo.ourfolio.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Table(name = "users")
public class User extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_pk")
    private Long pk;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "user_password")
    private String userPassword;
    private String username;
    private String region;
    private String occupation;
    @Column(name="is_having_job")
    private Boolean isHavingJob;
    @Column(name = "is_enabled")
    private Boolean isEnabled;

    public UserDto toDto() {
        UserDto userDto = new UserDto();
        userDto.setUserId(userId);
        userDto.setUsername(username);
        userDto.setRegion(region);
        userDto.setOccupation(occupation);
        userDto.setIsHavingJob(isHavingJob);
        return userDto;
    }

    public void update(String username, String region, String occupation, Boolean isHavingJob){
        if (username != null && !username.isEmpty()) this.username= username;
        if (region != null && !region.isEmpty()) this.region = region;
        if (occupation != null && !occupation.isEmpty()) this.occupation = occupation;
        if (isHavingJob != null) this.isHavingJob = isHavingJob;
    }

    public void update(String Id, String userPassword) {
        if (userPassword != null) this.userPassword = userPassword;
        if (Id != null) this.userId = Id;
    }

    public void deactivateAccount() {
        isEnabled = false;
    }

    public void activateAccount() {
        isEnabled = true;
    }

    @Override
    public boolean equals(Object obj) {
        User user;
        if (obj instanceof User) {
            user = (User) obj;
        } else {
            return false;
        }
        return this.userId.equals(user.getUserId());
    }
}
