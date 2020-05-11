package com.webdrp.entity.vo;

import com.webdrp.entity.Income;
import lombok.Data;

@Data
public class IncomeNameVo extends Income {
    private String targetNickname;
    private String originNickname;
}
