package com.hertz.service.hertzlibrary.util;

import static org.springframework.util.StringUtils.hasText;

/*
    Usually we would get the user identity by decoding some JWT, but for simplicity
    we'll just pretend all tokens to be in the form of "user_name-token"
 */
public class TokenDecoderUtil {

    public static final String TOKEN_SUFFIX = "-token";

    private TokenDecoderUtil() {
    }

    public static String userName(final String token) {
        if (hasText(token)) {
            if (token.endsWith(TOKEN_SUFFIX)) {
                var userName = token.replace(TOKEN_SUFFIX, "");

                if (hasText(userName)) {
                    return userName;
                }

                throw new IllegalArgumentException("Empty user name in token");
            }

            throw new IllegalArgumentException("Bad user token");
        }

        throw new IllegalArgumentException("User token is missing");
    }

}
