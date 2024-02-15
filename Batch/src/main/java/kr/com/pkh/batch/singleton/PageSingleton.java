package kr.com.pkh.batch.singleton;

import lombok.Data;

@Data
public class PageSingleton {
    private static PageSingleton instance;

    int pageNo = 1;     // 페이지 번호
    int numOfRows=10;   // 페이지당 row 수
    int totalCount = 0; // 전체 row 수


    private PageSingleton(){}
    private PageSingleton(int pageNo, int numOfRows, int totalCount){
        this.pageNo=pageNo;
        this.numOfRows = numOfRows;
        this.totalCount=totalCount;
    }

    public static PageSingleton getInstance() {

        if (instance == null) {
            instance = new PageSingleton();
        }
        return instance;
    }
}
