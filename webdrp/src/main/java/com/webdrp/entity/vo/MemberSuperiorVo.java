package com.webdrp.entity.vo;

import com.webdrp.entity.Member;
import lombok.Data;

@Data
public class MemberSuperiorVo extends Member {
    private Member superior;
    private Double income;
    private Double withdrawal;
}
