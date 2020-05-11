package com.webdrp.entity.vo;

import com.webdrp.entity.Commodity;
import com.webdrp.entity.Propcard;
import lombok.Data;

@Data
public class PropcardVo extends Propcard {

    private Commodity commodity;
}
