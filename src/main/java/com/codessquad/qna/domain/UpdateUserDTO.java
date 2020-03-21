package com.codessquad.qna.domain;

public class UpdateUserDTO {
    private String newPassword;
    private String checkPassword;
    private String name;
    private String email;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getCheckPassword() {
        return checkPassword;
    }

    public void setCheckPassword(String checkPassword) {
        this.checkPassword = checkPassword;
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
        return "UpdateUserDTO{" +
                "newPassword='" + newPassword + '\'' +
                ", checkPassword='" + checkPassword + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public boolean isCheckFail() {
        return !newPassword.equals(checkPassword);
    }
}
