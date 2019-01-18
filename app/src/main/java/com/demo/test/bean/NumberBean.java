package com.demo.test.bean;

import com.guo.wheelview.IPickerViewData.IPickerViewData;

public class NumberBean implements IPickerViewData {

    int number;

    public NumberBean(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String getPickerViewText() {
        return String.format("%02d",number);
    }
}
