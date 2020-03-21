package com.codessquad.qna.domain;

import javax.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 50)
    private String userId;
    @Column(nullable = false, length = 50)
    private String password;
    @Column(nullable = false, length = 50)
    private String name;
    @Column(nullable = false, length = 50)
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public boolean isSameUserId(String userId) {
        return this.userId.equals(userId);
    }

    public boolean isSameId(Long id) {
        return this.id.equals(id);
    }

    public void update(UpdateUserDTO updateUserDTO) {
        this.password = updateUserDTO.getNewPassword();
        this.name = updateUserDTO.getName();
        this.email = updateUserDTO.getEmail();
    }

    public boolean notMatchPassword(String password) {
        return !this.password.equals(password);
    }

//    public boolean notMatchWith(User sessionedUser) {
//        return !(this == sessionedUser);
//    }

    public boolean notMatchId(Long id) {
        return !this.id.equals(id);
    }
}
