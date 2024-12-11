
package kr.com.pkh.batch.singleton;

import lombok.Data;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

/**
 * 부동산 정보 수집 범위를 지정하는 싱글톤 <br>
 * 범위 : (페이지) , 수집기간 , 지역코드
 *
 * /////// [고도화] job, step 의 실행상태를 유지하고 데이터를 저장하는 'ExecutionContext' 을 사용하는 것으로 변경예정임
 */
@Data
public class AptTradeScope {

    private static AptTradeScope instance;

    private boolean ScopeFlag=true; // reader 최초 동작 시 초기값을 설정하기 위한 flag

    // page //
    private int pageNo = 0;         // 페이지 번호
    private int numOfRows=0;       // 페이지당 row 수
    private int totalPage = 0;      // 전체 페이지 수

    // date //
    private YearMonth startDate;   // 초기화 모드인 경우 수집 시작 날짜 (YYYY-MM)
    private YearMonth endDate;     // 초기화 모드인 경우 수집 종료 날짜 (YYYY-MM)

    // region //
    // 지역코드정보 저장
    private List<Integer> regionCodeList = new ArrayList<>();
    private int regionSeq = 1;

    private AptTradeScope(){}
    private AptTradeScope(int pageNo, int numOfRows, int totalPage, YearMonth startDate, YearMonth endDate){
        this.pageNo=pageNo;
        this.numOfRows = numOfRows;
        this.totalPage = totalPage;
        this.startDate=startDate;
        this.endDate=endDate;
    }

    public static synchronized AptTradeScope getInstance() {

        if (instance == null) {
            instance = new AptTradeScope();
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
    public synchronized void updateTotalPage(String totalCount, int numOfRows){
        int totalCountInt=Integer.parseInt(totalCount);
        if(totalCountInt <= numOfRows){
            totalPage=1;
        } else if(totalCountInt % numOfRows==0){
            totalPage= totalCountInt / numOfRows;
        }else {
            totalPage= totalCountInt / numOfRows;
            totalPage++;
        }
    }

    // total page 초기화 함수
    public synchronized void initTotalPage(){
        totalPage=0;
    }

    // startDate 증가 함수
    public synchronized void incrementStartDate(){
        startDate = startDate.plusMonths(1);
    }

    public synchronized void incrementRegionSeq(){
        regionSeq++;}
    public synchronized int getRegionCodeSize(){
        return regionCodeList.size() ;
    }

}
