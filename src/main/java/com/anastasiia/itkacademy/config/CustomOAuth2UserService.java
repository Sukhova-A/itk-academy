package com.anastasiia.itkacademy.config;


import java.util.List;

import com.anastasiia.itkacademy.entity.Role;
import com.anastasiia.itkacademy.entity.User;
import com.anastasiia.itkacademy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String login = oAuth2User.getAttribute("login");
        User user = userRepository.findByLogin(login)
                .orElseGet(() -> newUser(oAuth2User, login));

        SimpleGrantedAuthority authority =
                new SimpleGrantedAuthority(user.getRole().name());

        log.info("OAuth2 login: {} ({})({})", user.getLogin(), user.getEmail(), user.getName());

        return new DefaultOAuth2User(
                List.of(authority),
                oAuth2User.getAttributes(),
                "login");
    }

    private User newUser(OAuth2User oAuth2User, String login) {
        User user = new User();
        user.setId(oAuth2User.getAttribute("id").toString());
        user.setLogin(login);
        user.setName(oAuth2User.getAttribute("name"));
        user.setEmail(oAuth2User.getAttribute("email"));
        user.setRole(Role.USER);
        return userRepository.save(user);
    }
}