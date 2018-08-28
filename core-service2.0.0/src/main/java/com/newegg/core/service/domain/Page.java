package com.newegg.core.service.domain;

public class Page {
    private int pageNow;
    private int pageTotal;
    private int dataTotal;

    public Page(int pageNow, int pageTotal, int dataTotal) {
        this.pageNow = pageNow;
        this.pageTotal = pageTotal;
        this.dataTotal = dataTotal;
    }

    public Page() {
    }

    public int getPageNow() {
        return pageNow;
    }

    public void setPageNow(int pageNow) {
        this.pageNow = pageNow;
    }

    public int getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(int pageTotal) {
        this.pageTotal = pageTotal;
    }

    public int getDataTotal() {
        return dataTotal;
    }

    public void setDataTotal(int dataTotal) {
        this.dataTotal = dataTotal;
    }
}
