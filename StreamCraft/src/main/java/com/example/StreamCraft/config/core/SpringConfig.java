package com.example.StreamCraft.config.core;


import com.example.StreamCraft.Manager.CommentManager;
import com.example.StreamCraft.Manager.PostManager;
import com.example.StreamCraft.Repository.post.CommentRepository;
import com.example.StreamCraft.Repository.post.PostRepository;
import com.example.StreamCraft.Repository.user.CommonRepository;
import com.example.StreamCraft.Repository.user.Oauth2UserRepository;
import com.example.StreamCraft.Repository.user.UserRepository;
import com.example.StreamCraft.oauth2.Model.AbstractDelegatingOauth2Converter;
import com.example.StreamCraft.oauth2.Model.ProviderOauth2Converter;
import com.example.StreamCraft.oauth2.Model.ProviderUserRequest;
import com.example.StreamCraft.oauth2.ProviderUser.ProviderUser;
import com.example.StreamCraft.service.S3Serivce.S3Service;
import com.example.StreamCraft.UserAccount.TransferModelMapper;
import com.example.StreamCraft.converter.UserConverter;
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
