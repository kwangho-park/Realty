package kr.com.pkh.batch.singleton;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import java.time.YearMonth;

/**
 * 부동산 정보 수집 범위를 지정하는 싱글톤
 */
@Data
public class Scope {

    private static Scope instance;

    private YearMonth startDate;   // 초기화 모드인 경우 수집 시작 날짜 (YYYY-MM)
    private YearMonth endDate;     // 초기화 모드인 경우 수집 종료 날짜 (YYYY-MM)


    private int pageNo = 1;         // 페이지 번호
    private int numOfRows=10;       // 페이지당 row 수
    private int totalPage = 0;      // 전체 페이지 수

    private boolean ScopeFlag=true; // reader 최초 동작 시 startDate, endDate 설정하기 위한 flag

    private Scope(){}
    private Scope(int pageNo, int numOfRows, int totalPage, YearMonth startDate, YearMonth endDate){
        this.pageNo=pageNo;
        this.numOfRows = numOfRows;
        this.totalPage = totalPage;
        this.startDate=startDate;
        this.endDate=endDate;
    }

    public static synchronized Scope getInstance() {

        if (instance == null) {
            instance = new Scope();
        }
        return instance;
    }

    // 페이지 번호 증가
    public synchronized void incrementPageNo(){
        pageNo++;
    }

    // 페이지 번호 초기화
    public synchronized void initPageNo(){
        pageNo=1;
    }

    // total page 업데이트
    public synchronized void updateTotalPage(int totalCount, int numOfRows){
//        int totalCountInt=Integer.parseInt(totalCount);
//        int numOfRowsInt=Integer.parseInt(numOfRows);

        totalPage= totalCount / numOfRows;
    }

    // total page 초기화 함수
    public synchronized void initTotalPage(){
        totalPage=0;
    }

    // startDate 증가 함수
    public synchronized void incrementStartDate(){
        startDate = startDate.plusMonths(1);
    }
}
