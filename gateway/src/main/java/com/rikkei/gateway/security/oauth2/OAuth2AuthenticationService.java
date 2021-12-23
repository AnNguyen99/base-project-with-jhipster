package com.rikkei.gateway.security.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import tech.jhipster.config.JHipsterProperties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Service
public class OAuth2AuthenticationService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private JHipsterProperties jHipsterProperties;

    public OAuth2AccessToken login(HttpServletRequest request, HttpServletResponse response, Map<String, String> params) {
        String username = params.get("username");
        String password = params.get("password");
        boolean rememberMe = Boolean.parseBoolean(params.get("rememberMe"));
        String remoteAddr = request.getRemoteAddr();
        String remoteAddr2 = HttpRequestResponseUtils.getClientIp(request);
        String userAgent = request.getHeader("User-Agent");
        String xUserAgent = request.getHeader("X-User-Agent");
        params.put("clientIp", StringUtils.hasText(remoteAddr2) ? remoteAddr2 : remoteAddr);
        params.put("clientDevice", StringUtils.hasText(xUserAgent) ? xUserAgent : userAgent);

        return loginWithGrantPassword(username, password, params);
    }

    public OAuth2AccessToken loginWithGrantPassword(String username, String password, Map<String, String> params) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> formParams = new LinkedMultiValueMap<>();
        formParams.set("username", username);
        formParams.set("password", password);
        formParams.set("grant_type", "password");
        formParams.set("clientIp", params.get("clientIp"));
        formParams.set("clientDevice", params.get("clientDevice"));
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(formParams, httpHeaders);
        ResponseEntity<OAuth2AccessToken> responseEntity = restTemplate.postForEntity(getUrlTokenEndpoint(), entity, OAuth2AccessToken.class);

        return responseEntity.getBody();
    }

    protected String getUrlTokenEndpoint() {
        String tokenEndpointUrl = jHipsterProperties.getSecurity().getClientAuthorization().getAccessTokenUri();
        if (tokenEndpointUrl == null) {
            throw new InvalidClientException("no token endpoint configured in application properties");
        }
        return tokenEndpointUrl;
    }

}
