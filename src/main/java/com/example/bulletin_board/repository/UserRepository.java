package com.example.bulletin_board.repository;

import com.example.bulletin_board.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    //아이디로 사용자 정보를 조회하는 메소드
    //사용자가 없을경우 null 대신에 Optional.empty()를 반환하여 NullPointerException 방지
    Optional<UserEntity> findByUsername(String username);

    //아이디가 이미 존재하는지 확인하는 메소드
    boolean existsByUsername(String username);

    //닉네임이 이미 존재하는지 확인하는 메소드
    boolean existsByNickname(String nickname);


}
