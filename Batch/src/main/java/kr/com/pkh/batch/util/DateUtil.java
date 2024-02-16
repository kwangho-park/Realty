package kr.com.pkh.batch.util;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class DateUtil {


    /**
     * String을 YearMonth 형식으로 파싱
     *
     * @param yearMonth : YYYYMM (string)
     * @return YYYY-MM (YearMonth)
     */
    public static YearMonth stringToYearMonth(String yearMonth){
        
        String formattedInput = yearMonth.substring(0, 4) + "-" + yearMonth.substring(4);
        return YearMonth.parse(formattedInput);
    }

    /**
     * YearMonth을 String 형식으로 파싱
     *
     * @param yearMonth
     * @return
     */
    public static String yearMonthToString(YearMonth yearMonth){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
        return yearMonth.format(formatter);
    }

}
