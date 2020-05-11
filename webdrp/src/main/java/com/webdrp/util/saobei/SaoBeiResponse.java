package com.webdrp.util.saobei;

/**
 * @Author: zhang yuan ming
 * @Date: create in 15:04 2019-11-20
 * @mail: zh878998515@gmail.com
 * @Description:
 */
public class SaoBeiResponse {

    /**
     * return_code : 01
     * return_msg : success
     */

    private String return_code;
    private String return_msg;

    public static SaoBeiResponse SUCCESS  = new SaoBeiResponse("01","success");

    public static SaoBeiResponse FALSE  = new SaoBeiResponse("001","false");


    public SaoBeiResponse(String return_code, String return_msg) {
        this.return_code = return_code;
        this.return_msg = return_msg;
    }

    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }
}
