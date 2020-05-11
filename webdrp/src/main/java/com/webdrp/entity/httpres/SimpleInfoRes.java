package com.webdrp.entity.httpres;

public class SimpleInfoRes {


    /**
     * code : 2000
     * msg : 获取用户信息成功
     * data : {"user_name":"13189550976","avatar":"http://wap.yyhz369.com/Uploads/avatar.jpg","passport_num":"60975973","real_name":"张远明","gender":"男","id_number":"4409******6055","expire_time":"2019-10-03","type_name":"黄金版会员"}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * user_name : 13189550976
         * avatar : http://wap.yyhz369.com/Uploads/avatar.jpg
         * passport_num : 60975973
         * real_name : 张远明
         * gender : 男
         * id_number : 4409******6055
         * expire_time : 2019-10-03
         * type_name : 黄金版会员
         */

        private String user_name;
        private String avatar;
        private String passport_num;
        private String real_name;
        private String gender;
        private String id_number;
        private String expire_time;
        private String type_name;

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getPassport_num() {
            return passport_num;
        }

        public void setPassport_num(String passport_num) {
            this.passport_num = passport_num;
        }

        public String getReal_name() {
            return real_name;
        }

        public void setReal_name(String real_name) {
            this.real_name = real_name;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getId_number() {
            return id_number;
        }

        public void setId_number(String id_number) {
            this.id_number = id_number;
        }

        public String getExpire_time() {
            return expire_time;
        }

        public void setExpire_time(String expire_time) {
            this.expire_time = expire_time;
        }

        public String getType_name() {
            return type_name;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }
    }
}
