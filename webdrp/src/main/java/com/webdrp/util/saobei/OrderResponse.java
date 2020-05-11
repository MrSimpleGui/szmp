package com.webdrp.util.saobei;

/**
 * @Author: zhang yuan ming
 * @Date: create in 14:44 2019-11-20
 * @mail: zh878998515@gmail.com
 * @Description:
 */
public class OrderResponse {


    /**
     * attach : 1155
     * channel_trade_no : 4200000450201911202794880984
     * end_time : 20191120143906
     * key_sign : 1bf4524209f951682d8117f390d49e77
     * merchant_name : 广州小野网络有限公司
     * merchant_no : 811000002000012
     * out_trade_no : 300609130021319112014385800004
     * pay_type : 010
     * receipt_fee : 100
     * result_code : 01
     * return_code : 01
     * return_msg : 支付成功
     * terminal_id : 30060913
     * terminal_time : 20191120143854
     * terminal_trace : d1155t1574231934593
     * total_fee : 100
     * user_id : o9pRS1f12RQdHQ9PJDfY_Tskt6Ww
     */

    private String attach;
    private String channel_trade_no;
    private String end_time;
    private String key_sign;
    private String merchant_name;
    private String merchant_no;
    private String out_trade_no;
    private String pay_type;
    private String receipt_fee;
    private String result_code;
    private String return_code;
    private String return_msg;
    private String terminal_id;
    private String terminal_time;
    private String terminal_trace;
    private String total_fee;
    private String user_id;

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getChannel_trade_no() {
        return channel_trade_no;
    }

    public void setChannel_trade_no(String channel_trade_no) {
        this.channel_trade_no = channel_trade_no;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getKey_sign() {
        return key_sign;
    }

    public void setKey_sign(String key_sign) {
        this.key_sign = key_sign;
    }

    public String getMerchant_name() {
        return merchant_name;
    }

    public void setMerchant_name(String merchant_name) {
        this.merchant_name = merchant_name;
    }

    public String getMerchant_no() {
        return merchant_no;
    }

    public void setMerchant_no(String merchant_no) {
        this.merchant_no = merchant_no;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getReceipt_fee() {
        return receipt_fee;
    }

    public void setReceipt_fee(String receipt_fee) {
        this.receipt_fee = receipt_fee;
    }

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
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

    public String getTerminal_id() {
        return terminal_id;
    }

    public void setTerminal_id(String terminal_id) {
        this.terminal_id = terminal_id;
    }

    public String getTerminal_time() {
        return terminal_time;
    }

    public void setTerminal_time(String terminal_time) {
        this.terminal_time = terminal_time;
    }

    public String getTerminal_trace() {
        return terminal_trace;
    }

    public void setTerminal_trace(String terminal_trace) {
        this.terminal_trace = terminal_trace;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "OrderResponse{" +
                "attach='" + attach + '\'' +
                ", channel_trade_no='" + channel_trade_no + '\'' +
                ", end_time='" + end_time + '\'' +
                ", key_sign='" + key_sign + '\'' +
                ", merchant_name='" + merchant_name + '\'' +
                ", merchant_no='" + merchant_no + '\'' +
                ", out_trade_no='" + out_trade_no + '\'' +
                ", pay_type='" + pay_type + '\'' +
                ", receipt_fee='" + receipt_fee + '\'' +
                ", result_code='" + result_code + '\'' +
                ", return_code='" + return_code + '\'' +
                ", return_msg='" + return_msg + '\'' +
                ", terminal_id='" + terminal_id + '\'' +
                ", terminal_time='" + terminal_time + '\'' +
                ", terminal_trace='" + terminal_trace + '\'' +
                ", total_fee='" + total_fee + '\'' +
                ", user_id='" + user_id + '\'' +
                '}';
    }
}
