package com.rikkei.gateway.web.rest;

import com.rikkei.gateway.security.oauth2.OAuth2AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


@RestController
@RequestMapping("/auth")
public class AuthResource {

    @Autowired
    private OAuth2AuthenticationService oAuth2AuthenticationService;

    @PostMapping("/login")
    public ResponseEntity login(
        HttpServletRequest request,
        HttpServletResponse response,
        @RequestBody Map<String, String> params
    ) {
        return ResponseEntity.ok(oAuth2AuthenticationService.login(request, response, params));
    }
}
