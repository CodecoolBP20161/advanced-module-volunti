package com.codecool.volunti.service;


import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class PagableTest {

    private Pageable pageable;
    private List<String> sample;

    @Before
    public void setUp() {
        sample = Arrays.asList("aaa", "bbb", "ccc", "ddd", "fds",
                "xcv","ert","qqq", "nnn", "xvc");
    }

    @Test
    public void testForGetters() {
        pageable = new Pageable<String>(sample, 1,2);

        assertEquals(pageable.getPage(), 1);
        assertEquals(pageable.getList().size(), 10);
        assertEquals(pageable.getListForPage(), Arrays.asList("aaa", "bbb"));
        assertEquals(pageable.getPageSize(), 2);
    }

    @Test
    public void calculatePagesIfPageSizeIsLessThanZeroTest() {
        pageable = new Pageable<String>(sample, 1,-2);
        assertEquals(pageable.getMaxPages(), 1);
    }

    @Test
    public void calculatePagesIfPageSizeIsEven() {
        pageable = new Pageable<String>(sample, 1,2);
        assertEquals(pageable.getMaxPages(), 5);
    }

    @Test
    public void calculatePagesIfPageSizeIsOdd() {
        pageable = new Pageable<String>(sample, 1,3);
        assertEquals(pageable.getMaxPages(), 4);
    }

    @Test
    public void setPageTest() {
        pageable = new Pageable<String>(sample, 1,3);

        pageable.setPage(pageable.getPage());
        assertEquals(pageable.getStartingIndex(), 0);
        assertEquals(pageable.getMaxPages(),4);
    }

    @Test
    public void setPageableTestValueIsGreaterThanMaxPage(){
        pageable = new Pageable<String>(sample, 4,3);
        assertEquals(pageable.getMaxPages(), 4);
    }

    @Test
    public void setPageableTestValueIsLessThanMaxPage(){
        pageable = new Pageable<String>(sample, 2,3);
        assertEquals(pageable.getMaxPages(), 4);

    }

}
