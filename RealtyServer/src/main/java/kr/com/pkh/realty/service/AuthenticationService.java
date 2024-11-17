package kr.com.pkh.realty.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

	/*public UserInfoEntity login(String userId, String userPw) throws Exception {

		// [고도화 예정] user pw 단방향 암호화 + base64 인코딩 예정
		// findByUserId() : userId 사용자 검색
		// filter() : 검색 결과(entity) 중 pw 를 비교
		// orElse() : 데이터가 존재하지 않는 경우에는 null 반환
		return userInfoRepository.findByUserId(userId).filter(entity -> entity.getUserPw().equals(userPw)).orElse(null);

	}*/
	
	
}
