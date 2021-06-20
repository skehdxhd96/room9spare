package com.goomoong.room9backend.callback;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/oauth2/callback")
public class OAuth2CallbackController {

    // t0wX7Fu0e9opD47gnfACVQwZEZAbmGBiJwqTrcJyyE_lespYP76JNgLwFCNAdLZ1_xmdOQo9dJcAAAF6KI502A
    @GetMapping("/kakao")
    public Map<String, String> kakao(@RequestParam String code) {
        Map<String, String> res = new HashMap<>();
        res.put("code", code);


        MultiValueMap<String, String> tokenRequest = new LinkedMultiValueMap<>();
        tokenRequest.add("grant_type", "authorization_code");
        tokenRequest.add("client_id", "64dfaa62a542bcefe16d09bd77b6ca8c");
        tokenRequest.add("redirect_uri", "http://localhost:8080/oauth2/callback/kakao");
        tokenRequest.add("code", code);

        try {
            RestTemplate restTemplate = new RestTemplate();
            Map<String, String> response = restTemplate.postForObject("https://kauth.kakao.com/oauth/token", tokenRequest, Map.class);
            res.put("access_token", response.get("access_token"));

            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + response.get("access_token"));
            MultiValueMap<String, String> request2 = new LinkedMultiValueMap<>();
            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity(request2, headers);
            RestTemplate getUserInfoRequest = new RestTemplate();
            ResponseEntity<String> userInfo = getUserInfoRequest.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.GET, entity, String.class);
            res.put("userInfo", userInfo.getBody());
        } catch (Exception ex) {

        }
        return res;
    }
}
