package chefmark;

public class ResultPage {
    private String pageResult;
    private String nextPage;

    /**
     * constructor for result page
     * @param pr
     * @param np
     */
    public ResultPage(String pr, String np){
        this.pageResult = pr;
        this.nextPage = np;
    }

    /**
     * 
     * @return
     */
    public String getPageResult(){
        return this.pageResult;
    }

    /**
     * 
     * @return
     */
    public String getNextPage(){
        return this.nextPage;
    }
}
