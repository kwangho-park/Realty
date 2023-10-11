package kr.com.pkh.batch.extend.validationParam;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.util.StringUtils;

// batch 실행 시 입력받는 parameter 의 csv 파일여부를 검증하는 비즈니스 로직
public class FileParamValidator implements JobParametersValidator {
    @Override
    public void validate(JobParameters parameters) throws JobParametersInvalidException {
        String fileName = parameters.getString("fileName");

        if(!StringUtils.endsWithIgnoreCase(fileName, "csv")){
            throw new JobParametersInvalidException("this is not csv file");
        }
    }
}
