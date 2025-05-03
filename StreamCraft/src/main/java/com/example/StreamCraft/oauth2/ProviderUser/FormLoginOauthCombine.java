package com.example.StreamCraft.oauth2.ProviderUser;

import com.example.StreamCraft.dto.user.UserInfoResponseDto;
import com.example.StreamCraft.Entity.commom.MergedUserEntity;
import com.example.StreamCraft.Entity.user.Oauth2Entity;
import com.example.StreamCraft.Entity.user.UserEntity;
import lombok.extern.slf4j.Slf4j;
/**
 * ✅ FormLoginOauthCombine 클래스
 *
 * - 다양한 사용자 엔티티를 공통 응답 DTO(UserInfoResponseDto)로 변환하는 유틸 클래스
 * - Spring Security에서 사용자 정보를 일관된 형식으로 사용하기 위한 목적
 */

@Slf4j
public class FormLoginOauthCombine {



    /**
     * ✅ Oauth2Entity → UserInfoResponseDto 변환
     * - 소셜 로그인 사용자의 정보 변환
     * - 소셜 로그인 사용자를 Dto로 변환하여 저장
     * @param entity Oauth2Entity (Google/Naver 로그인 사용자)
     * @return 변환된 사용자 DTO (비밀번호는 없음)
     */
    public static UserInfoResponseDto Oauth2Combine(Oauth2Entity entity){

     return   UserInfoResponseDto.builder()
               .role(entity.getRole())
               .username(entity.getEmail())
               .provider(entity.getProvider())
               .password("없음")
               .nickname(entity.getNickName()).build();
    }

    /**
     * ✅ UserEntity → UserInfoResponseDto 변환
     * - 일반 회원가입(Form Login) 사용자 정보 변환
     *
     * @param entity 일반 사용자(UserEntity)
     * @return 변환된 사용자 DTO
     */
    public static UserInfoResponseDto FormLoginCombine(UserEntity entity){
       return UserInfoResponseDto.builder()
                .role(entity.getRole())
                .username(entity.getUsername())
                .provider(entity.getProvider())
                .nickname(entity.getNickName())
                .password(entity.getPassword()).build();
    }
    /**
     * ✅ CommonEntity → UserInfoResponseDto 변환
     * - Oauth2 유저를 다시 공통 테이블로 이동하여 Oauth2 사용자와 일반 사용자를 통합하게 끔 함
     * - 공통 사용자 테이블(CommonEntity)을 기반으로 사용자 정보 반환
     * @param mergedUserEntity 공용 사용자 정보
     * @return 변환된 사용자 DTO (비밀번호는 없음)
     */
    public static UserInfoResponseDto CommonCombine(MergedUserEntity mergedUserEntity) {

     return    UserInfoResponseDto.builder()
                .role(mergedUserEntity.getRole())
                .provider(mergedUserEntity.getProvider())
                .username(mergedUserEntity.getUsername())
                .nickname(mergedUserEntity.getNickname())
                .password("없음").build();

    }
}
