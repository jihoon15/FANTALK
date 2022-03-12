package com.ssafy.api.service;

import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.db.repository.UserRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.ssafy.db.entity.User;
import com.ssafy.db.repository.UserRepository;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    UserRepositorySupport userRepositorySupport;
    PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(@Lazy UserRepository userRepository, @Lazy UserRepositorySupport userRepositorySupport, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userRepositorySupport = userRepositorySupport;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(UserRegisterPostReq userRegisterInfo) {
        User user = new User();
        user.setUsername(userRegisterInfo.getId());
        // 보안을 위해서 유저 패스워드 암호화 하여 디비에 저장.
        user.setPassword(passwordEncoder.encode(userRegisterInfo.getPassword()));
        user.setName(userRegisterInfo.getName());
        user.setEmail(userRegisterInfo.getEmail());
        user.setRole("ROLE_USER");
        return userRepository.save(user);
    }

    @Override
    public User getUserByUsername(String username) {
        // 디비에 유저 정보 조회 (userId 를 통한 조회).
        User user = userRepositorySupport.findUserByUsername(username).get();
        return user;
    }

    @Override
    public void removeUser(String username) {
        // 유저 정보 삭제 (username을 통한 삭제)
        User user = userRepository.findByUsername(username);
        userRepository.delete(user);

    }

    // 회원 ID로 조회
    @Override
    public User findById(Long id) {

        try{
            User user = userRepository.findById(id).get();
            return user;
        }catch(Exception e){
            throw e;
        }
    }
}