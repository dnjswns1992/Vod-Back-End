package com.example.springsecurity04.Converter;


import com.example.springsecurity04.Dto.UserDto;
import com.example.springsecurity04.Handler.ErrorHandler.DuplicateEmailException;
import com.example.springsecurity04.Handler.ErrorHandler.DuplicateNickNameException;
import com.example.springsecurity04.Handler.ErrorHandler.DuplicateUsernameException;
import com.example.springsecurity04.Oauth2.ProviderUser.ProviderUser;
import com.example.springsecurity04.Repository.CommonRepository;
import com.example.springsecurity04.Repository.Oauth2UserRepository;
import com.example.springsecurity04.Repository.UserRepository;
import com.example.springsecurity04.Service.S3Serivce.S3Service;
import com.example.springsecurity04.Table.Common.CommonEntity;
import com.example.springsecurity04.Table.UserAccount.Oauth2Entity;
import com.example.springsecurity04.Table.UserAccount.UserEntity;
import com.example.springsecurity04.UserAccount.TransferModelMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Data
@Slf4j
public class UserConverter {

    private final PasswordEncoder encoder;
    private final UserRepository repository;
    private final Oauth2UserRepository oauth2UserRepository;
    private final CommonRepository commonRepository;
    private final S3Service s3Service;




    public boolean FormLoginUserRegister(UserDto userDto, HttpServletRequest servletRequest, MultipartFile multipartFile) {

        TransferModelMapper<UserDto,UserEntity> modelMapper = new TransferModelMapper<>();

        if(repository.existsByNickName(userDto.getNickname())) throw new DuplicateNickNameException("NickName Duplicate :");

        if(repository.existsByEmail(userDto.getEmail())) throw new DuplicateEmailException("Email Duplicate");

        if(repository.existsByUsername(userDto.getUsername())) throw new DuplicateUsernameException("UserName Duplicate");

        Optional<UserEntity> userEntityByUsername = repository.findUserEntityByUsername(userDto.getUsername());


        if(userEntityByUsername.isEmpty()) {

          try {


              userDto.setRole("ROLE_ADMIN");
              userDto.setNickname(userDto.getNickname());
              userDto.setProvider("FormLogin");
              userDto.setPassword(encoder.encode(userDto.getPassword()));

              String upload = s3Service.upload(multipartFile);

              UserEntity entity = modelMapper.getTransfer(userDto, UserEntity.class);
              entity.setEmail(userDto.getEmail() == null ? "이메일 없음" : userDto.getEmail());
              entity.setImageUrl(upload);
              repository.save(entity);

              CommonEntity commonEntity = new CommonEntity(entity);
              entity.setUserIp(servletRequest.getRemoteAddr());

              commonRepository.save(commonEntity);

              return true;
          }catch (Exception e) {
              log.info("error = {} ",e.getMessage());

          }
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
