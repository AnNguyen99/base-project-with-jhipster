package com.rikkei.sso.config.oauth2;

public final class OAuth2Constants {

    public static final String CLIEN_ID = "clientId";
    public static final String CLIENT_SECRET = "secret";
    public static final String GRANT_TYPE_PASSWORD = "password";
    public static final String AUTHORIZATION_CODE = "authorization_code";
    public static final String REFRESH_TOKEN = "refresh_token";
    public static final String IMPLICIT = "implicit";
    public static final String SCOPE_READ = "read";
    public static final String SCOPE_WRITE = "write";
    public static final String TRUST = "trust";
    public static final int ACCESS_TOKEN_VALIDITY_SECONDS = 24 * 60 * 60;
    public static final int FREFRESH_TOKEN_VALIDITY_SECONDS = 7 * 24 * 60 * 60;
}
