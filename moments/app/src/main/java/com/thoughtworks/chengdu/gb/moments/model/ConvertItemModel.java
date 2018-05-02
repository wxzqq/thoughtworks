package com.thoughtworks.chengdu.gb.moments.model;

/**
 * Created by GB on 2018/04/22.
 */
public class ConvertItemModel {
    private String itemTitle;//标题
    private String itemDesc;//描述
    private boolean showArrow;//是否显示箭头
    private ConvertType convertType;//转换类型

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public boolean isShowArrow() {
        return showArrow;
    }

    public void setShowArrow(boolean showArrow) {
        this.showArrow = showArrow;
    }

    public ConvertType getConvertType() {
        return convertType;
    }

    public void setConvertType(ConvertType convertType) {
        this.convertType = convertType;
    }

    public enum ConvertType {
        //转换符号
        ConvertTypeSymbols("ConvertTypeSymbols"),
        //转换数字
        ConvertTypeNumbers("ConvertTypeNumbers");

        private String type;

        ConvertType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }
}
