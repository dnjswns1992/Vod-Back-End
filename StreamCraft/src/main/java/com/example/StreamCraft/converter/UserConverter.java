package com.example.StreamCraft.converter;


import com.example.StreamCraft.dto.user.UserRegisterDto;
import com.example.StreamCraft.Handler.ErrorHandler.DuplicateEmailException;
import com.example.StreamCraft.Handler.ErrorHandler.DuplicateNickNameException;
import com.example.StreamCraft.Handler.ErrorHandler.DuplicateUsernameException;
import com.example.StreamCraft.Oauth2.ProviderUser.ProviderUser;
import com.example.StreamCraft.Repository.CommonRepository;
import com.example.StreamCraft.Repository.Oauth2UserRepository;
import com.example.StreamCraft.Repository.UserRepository;
import com.example.StreamCraft.Service.S3Serivce.S3Service;
import com.example.StreamCraft.Table.Common.CommonEntity;
import com.example.StreamCraft.Table.UserAccount.Oauth2Entity;
import com.example.StreamCraft.Table.UserAccount.UserEntity;
import com.example.StreamCraft.UserAccount.TransferModelMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * 사용자 등록 처리용 Converter 클래스
 * - Form 회원가입
 * - Oauth2 회원가입
 */

@Data
@Slf4j
public class UserConverter {

    private final PasswordEncoder encoder;
    private final UserRepository repository;
    private final Oauth2UserRepository oauth2UserRepository;
    private final CommonRepository commonRepository;
    private final S3Service s3Service;


    /**
     * 일반 Form 로그인 방식의 회원가입 처리 메서드
     * @param userRegisterDto 회원가입 입력 정보
     * @param servletRequest 사용자 요청 정보 (IP 확인용)
     * @param multipartFile 프로필 이미지
     * @return 성공 시 true, 실패 시 false
     */
    public boolean FormLoginUserRegister(UserRegisterDto userRegisterDto, HttpServletRequest servletRequest, MultipartFile multipartFile) {


            // Dto → Entity 변환기
            TransferModelMapper<UserRegisterDto,UserEntity> modelMapper = new TransferModelMapper<>();


            // 중복 체크 (닉네임, 이메일, 유저네임)
            if (repository.existsByNickName(userRegisterDto.getNickname())) {
                throw new DuplicateNickNameException("NickName Duplicate");
            }

            if (repository.existsByEmail(userRegisterDto.getEmail())) {
                throw new DuplicateEmailException("Email Duplicate");
            }

            if (repository.existsByUsername(userRegisterDto.getUsername())) {
                throw new DuplicateUsernameException("UserName Duplicate");
            }


            // 이미 존재하는 유저인지 확인
            Optional<UserEntity> userEntityByUsername = repository.findUserEntityByUsername(userRegisterDto.getUsername());

        // 존재하지 않을 경우 등록 처리

        if(userEntityByUsername.isEmpty()) {

              try {

                  // 유저 정보 세팅

                  userRegisterDto.setRole("ROLE_USER");
                  userRegisterDto.setNickname(userRegisterDto.getNickname());
                  userRegisterDto.setProvider("FormLogin");
                  userRegisterDto.setPassword(encoder.encode(userRegisterDto.getPassword()));

                  // 이미지 S3 업로드
                  String upload = s3Service.upload(multipartFile);

                  // DTO → Entity 변환 후 저장
                  UserEntity entity = modelMapper.getTransfer(userRegisterDto, UserEntity.class);
                  entity.setEmail(userRegisterDto.getEmail() == null ? "이메일 없음" : userRegisterDto.getEmail());
                  entity.setImageUrl(upload);
                  repository.save(entity);

                  CommonEntity commonEntity = new CommonEntity(entity);

                  // IP 주소 저장
                  entity.setUserIp(servletRequest.getRemoteAddr());

                  // 공용 테이블(CommonEntity)에도 저장
                  commonRepository.save(commonEntity);

                  return true;
              }catch (Exception e) {
                  log.info("error = {} ",e.getMessage());

              }
            }
            return false;
    }
    /**
     * OAuth2 방식의 회원가입 시 공통 정보 저장
     * @param providerUser OAuth2 제공자 정보
     * @param oauth2Entity 사용자 정보
     */
    public void Oauth2UserRegister(ProviderUser providerUser, Oauth2Entity oauth2Entity){

        CommonEntity build = CommonEntity.builder().username(oauth2Entity.getEmail())
                .provider(oauth2Entity.getProvider())
                .nickname(oauth2Entity.getNickName())
                .role(oauth2Entity.getRole())
                .oauth2Entity(oauth2Entity).build();

        commonRepository.save(build);


    }
}
