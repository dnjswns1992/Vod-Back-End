package com.example.springsecurity04.Service.jwtCheckService;

import com.example.springsecurity04.Dto.UserDto;
import com.example.springsecurity04.Dto.UserInformation;
import com.example.springsecurity04.Repository.UserRepository;
import com.example.springsecurity04.Table.UserAccount.UserEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceCustom implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDto dto;

        Optional<UserEntity> userEntity = repository.findUserEntityByUsername(username);


        if(userEntity.isPresent()) {
            ModelMapper mapper = new ModelMapper();
            dto = mapper.map(userEntity.get(), UserDto.class);
            UserInformation information = new UserInformation(dto);
            UserDetailsInformation detailsInformation = new UserDetailsInformation(information);

            return detailsInformation;
        }else {
          throw new UsernameNotFoundException("인증 처리 불가");
        }
    }
}
