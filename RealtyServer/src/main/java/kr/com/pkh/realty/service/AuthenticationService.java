package kr.com.pkh.realty.service;

import kr.com.pkh.realty.repository.UserInfoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import kr.com.pkh.realty.entity.UserInfoEntity;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

	private final ModelMapper modelMapper;
	private final UserInfoRepository userInfoRepository;
	
	public UserInfoEntity login(String userId, String userPw) throws Exception {

		// 향후 user pw 단방향 암호화 + base64 인코딩 예정

 		return userInfoRepository.findByUserId(userId).filter(m -> m.getUserPw().equals(userPw)).orElse(null);
	}
	
	
}
