package com.csma.redisinaction.ch07.common;

import java.util.List;

/**
 * 简单的分页组件
 * Created by csma on 5/30/16.
 */
public class PageableResult<T> {
    
    private List<T> itemList;

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    private Long total = 0L;
    private Integer pageSize = PAGE_SIZE;
    private Integer currentPage = 0;

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    private Integer totalPage = 0;
    

    public String getResultKey() {
        return resultKey;
    }

    public void setResultKey(String resultKey) {
        this.resultKey = resultKey;
    }

    public List<T> getItemList() {
        return itemList;
    }

    public void setItemList(List<T> itemList) {
        this.itemList = itemList;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    private String resultKey;

    public PageableResult(List<T> itemList, Long total){
        this.itemList = itemList;
        this.total = total;

        init();
    }

    private void init() {
        //计算各个项的数据
        totalPage = (int)(total / pageSize);

        if(total % pageSize != 0){
            totalPage = totalPage + 1;
        }
    }

    public Integer getNext(){
        return currentPage.compareTo(totalPage) < 0 ? currentPage + 1 : totalPage;
    }

    public Integer getPrev(){
        return currentPage.compareTo(1) > 0 ? currentPage - 1 : 1;
    }

    public static final Integer PAGE_SIZE = 20;
}
