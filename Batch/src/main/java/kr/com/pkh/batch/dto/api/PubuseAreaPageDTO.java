package kr.com.pkh.batch.dto.api;

import kr.com.pkh.batch.dto.db.PageDTO;
import lombok.Data;

import java.util.List;

@Data
public class PubuseAreaPageDTO {

    List<PubuseAreaDTO> pubuseAreaDTOList;
    PageDTO pageDTO;
}
