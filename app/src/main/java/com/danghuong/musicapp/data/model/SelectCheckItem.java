package com.danghuong.musicapp.data.model;

public class SelectCheckItem {
    private boolean isCheck;
    private SelectItem selectItem;

    public SelectCheckItem(boolean isCheck, SelectItem selectItem) {
        this.isCheck = isCheck;
        this.selectItem = selectItem;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public SelectItem getSelectItem() {
        return selectItem;
    }

    public void setSelectItem(SelectItem selectItem) {
        this.selectItem = selectItem;
    }
}
