package com.webdrp.entity.address;

import java.util.List;

public class AddressDto {

    /**
     * name : 河北省
     * code : 130000
     * sub : [{"name":"石家庄市","code":"130100","sub":[{"name":"市辖区","code":"130101"},{"name":"长安区","code":"130102"},{"name":"桥西区","code":"130104"},{"name":"新华区","code":"130105"},{"name":"井陉矿区","code":"130107"},{"name":"裕华区","code":"130108"},{"name":"藁城区","code":"130109"},{"name":"鹿泉区","code":"130110"},{"name":"栾城区","code":"130111"},{"name":"井陉县","code":"130121"},{"name":"正定县","code":"130123"},{"name":"行唐县","code":"130125"},{"name":"灵寿县","code":"130126"},{"name":"高邑县","code":"130127"},{"name":"深泽县","code":"130128"},{"name":"赞皇县","code":"130129"},{"name":"无极县","code":"130130"},{"name":"平山县","code":"130131"},{"name":"元氏县","code":"130132"},{"name":"赵县","code":"130133"},{"name":"辛集市","code":"130181"},{"name":"晋州市","code":"130183"},{"name":"新乐市","code":"130184"}]},{"name":"唐山市","code":"130200","sub":[{"name":"市辖区","code":"130201"},{"name":"路南区","code":"130202"},{"name":"路北区","code":"130203"},{"name":"古冶区","code":"130204"},{"name":"开平区","code":"130205"},{"name":"丰南区","code":"130207"},{"name":"丰润区","code":"130208"},{"name":"曹妃甸区","code":"130209"},{"name":"滦县","code":"130223"},{"name":"滦南县","code":"130224"},{"name":"乐亭县","code":"130225"},{"name":"迁西县","code":"130227"},{"name":"玉田县","code":"130229"},{"name":"遵化市","code":"130281"},{"name":"迁安市","code":"130283"}]},{"name":"秦皇岛市","code":"130300","sub":[{"name":"市辖区","code":"130301"},{"name":"海港区","code":"130302"},{"name":"山海关区","code":"130303"},{"name":"北戴河区","code":"130304"},{"name":"青龙满族自治县","code":"130321"},{"name":"昌黎县","code":"130322"},{"name":"抚宁县","code":"130323"},{"name":"卢龙县","code":"130324"}]},{"name":"邯郸市","code":"130400","sub":[{"name":"市辖区","code":"130401"},{"name":"邯山区","code":"130402"},{"name":"丛台区","code":"130403"},{"name":"复兴区","code":"130404"},{"name":"峰峰矿区","code":"130406"},{"name":"邯郸县","code":"130421"},{"name":"临漳县","code":"130423"},{"name":"成安县","code":"130424"},{"name":"大名县","code":"130425"},{"name":"涉县","code":"130426"},{"name":"磁县","code":"130427"},{"name":"肥乡县","code":"130428"},{"name":"永年县","code":"130429"},{"name":"邱县","code":"130430"},{"name":"鸡泽县","code":"130431"},{"name":"广平县","code":"130432"},{"name":"馆陶县","code":"130433"},{"name":"魏县","code":"130434"},{"name":"曲周县","code":"130435"},{"name":"武安市","code":"130481"}]},{"name":"邢台市","code":"130500","sub":[{"name":"市辖区","code":"130501"},{"name":"桥东区","code":"130502"},{"name":"桥西区","code":"130503"},{"name":"邢台县","code":"130521"},{"name":"临城县","code":"130522"},{"name":"内丘县","code":"130523"},{"name":"柏乡县","code":"130524"},{"name":"隆尧县","code":"130525"},{"name":"任县","code":"130526"},{"name":"南和县","code":"130527"},{"name":"宁晋县","code":"130528"},{"name":"巨鹿县","code":"130529"},{"name":"新河县","code":"130530"},{"name":"广宗县","code":"130531"},{"name":"平乡县","code":"130532"},{"name":"威县","code":"130533"},{"name":"清河县","code":"130534"},{"name":"临西县","code":"130535"},{"name":"南宫市","code":"130581"},{"name":"沙河市","code":"130582"}]},{"name":"保定市","code":"130600","sub":[{"name":"市辖区","code":"130601"},{"name":"新市区","code":"130602"},{"name":"北市区","code":"130603"},{"name":"南市区","code":"130604"},{"name":"满城县","code":"130621"},{"name":"清苑县","code":"130622"},{"name":"涞水县","code":"130623"},{"name":"阜平县","code":"130624"},{"name":"徐水县","code":"130625"},{"name":"定兴县","code":"130626"},{"name":"唐县","code":"130627"},{"name":"高阳县","code":"130628"},{"name":"容城县","code":"130629"},{"name":"涞源县","code":"130630"},{"name":"望都县","code":"130631"},{"name":"安新县","code":"130632"},{"name":"易县","code":"130633"},{"name":"曲阳县","code":"130634"},{"name":"蠡县","code":"130635"},{"name":"顺平县","code":"130636"},{"name":"博野县","code":"130637"},{"name":"雄县","code":"130638"},{"name":"涿州市","code":"130681"},{"name":"定州市","code":"130682"},{"name":"安国市","code":"130683"},{"name":"高碑店市","code":"130684"}]},{"name":"张家口市","code":"130700","sub":[{"name":"市辖区","code":"130701"},{"name":"桥东区","code":"130702"},{"name":"桥西区","code":"130703"},{"name":"宣化区","code":"130705"},{"name":"下花园区","code":"130706"},{"name":"宣化县","code":"130721"},{"name":"张北县","code":"130722"},{"name":"康保县","code":"130723"},{"name":"沽源县","code":"130724"},{"name":"尚义县","code":"130725"},{"name":"蔚县","code":"130726"},{"name":"阳原县","code":"130727"},{"name":"怀安县","code":"130728"},{"name":"万全县","code":"130729"},{"name":"怀来县","code":"130730"},{"name":"涿鹿县","code":"130731"},{"name":"赤城县","code":"130732"},{"name":"崇礼县","code":"130733"}]},{"name":"承德市","code":"130800","sub":[{"name":"市辖区","code":"130801"},{"name":"双桥区","code":"130802"},{"name":"双滦区","code":"130803"},{"name":"鹰手营子矿区","code":"130804"},{"name":"承德县","code":"130821"},{"name":"兴隆县","code":"130822"},{"name":"平泉县","code":"130823"},{"name":"滦平县","code":"130824"},{"name":"隆化县","code":"130825"},{"name":"丰宁满族自治县","code":"130826"},{"name":"宽城满族自治县","code":"130827"},{"name":"围场满族蒙古族自治县","code":"130828"}]},{"name":"沧州市","code":"130900","sub":[{"name":"市辖区","code":"130901"},{"name":"新华区","code":"130902"},{"name":"运河区","code":"130903"},{"name":"沧县","code":"130921"},{"name":"青县","code":"130922"},{"name":"东光县","code":"130923"},{"name":"海兴县","code":"130924"},{"name":"盐山县","code":"130925"},{"name":"肃宁县","code":"130926"},{"name":"南皮县","code":"130927"},{"name":"吴桥县","code":"130928"},{"name":"献县","code":"130929"},{"name":"孟村回族自治县","code":"130930"},{"name":"泊头市","code":"130981"},{"name":"任丘市","code":"130982"},{"name":"黄骅市","code":"130983"},{"name":"河间市","code":"130984"}]},{"name":"廊坊市","code":"131000","sub":[{"name":"市辖区","code":"131001"},{"name":"安次区","code":"131002"},{"name":"广阳区","code":"131003"},{"name":"固安县","code":"131022"},{"name":"永清县","code":"131023"},{"name":"香河县","code":"131024"},{"name":"大城县","code":"131025"},{"name":"文安县","code":"131026"},{"name":"大厂回族自治县","code":"131028"},{"name":"霸州市","code":"131081"},{"name":"三河市","code":"131082"}]},{"name":"衡水市","code":"131100","sub":[{"name":"市辖区","code":"131101"},{"name":"桃城区","code":"131102"},{"name":"枣强县","code":"131121"},{"name":"武邑县","code":"131122"},{"name":"武强县","code":"131123"},{"name":"饶阳县","code":"131124"},{"name":"安平县","code":"131125"},{"name":"故城县","code":"131126"},{"name":"景县","code":"131127"},{"name":"阜城县","code":"131128"},{"name":"冀州市","code":"131181"},{"name":"深州市","code":"131182"}]}]
     */

    private String name;
    private String code;
    private List<SubBeanX> sub;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<SubBeanX> getSub() {
        return sub;
    }

    public void setSub(List<SubBeanX> sub) {
        this.sub = sub;
    }

    public static class SubBeanX {
        /**
         * name : 石家庄市
         * code : 130100
         * sub : [{"name":"市辖区","code":"130101"},{"name":"长安区","code":"130102"},{"name":"桥西区","code":"130104"},{"name":"新华区","code":"130105"},{"name":"井陉矿区","code":"130107"},{"name":"裕华区","code":"130108"},{"name":"藁城区","code":"130109"},{"name":"鹿泉区","code":"130110"},{"name":"栾城区","code":"130111"},{"name":"井陉县","code":"130121"},{"name":"正定县","code":"130123"},{"name":"行唐县","code":"130125"},{"name":"灵寿县","code":"130126"},{"name":"高邑县","code":"130127"},{"name":"深泽县","code":"130128"},{"name":"赞皇县","code":"130129"},{"name":"无极县","code":"130130"},{"name":"平山县","code":"130131"},{"name":"元氏县","code":"130132"},{"name":"赵县","code":"130133"},{"name":"辛集市","code":"130181"},{"name":"晋州市","code":"130183"},{"name":"新乐市","code":"130184"}]
         */

        private String name;
        private String code;
        private List<SubBean> sub;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public List<SubBean> getSub() {
            return sub;
        }

        public void setSub(List<SubBean> sub) {
            this.sub = sub;
        }

        public static class SubBean {
            /**
             * name : 市辖区
             * code : 130101
             */

            private String name;
            private String code;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }
        }
    }
}
