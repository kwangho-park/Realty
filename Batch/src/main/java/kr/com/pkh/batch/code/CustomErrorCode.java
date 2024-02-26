package kr.com.pkh.batch.code;

public enum CustomErrorCode {

    REGION_FLAG("특정 지역코드의 전체 데이터 조회 완료"),
    DATE_FLAG("특정 수집날짜의 전체 데이터 조회 완료");

    private final String description;
    CustomErrorCode(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

}
