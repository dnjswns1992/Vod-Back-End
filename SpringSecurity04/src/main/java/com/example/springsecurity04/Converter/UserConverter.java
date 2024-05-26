package com.example.springsecurity04.Converter;


import com.example.springsecurity04.Dto.UserDto;
import com.example.springsecurity04.Error.DuplicateEmailException;
import com.example.springsecurity04.Error.DuplicateNickNameException;
import com.example.springsecurity04.Error.DuplicateUsernameException;
import com.example.springsecurity04.Oauth2.ProviderUser.ProviderUser;
import com.example.springsecurity04.Repository.CommonRepository;
import com.example.springsecurity04.Repository.Oauth2UserRepository;
import com.example.springsecurity04.Repository.UserRepository;
import com.example.springsecurity04.Table.Common.CommonEntity;
import com.example.springsecurity04.Table.UserAccount.Oauth2Entity;
import com.example.springsecurity04.Table.UserAccount.UserEntity;
import com.example.springsecurity04.UserAccount.TransferModelMapper;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Data
public class UserConverter {

    private final PasswordEncoder encoder;
    private final UserRepository repository;
    private final Oauth2UserRepository oauth2UserRepository;
    private final CommonRepository commonRepository;



    public boolean FormLoginUserRegister(UserDto userDto, HttpServletRequest servletRequest) {

        TransferModelMapper<UserDto,UserEntity> modelMapper = new TransferModelMapper<>();

        if(repository.existsByNickName(userDto.getNickname())) throw new DuplicateNickNameException("NickName Duplicate :");

        if(repository.existsByEmail(userDto.getEmail())) throw new DuplicateEmailException("Email Duplicate");

        if(repository.existsByUsername(userDto.getUsername())) throw new DuplicateUsernameException("UserName Duplicate");

        Optional<UserEntity> userEntityByUsername = repository.findUserEntityByUsername(userDto.getUsername());


        if(userEntityByUsername.isEmpty()) {
            userDto.setRole("ROLE_ADMIN");
            userDto.setNickname(userDto.getNickname());
            userDto.setProvider("FormLogin");
            userDto.setPassword(encoder.encode(userDto.getPassword()));

            UserEntity entity = modelMapper.getTransfer(userDto, UserEntity.class);
            entity.setEmail(userDto.getEmail() == null ? "이메일 없음" : userDto.getEmail());

            repository.save(entity);

            CommonEntity commonEntity = new CommonEntity(entity);
            entity.setUserIp(servletRequest.getRemoteAddr());

            commonRepository.save(commonEntity);

            return true;
        }
        return false;
    }
    public void Oauth2UserRegister(ProviderUser providerUser, Oauth2Entity oauth2Entity){

        CommonEntity build = CommonEntity.builder().username(oauth2Entity.getEmail())
                .provider(oauth2Entity.getProvider())
                .nickname(oauth2Entity.getNickName())
                .role(oauth2Entity.getRole())
                .oauth2Entity(oauth2Entity).build();

        commonRepository.save(build);


    }
}
