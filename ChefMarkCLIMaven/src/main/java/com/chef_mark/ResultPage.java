package com.chef_mark;

public class ResultPage {
    private String pageResult;
    private String nextPage;

    public ResultPage(String pr, String np){
        this.pageResult = pr;
        this.nextPage = np;
    }

    public String getPageResult(){
        return this.pageResult;
    }

    public String getNextPage(){
        return this.nextPage;
    }
}
