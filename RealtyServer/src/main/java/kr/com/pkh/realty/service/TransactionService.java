package kr.com.pkh.realty.service;

import kr.com.pkh.realty.entity.UserInfoEntity;
import kr.com.pkh.realty.entity.UserLogEntity;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class TransactionService {

    
    @Autowired
    UserLogService userLogService;


}
