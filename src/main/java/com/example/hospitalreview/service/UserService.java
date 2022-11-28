package com.example.hospitalreview.service;


import com.example.hospitalreview.domain.User;
import com.example.hospitalreview.domain.dto.UserDto;
import com.example.hospitalreview.domain.dto.UserJoinRequest;
import com.example.hospitalreview.exception.ErrorCode;
import com.example.hospitalreview.exception.HospitalReviewAppException;
import com.example.hospitalreview.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public UserDto join(UserJoinRequest request){

        // userName이 중복되었으면 회원가입X -> Exception(예외) 발생

        userRepository.findByUserName(request.getUserName())
                .ifPresent(user-> {throw new HospitalReviewAppException(ErrorCode.DUPLICATED_USER_NAME, String.format("UserName:%s", request.getUserName()));});


        
        // save는 entity의 형태를 받음 -> entity로 변환해줌
        User savedUser = userRepository.save(request.toEntity());
        log.info(savedUser.getUserName());

        return UserDto.builder()
                .id(savedUser.getId())
                .userName(savedUser.getUserName())
                .emailAddress(savedUser.getEmailAddress())
                .build();
    }


}
