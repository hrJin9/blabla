package com.blabla.helper;

import com.blabla.util.JwtTokenProvider;
import com.blabla.util.TokenGenerator;

public final class BearerAuthHelper {

    private static TokenGenerator tokenGenerator = new TokenGenerator(
            new JwtTokenProvider("eyJpc3MiOiJ2ZWxvcGVydC5jb20iLCJleHAiOiIxNDg1MjcwMDAwMDAwIiwiaHR0cHM6Ly92ZWxvcGVydC5jb20vand0X2NsYWltcy9pc19hZG1pbiI6dHJ1ZSwidXNlcklkIjoiMTEwMjgzNzM3MjcxMDIiLCJ1c2VybmFtZSI6InZlbG9wZXJ0In0")
    );

    public static String generateToken(Long id) {
        return tokenGenerator.generate(id).getAccessToken();
    }
}
