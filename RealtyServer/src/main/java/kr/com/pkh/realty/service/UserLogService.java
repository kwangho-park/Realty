package kr.com.pkh.realty.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserLogService {
    //private final UserLogRepository userLogRepository;
    private final ModelMapper modelMapper;
/*
    public void login(UserLogEntity userLogEntity){

        userLogRepository.save(userLogEntity);
    }*/

}
