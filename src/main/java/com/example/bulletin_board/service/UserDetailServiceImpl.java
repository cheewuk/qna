package com.example.bulletin_board.service;

import com.example.bulletin_board.domain.UserEntity;
import com.example.bulletin_board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true) //사용자 정보를 DB에서 읽어옴
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //UserRepository를 사용하여 아이디로 사용자 정보를 조회
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("사용자를 찾을수 없습니다."));

        //조회된 UserEntity정보를 기반으로 SpringSecurity의 UserDetails 객체를 생성해서 반환
        //첫번째 파라미터: username(사용자 ID)
        //두번째 파라미터: password(DB에 저장된 암호화된 비밀번호)
        //세번째 파라미터: authorities (사용자의 권한 목록, Collection<? extends

        return User.builder()
                .username(userEntity.getUsername())
                .password(userEntity.getUserPassword()) //DB에 저장된 암호화된 비밀번호
                .authorities("ROLE_USER") //임시로 단일 권한 부여 (나중에 수정 필요)
                .build();

        //또는 직접 UserDetails 구현체를 만들어서 반환할수도있음

    }

}
