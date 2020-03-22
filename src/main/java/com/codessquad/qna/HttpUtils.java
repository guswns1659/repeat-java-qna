package com.codessquad.qna;

import javax.servlet.http.HttpSession;

public class HttpUtils {
    public static final String SESSIONED_ID = "sessionedUser";

    public static boolean isNotLoginUser(HttpSession httpSession) {
        return httpSession.getAttribute(SESSIONED_ID) == null;
    }
}
