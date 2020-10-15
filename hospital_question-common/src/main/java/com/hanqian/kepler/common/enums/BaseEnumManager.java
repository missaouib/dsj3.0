package com.hanqian.kepler.common.enums;

/**
 * 枚举
 * ============================================================================
 * 版权所有 2016 。
 *
 * @author fallenpanda
 * @version 1.0 2016-07-13 。
 * ============================================================================
 */
public class BaseEnumManager {

    /**
     * 文档操作
     */
    public static enum MethodEnum {

        /**
         * 保存
         */
        Save("保存"),

        /**
         * 修改
         */
        Update("修改"),

        /**
         * 删除
         */
        Delete("删除"),

        /**
         * 启用
         */
        Enable("启用"),

        /**
         * 停用
         */
        Disenable("停用");

        private final String value;

        private MethodEnum(final String value) {
            this.value = value;
        }
        public String value() {
            return this.value;
        }

    }

    /**
     * 文档状态
     */
    public static enum StateEnum {

        /**
         * 删除
         */
        Delete("删除"),

        /**
         * 启用
         */
        Enable("启用"),

        /**
         * 停用
         */
        Disenable("停用"),

        /**
         * 历史
         */
        History("历史");

        private final String value;

        private StateEnum(final String value) {
            this.value = value;
        }
        public String value() {
            return this.value;
        }
    }

    /**
     * 性别
     */
    public static enum SexEnum {

        /**
         * 男
         */
        male("男"),

        /**
         * 女
         */
        female("女");

        private final String value;

        private SexEnum(final String value) {
            this.value = value;
        }
        public String value() {
            return this.value;
        }

    }

    /**
     * 调查对象
     */
    public enum ObjectTypeEnum{
        Patient("患者", 1),
        PatientFamily("患者家属", 2),

        Doctor("医生", 3),
        Nurse("护士", 4),
//        MedicalTechnician("医技人员", 5),
//        Management("管理人员", 6),
        Other("其他", 7);

        private final String value;
        private final int key;

        ObjectTypeEnum(String value, int key) {
            this.value = value;
            this.key = key;
        }
        public String value() {
            return this.value;
        }
        public int key() {
            return this.key;
        }
    }

    /**
     * 医院名
     */
    public enum HospitalName{
        JZ("精神卫生中心"),
        SY("第十人民医院"),
        DYRM("第一人民医院"),
        YY("岳阳医院"),
        LH("龙华医院"),
        XK("胸科医院"),
        XH("新华医院"),
        LY("第六人民医院"),
        FK("肺科医院"),

        //第二批
        DYRMYYBY("第一人民医院北院"),
        DYRMYYNY("第一人民医院南院"),
        SGYYDY("曙光医院东院"),
        SGYYXY("曙光医院西院"),
        ZYYY("上海市中医医院"),
        HDYY("华东医院"),
        JSWSZXXH("精神卫生中心徐汇"),
        JSWSZXMH("精神卫生中心闵行"),
        GWZX("公卫中心"),
        RJYYDY("仁济医院东院"),
        RJYYXY("仁济医院西院"),
        RJYYBY("仁济医院北院"),
        TJYY("同济医院"),
        RJYYZY("瑞金医院总院"),
        ETEXZX("儿童医学中心"),
        ETYYBJXL("儿童医院北京西路"),
        DYFYBJYDY("第一妇婴保健院东院"),
        JYN("第九人民医院南院");

        private final String value;

        HospitalName(String value) {
            this.value = value;
        }

        public String value() {
            return value;
        }
    }









    /**
     * 账号类型
     */
    public enum AccountTypeEnum{

        Member("企业用户",1),
        CompanyManager("企业管理员",2),
        SystemManager("系统管理员",3);

        private final String value;
        private final int key;

        AccountTypeEnum(final String value, final int key) {
            this.value = value;
            this.key = key;
        }
        public String value() {
            return this.value;
        }
        public int key() {
            return this.key;
        }

    }

}
