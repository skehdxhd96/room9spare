package com.goomoong.room9backend.callback;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goomoong.room9backend.domain.user.Role;
import com.goomoong.room9backend.domain.user.User;
import com.goomoong.room9backend.domain.user.UserRepository;
import com.goomoong.room9backend.domain.user.dto.KakaoOAuth2ResponseDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/oauth2/callback")
public class OAuth2CallbackController {

    private final UserRepository userRepository;

    // t0wX7Fu0e9opD47gnfACVQwZEZAbmGBiJwqTrcJyyE_lespYP76JNgLwFCNAdLZ1_xmdOQo9dJcAAAF6KI502A
    @GetMapping("/kakao")
    public Map<String, String> kakao(@RequestParam String code) {
        Map<String, String> res = new HashMap<>();
        res.put("code", code);

        String myJwtKey = "awP5PFvxzQs7wBRycYddIuSLCwaSWyAMcCvE4LnvJVU=";


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

            // 1. DB에 사용자가 있는지 확인
            // 2. 만약 없다면 데이터베이스에 삽입 (회원가입)
            // 3. 만약 있다면 패스
            // 4. 사용자 정보를 데이터베이스 가져옴
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            KakaoOAuth2ResponseDto responseDto = objectMapper.readValue(userInfo.getBody(), KakaoOAuth2ResponseDto.class);
            System.out.println(responseDto);

            User verifyUser = userRepository.findByAccountId(responseDto.getId()).orElseGet(() ->
                    userRepository.save(User.toEntity(responseDto.getId(), Role.CUSTOMER, responseDto.getKakaoAccount().getProfile().getNickname(), responseDto.getKakaoAccount().getProfile().getThumbnailImageUrl()))
            );

            String token = Jwts.builder()
                    .setSubject(verifyUser.getId().toString())
                    .setExpiration(new Date((new Date()).getTime() + 1000000000))
                    .signWith(Keys.hmacShaKeyFor(myJwtKey.getBytes()))
                    .compact();
            res.put("jwtToken", token);

        } catch (Exception ex) {
        }
        return res;
    }
}
