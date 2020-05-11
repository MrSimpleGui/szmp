package com.webdrp.entity.vo;

import com.webdrp.entity.CommissionMode;
import com.webdrp.entity.Grade;
import lombok.Data;

import java.util.List;

@Data
public class GradeVo extends Grade {

    private List<CommissionMode> modeList;
}
