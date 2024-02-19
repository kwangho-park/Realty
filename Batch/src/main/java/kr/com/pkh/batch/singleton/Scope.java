package kr.com.pkh.batch.singleton;

import lombok.Data;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 부동산 정보 수집 범위를 지정하는 싱글톤 <br>
 * 범위 : (페이지) , 수집기간 , 지역코드
 */
@Data
public class Scope {

    private static Scope instance;

    private boolean ScopeFlag=true; // reader 최초 동작 시 startDate, endDate 설정하기 위한 flag

    // page //
    private int pageNo = 1;         // 페이지 번호
    private int numOfRows=10;       // 페이지당 row 수
    private int totalPage = 0;      // 전체 페이지 수

    // date //
    private YearMonth startDate;   // 초기화 모드인 경우 수집 시작 날짜 (YYYY-MM)
    private YearMonth endDate;     // 초기화 모드인 경우 수집 종료 날짜 (YYYY-MM)

    // region //
    // 지역코드정보 저장
    private List<Integer> regionCodeList = new ArrayList<>();
    private int regionId = 1;

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

        if(totalCount % numOfRows==0){
            totalPage= totalCount / numOfRows;
        }else {
            totalPage= totalCount / numOfRows;
            totalPage++;
        }
    }

    public synchronized void updateTotalPage(String totalCount, int numOfRows){
        int totalCountInt=Integer.parseInt(totalCount);
        totalPage= totalCountInt / numOfRows;
    }
    public synchronized void updateTotalPage(String totalCount, String numOfRows){
        int totalCountInt=Integer.parseInt(totalCount);
        int numOfRowsInt=Integer.parseInt(numOfRows);
        totalPage= totalCountInt / numOfRowsInt;
    }


    // total page 초기화 함수
    public synchronized void initTotalPage(){
        totalPage=0;
    }

    // startDate 증가 함수
    public synchronized void incrementStartDate(){
        startDate = startDate.plusMonths(1);
    }

    public synchronized void incrementRegionId(){regionId++;}
    public synchronized int regionCodeSize(){
        return regionCodeList.size();
    }
}
