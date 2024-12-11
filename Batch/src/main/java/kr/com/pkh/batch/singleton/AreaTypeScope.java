package kr.com.pkh.batch.singleton;


import kr.com.pkh.batch.dto.db.AptTradeDTO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 부동산 아파트 주소, 면적 타입 수집범위를 지정하는 싱글통 <br>
 * 범위 : (페이지), pnu list
 */
@Data
public class AreaTypeScope {
    private static AreaTypeScope instance;

    private boolean ScopeFlag=true; // reader 최초 동작 시 초기값을 설정하기 위한 flag

    // page //
    private int pageNo = 0;         // 페이지 번호
    private int numOfRows=0;       // 페이지당 row 수
    private int totalPage = 0;      // 전체 페이지 수

    // pnu 리스트 저장
    private List<AptTradeDTO> pnuList = new ArrayList<AptTradeDTO>();
    private int punSeq=1;

    private AreaTypeScope(){}

    private AreaTypeScope(int pageNo, int numOfRows, int totalPage){
        this.pageNo=pageNo;
        this.numOfRows = numOfRows;
        this.totalPage = totalPage;
    }

    public static synchronized AreaTypeScope getInstance(){

        if(instance==null){
            instance=new AreaTypeScope();
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

    public synchronized void incrementPnuSeq(){
        punSeq++;
    }
    public synchronized int getPnuListSize(){
        return pnuList.size();
    }



}
