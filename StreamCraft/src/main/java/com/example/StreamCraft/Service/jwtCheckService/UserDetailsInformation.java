package com.example.StreamCraft.Service.jwtCheckService;

import com.example.StreamCraft.dto.user.UserInfoResponseDto;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Data
@Slf4j
public class UserDetailsInformation implements UserDetails, OAuth2User {



    private final UserInfoResponseDto information;

    public UserDetailsInformation(UserInfoResponseDto information) {
        this.information = information;
    }

    @Override
    public <A> A getAttribute(String name) {
        return null;
    }

    @Override
    public Map<String, Object> getAttributes() {
       return null;
    }



    //이 getRole가 -> SimpleGrantedAuthoritiy타입에 들어가서
    // SimpleGrantedAuthoritiy에 있는 getAuthoritiy()가
    // this.role을 반환하는것이 아니라 오버라이딩 해서 dto.getRole()로 반환되게끔 바꾼다.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return information.getRole();
            }
        });
        return authorities;
    }

    @Override
    public String getPassword() {
        return information.getPassword();
    }

    @Override
    public String getUsername() {
        return information.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return null;
    }
}
