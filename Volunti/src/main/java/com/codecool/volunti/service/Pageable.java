package com.codecool.volunti.service;

import java.util.List;

public class Pageable<T> {

    private static final int PAGE_WINDOW = 10;

    /** the list over which this class is paging */
    private List<T> list;

    /** the page size */
    private int pageSize ;

    /** the current page */
    private int page;

    /** the starting index */
    private int startingIndex;

    /** the ending index */
    private int endingIndex;

    /** the maximum number of pages */
    private int maxPages;

    /**
     * Creates a new instance with the specified list.
     *
     * @param list    a List
     */
    public Pageable(List<T> list, int page, int pageSize) {
        this.list = list;
        this.page = page;
        this.maxPages = 1;
        this.pageSize = pageSize;

        calculatePages();
        setPage(page);
    }

    private void calculatePages() {
        if (pageSize > 0) {
            // calculate how many pages there are
            if (list.size() % pageSize == 0) {
                maxPages = list.size() / pageSize;
            } else {
                maxPages = (list.size() / pageSize) + 1;
            }
        }
    }

    /**
     * Gets the list that this instance is paging over.
     *
     * @return  a List
     */
    public List<T> getList() {
        return this.list;
    }

    /**
     * Gets the subset of the list for the current page.
     *
     * @return  a List
     */
    public List<T> getListForPage() {
        return list.subList(startingIndex, endingIndex);
    }

    /**
     * Gets the page size.
     *
     * @return  the page size as an int
     */
    public int getPageSize() {
        return this.pageSize;
    }

    /**
     * Sets the page size.
     *
     * @param pageSize   the page size as an int
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
        calculatePages();
    }

    /**
     * Gets the page.
     *
     * @return  the page as an int
     */
    public int getPage() {
        return this.page;
    }

    /**
     * Sets the page size.
     *
     * @param p    the page as an int
     */
    public void setPage(int p) {
        if (p >= maxPages) {
            this.page = maxPages;
        } else if (p <= 1) {
            this.page = 1;
        } else {
            this.page = p;
        }

        // now work out where the sub-list should start and end
        startingIndex = pageSize * (page-1);
        if (startingIndex < 0) {
            startingIndex = 0;
        }
        endingIndex = startingIndex + pageSize;
        if (endingIndex > list.size()) {
            endingIndex = list.size();
        }
    }

    /**
     * Gets the maximum number of pages.
     *
     * @return  the maximum number of pages as an int
     */
    public int getMaxPages() {
        return this.maxPages;
    }

    public int getStartingIndex() {
        return startingIndex;
    }


}