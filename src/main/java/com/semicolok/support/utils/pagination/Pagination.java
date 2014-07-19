package com.semicolok.support.utils.pagination;

import com.google.common.collect.Lists;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

public class Pagination<T> implements Serializable {
    private static final long serialVersionUID = 679213922269552054L;

    private final List<T> content = Lists.newArrayList();
    private final int pageNumber;
    private final int pageSize;
    private final Sort sort;
    private final long total;

    private long blockSize = 10;
    private long currentPageNumber;
    private long beginPageNumber;
    private long endPageNumber;

    public Pagination(List<T> content, int pageNumber, int pageSize, Sort sort, long total) {
        if (null == content) {
            throw new IllegalArgumentException("system.exception.page-content.must.not-null");
        }

        this.content.addAll(content);
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.sort = sort;
        this.total = total;

        this.currentPageNumber = pageNumber;
        this.endPageNumber = (long) (Math.ceil(currentPageNumber / new Double(blockSize)) * blockSize);
        this.endPageNumber = endPageNumber > getTotalPages() ? getTotalPages() : endPageNumber;
    }

    public int getTotalPages() {
        return getPageSize() == 0 ? 0 : (int) Math.ceil((double) total / (double) getPageSize());
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public int getPageNumber() {
        return this.pageNumber;
    }

    public int getContentSize() {
        return null != content ? content.size() : 0;
    }

    public boolean hasContent() {
        return !content.isEmpty();
    }

    public List<T> getContent() {
        return content;
    }

    public Sort getSort() {
        return sort;
    }

    public long getTotal() {
        return total;
    }

    public long getBlockSize() {
        return blockSize;
    }

    public long getCurrentPageNumber() {
        return currentPageNumber;
    }

    public long getBeginPageNumber() {
        return beginPageNumber;
    }

    public long getEndPageNumber() {
        return endPageNumber;
    }

    public boolean isFirstBlock() {
        return beginPageNumber == 0;
    }

    public boolean isPrevBlock() {
        return beginPageNumber > blockSize;
    }

    public boolean isNextBlock() {
        return getTotalPages() > endPageNumber;
    }

    public Iterator<T> iterator() {
        return content.iterator();
    }

    @Override
    public String toString() {
        return "Pagination [pageNumber=" + pageNumber + ", pageSize=" + pageSize + ", sort=" + sort + ", total="
                + total + ", blockSize=" + blockSize + ", currentPageNumber=" + currentPageNumber
                + ", beginPageNumber=" + beginPageNumber + ", endPageNumber=" + endPageNumber + "]";
    }
}
