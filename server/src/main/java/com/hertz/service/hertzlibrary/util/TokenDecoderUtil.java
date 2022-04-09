package com.hertz.service.hertzlibrary.util;

import static org.springframework.util.StringUtils.hasText;

public class TokenDecoderUtil {

    public static final String TOKEN_SUFFIX = "-token";

    private TokenDecoderUtil() {
    }

    public static String userName(final String token) {
        if (hasText(token)) {
            return token.replace(TOKEN_SUFFIX, "");
        }
        throw new IllegalArgumentException("User token is missing");
    }

}
