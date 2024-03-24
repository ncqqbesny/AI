package com.soft.mapp.basecenter.handler;

import java.io.Serializable;
import java.util.List;

public class PageModel<T>
        implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int page;
    private int pageSize;
    private int totalPages;
    private long totalCounts;
    private int size;
    private List<T> content;
    private boolean first;
    private boolean last;

    public PageModel() {}

    public int getPage()
    {
        return this.page;
    }

    public void setPage(int newpage)
    {
        this.page = newpage;
    }

    public int getPageSize()
    {
        return this.pageSize;
    }

    public void setPageSize(int newpageSize)
    {
        this.pageSize = newpageSize;
    }

    public List<T> getContent()
    {
        return this.content;
    }

    public void setContent(List<T> newcontent)
    {
        this.content = newcontent;
    }

    public int getTotalPages()
    {
        return this.totalPages;
    }

    public void setTotalPages(int newtotalPages)
    {
        this.totalPages = newtotalPages;
    }

    public long getTotalCounts()
    {
        return this.totalCounts;
    }

    public void setTotalCounts(long newtotalCounts)
    {
        this.totalCounts = newtotalCounts;
    }

    public int getSize()
    {
        return this.size;
    }

    public void setSize(int newsize)
    {
        this.size = newsize;
    }

    public boolean isFirst()
    {
        return this.first;
    }

    public void setFirst(boolean newfirst)
    {
        this.first = newfirst;
    }

    public boolean isLast()
    {
        return this.last;
    }

    public void setLast(boolean newlast)
    {
        this.last = newlast;
    }
}
