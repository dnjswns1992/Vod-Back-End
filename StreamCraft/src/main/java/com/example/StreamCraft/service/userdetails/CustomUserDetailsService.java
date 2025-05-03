package com.example.StreamCraft.service.userdetails;

import com.example.StreamCraft.dto.user.UserRegisterDto;
import com.example.StreamCraft.dto.user.UserInfoResponseDto;
import com.example.StreamCraft.Repository.user.UserRepository;
import com.example.StreamCraft.Entity.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
/**
 * ✅ Spring Security에서 사용자 인증 시 호출되는 커스텀 UserDetailsService 구현체
 *
 * - 로그인 시 사용자 이름(아이디)으로 DB에서 사용자 정보를 조회
 * - 조회된 정보를 기반으로 UserDetails 객체(UserDetailsInformation)를 반환
 */
@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    // 일반 로그인 사용자의 정보를 조회하는 JPA 리포지토리
    private final UserRepository repository;

    /**
     * ✅ 사용자 이름(username)으로 UserDetails 객체를 반환
     * - Spring Security가 내부적으로 인증할 때 호출함
     *
     * @param username 로그인 시 입력한 사용자명
     * @return UserDetailsInformation (UserDetails + OAuth2User 통합 클래스)
     * @throws UsernameNotFoundException 사용자 존재하지 않음
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 사용자 정보 DTO
        UserRegisterDto dto;
        // 사용자 이름으로 UserEntity 조회
        Optional<UserEntity> userEntity = repository.findUserEntityByUsername(username);

        // 존재하는 경우
        if(userEntity.isPresent()) {
            // Entity → DTO 변환
            ModelMapper mapper = new ModelMapper();
            dto = mapper.map(userEntity.get(), UserRegisterDto.class);

            // DTO를 UserInfoResponseDto 로 변환 후 UserDetails 객체 생성
            UserInfoResponseDto information = new UserInfoResponseDto(dto);
            UserDetailsInformation detailsInformation = new UserDetailsInformation(information);

            return detailsInformation;
        }else {
            // 존재하지 않으면 인증 예외 발생
            throw new UsernameNotFoundException("인증 처리 불가");
        }
    }
}
