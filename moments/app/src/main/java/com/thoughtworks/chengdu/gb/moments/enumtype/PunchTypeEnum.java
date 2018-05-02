package com.thoughtworks.chengdu.gb.moments.enumtype;

/**
 * Created by GB on 2017/11/28.
 */

public class PunchTypeEnum {
    public static enum  SettingTypeEnum{
        //调用构造函数来构造枚举项
        SettingTypeEnumDeviceId(0),
        SettingTypeEnumEmployeeCode(1),
        SettingTypeEnumProjectId(2),
        SettingTypeEnumToken(3),
        SettingTypeEnumPsw(4);

        private SettingTypeEnum(int value){
            this.value=value;
        }
        private int value;

        public int getValue() {
            return value;
        }

        //int到enum的转换函数
        public static SettingTypeEnum settingTypeEnumFromValue(int value){
            switch (value){
                case 0:
                    return SettingTypeEnumDeviceId;
                case 1:
                    return SettingTypeEnumEmployeeCode;
                case 2:
                    return SettingTypeEnumProjectId;
                case 3:
                    return SettingTypeEnumToken;
                case 4:
                    return SettingTypeEnumPsw;
                default:
                    return SettingTypeEnumDeviceId;
            }
        }
    }
}
