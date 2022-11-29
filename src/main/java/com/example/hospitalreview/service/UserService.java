package com.example.hospitalreview.service;


import com.example.hospitalreview.domain.User;
import com.example.hospitalreview.domain.dto.UserDto;
import com.example.hospitalreview.domain.dto.UserJoinRequest;
import com.example.hospitalreview.exception.ErrorCode;
import com.example.hospitalreview.exception.HospitalReviewAppException;
import com.example.hospitalreview.repository.UserRepository;
import com.example.hospitalreview.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;


    @Value("${jwt.token.secret}") 
    private String secretKey;
    // 어노테이션을 통해서 edit configuration에서 설정하는 환경변수나 yml에 환경 변수를 설정하여 넣어줄 수 있음
    private Long expireTimeMs = Long.valueOf(1000*60*60);

    public UserDto join(UserJoinRequest request){

        // userName이 중복되었으면 회원가입X -> Exception(예외) 발생

        userRepository.findByUserName(request.getUserName())
                .ifPresent(user-> {throw new HospitalReviewAppException(ErrorCode.DUPLICATED_USER_NAME, String.format("UserName:%s", request.getUserName()));});


        
        // save는 entity의 형태를 받음 -> entity로 변환해줌
        User savedUser = userRepository.save(request.toEntity(encoder.encode(request.getPassword())));
        log.info(savedUser.getUserName());

        return UserDto.builder()
                .id(savedUser.getId())
                .userName(savedUser.getUserName())
                .emailAddress(savedUser.getEmailAddress())
                .build();
    }


    public String login(String userName, String password) {

        // userName이 있는지 확인 -> 없으면 NOT_FOUND 에러

        User user = userRepository.findByUserName(userName)
                .orElseThrow(()-> new HospitalReviewAppException(ErrorCode.USER_NOT_FOUNDED, String.format("%s는 가입되지 않은 사용자의 id입니다.", userName)
        ));

        // password가 일치하는지 확인

        if(!encoder.matches(password, user.getPassword())){
            throw new HospitalReviewAppException(ErrorCode.INVALID_PASSWORD, "잘못된 비밀번호 입니다.");
        }

        
        // 두 가지 중 예외가 발생하지 않았으면 JWT 형식의 token 발행
        // key를 그대로 집어넣으면 절대 안됨!!
        return JwtTokenUtil.createToken(userName, secretKey, expireTimeMs);
    }
}
