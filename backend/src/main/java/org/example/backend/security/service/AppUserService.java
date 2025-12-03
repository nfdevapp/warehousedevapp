package org.example.backend.security.service;

import lombok.RequiredArgsConstructor;
import org.example.backend.model.entities.AppUser;
import org.example.backend.repository.AppUserRepo;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppUserService extends DefaultOAuth2UserService {

    private final AppUserRepo appUserRepo;

    public OAuth2User loadUser(OAuth2UserRequest userRequest) {

        OAuth2User oauthUser = super.loadUser(userRequest);
        AppUser appUser = appUserRepo.findById(oauthUser.getName())
                .orElseGet(() -> createAndSaveUser(oauthUser));

        return new DefaultOAuth2User(List.of(new SimpleGrantedAuthority(appUser.role())),
                oauthUser.getAttributes(), "id")
        ;
    }

    private AppUser createAndSaveUser(OAuth2User oauthUser) {
        AppUser newUser = AppUser.builder()
                .id(oauthUser.getName())//Userid von GitHub
                .name(oauthUser.getAttribute("login"))
                .role("USER")
//                .role("ADMIN")
                .build();
        appUserRepo.save(newUser);

        return newUser;
    }
}
