package com.codessquad.qna;

import com.codessquad.qna.domain.User;

import javax.servlet.http.HttpSession;

public class HttpUtils {
    public static final String SESSIONED_ID = "sessionedUser";

    public static boolean isNotLoginUser(User user) {
        return user == null;
    }

    public static User getSessionedUser(HttpSession httpSession) {
        return (User) httpSession.getAttribute(SESSIONED_ID);
    }
}
