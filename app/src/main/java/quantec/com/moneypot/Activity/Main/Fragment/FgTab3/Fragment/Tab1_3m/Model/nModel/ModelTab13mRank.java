package quantec.com.moneypot.Activity.Main.Fragment.FgTab3.Fragment.Tab1_3m.Model.nModel;

import java.util.ArrayList;

public class ModelTab13mRank {

    int errorcode;
    int totalElements;
    ArrayList<Content> content = new ArrayList<>();
    Page page = new Page();

    public int getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(int errorcode) {
        this.errorcode = errorcode;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public ArrayList<Content> getContent() {
        return content;
    }

    public void setContent(ArrayList<Content> content) {
        this.content = content;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }


    public class Content {
        String beginDate;
        long minCost;
        String name;
        String stCode;
        String descript;
        double rate30;
        double rate90;
        double rate180;
        double rate;
        Select select = new Select();

        public String getBeginDate() {
            return beginDate;
        }

        public void setBeginDate(String beginDate) {
            this.beginDate = beginDate;
        }

        public long getMinCost() {
            return minCost;
        }

        public void setMinCost(long minCost) {
            this.minCost = minCost;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getStCode() {
            return stCode;
        }

        public void setStCode(String stCode) {
            this.stCode = stCode;
        }

        public String getDescript() {
            return descript;
        }

        public void setDescript(String descript) {
            this.descript = descript;
        }

        public double getRate30() {
            return rate30;
        }

        public void setRate30(double rate30) {
            this.rate30 = rate30;
        }

        public double getRate90() {
            return rate90;
        }

        public void setRate90(double rate90) {
            this.rate90 = rate90;
        }

        public double getRate180() {
            return rate180;
        }

        public void setRate180(double rate180) {
            this.rate180 = rate180;
        }

        public double getRate() {
            return rate;
        }

        public void setRate(double rate) {
            this.rate = rate;
        }

        public Select getSelect() {
            return select;
        }

        public void setSelect(Select select) {
            this.select = select;
        }

        public class Select {
            int type;
            boolean isZim;
            boolean isDam;
            String code;
            String name;
            String descript;
            int minCost;
            double rate;

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public boolean isZim() {
                return isZim;
            }

            public void setZim(boolean zim) {
                isZim = zim;
            }

            public boolean isDam() {
                return isDam;
            }

            public void setDam(boolean dam) {
                isDam = dam;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getDescript() {
                return descript;
            }

            public void setDescript(String descript) {
                this.descript = descript;
            }

            public int getMinCost() {
                return minCost;
            }

            public void setMinCost(int minCost) {
                this.minCost = minCost;
            }

            public double getRate() {
                return rate;
            }

            public void setRate(double rate) {
                this.rate = rate;
            }
        }
    }


    static public class Page {
        int pageNumber;
        boolean firstPage;
        boolean lastPage;
        int totalPages;
        int pageSize;
        Sort sort = new Sort();
        int totalElements;

        public int getPageNumber() {
            return pageNumber;
        }

        public void setPageNumber(int pageNumber) {
            this.pageNumber = pageNumber;
        }

        public boolean isFirstPage() {
            return firstPage;
        }

        public void setFirstPage(boolean firstPage) {
            this.firstPage = firstPage;
        }

        public boolean isLastPage() {
            return lastPage;
        }

        public void setLastPage(boolean lastPage) {
            this.lastPage = lastPage;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public Sort getSort() {
            return sort;
        }

        public void setSort(Sort sort) {
            this.sort = sort;
        }

        public int getTotalElements() {
            return totalElements;
        }

        public void setTotalElements(int totalElements) {
            this.totalElements = totalElements;
        }
    }

    static public class Sort {
        boolean sorted;
        boolean unsorted;
        boolean empty;

        public boolean isSorted() {
            return sorted;
        }

        public void setSorted(boolean sorted) {
            this.sorted = sorted;
        }

        public boolean isUnsorted() {
            return unsorted;
        }

        public void setUnsorted(boolean unsorted) {
            this.unsorted = unsorted;
        }

        public boolean isEmpty() {
            return empty;
        }

        public void setEmpty(boolean empty) {
            this.empty = empty;
        }
    }

}
