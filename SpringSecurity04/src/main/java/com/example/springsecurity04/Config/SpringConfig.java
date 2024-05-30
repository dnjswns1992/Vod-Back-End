package com.example.springsecurity04.Config;


import com.example.springsecurity04.Manager.CommentManager;
import com.example.springsecurity04.Manager.PostManager;
import com.example.springsecurity04.Oauth2.Model.AbstractDelegatingOauth2Converter;
import com.example.springsecurity04.Oauth2.Model.ProviderOauth2Converter;
import com.example.springsecurity04.Oauth2.Model.ProviderUserRequest;
import com.example.springsecurity04.Oauth2.ProviderUser.ProviderUser;
import com.example.springsecurity04.Repository.*;
import com.example.springsecurity04.Service.S3Serivce.S3Service;
import com.example.springsecurity04.UserAccount.TransferModelMapper;
import com.example.springsecurity04.Converter.UserConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class SpringConfig {


     private final PasswordEncoder encoder;
    private final UserRepository repository;
    private final Oauth2UserRepository oauth2UserRepository;
    private final CommonRepository commonRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final S3Service S3service;




  @Bean
  public ProviderOauth2Converter converter(){
        return new AbstractDelegatingOauth2Converter() {
            @Override
            public ProviderUser converter(ProviderUserRequest providerUserRequest) {
                return super.converter(providerUserRequest);
            }
        };
  }
    @Bean
    public TransferModelMapper transferModelMapper(){
        return new TransferModelMapper();
    }
    @Bean
    public UserConverter userConverter(){
      return new UserConverter(encoder,repository,oauth2UserRepository,commonRepository,S3service);
    }
    @Bean
    public PostManager postManager(){
      return new PostManager(commonRepository,postRepository);
    }
    @Bean
    public CommentManager commentManager(){
      return new CommentManager(postRepository,commentRepository,commonRepository);
    }
}
