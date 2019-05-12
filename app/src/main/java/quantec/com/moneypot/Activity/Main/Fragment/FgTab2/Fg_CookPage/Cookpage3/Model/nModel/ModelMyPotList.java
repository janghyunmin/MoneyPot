package quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.Cookpage3.Model.nModel;

import java.util.ArrayList;

public class ModelMyPotList {

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

        int type;
        int investType;
        String code;
        String name;
        String descript;
        Long minPrice;
        int elNum;
        double rate;
        double rateOne;
        double rateThr;
        double rateSix;
        int active;
        int view;
        int status;
        String date;
        PackEls packEls;
        Codes codes;
        File File = new File();
        Select select;
        int uid;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getInvestType() {
            return investType;
        }

        public void setInvestType(int investType) {
            this.investType = investType;
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

        public Long getMinPrice() {
            return minPrice;
        }

        public void setMinPrice(Long minPrice) {
            this.minPrice = minPrice;
        }

        public int getElNum() {
            return elNum;
        }

        public void setElNum(int elNum) {
            this.elNum = elNum;
        }

        public double getRate() {
            return rate;
        }

        public void setRate(double rate) {
            this.rate = rate;
        }

        public double getRateOne() {
            return rateOne;
        }

        public void setRateOne(double rateOne) {
            this.rateOne = rateOne;
        }

        public double getRateThr() {
            return rateThr;
        }

        public void setRateThr(double rateThr) {
            this.rateThr = rateThr;
        }

        public double getRateSix() {
            return rateSix;
        }

        public void setRateSix(double rateSix) {
            this.rateSix = rateSix;
        }

        public int getActive() {
            return active;
        }

        public void setActive(int active) {
            this.active = active;
        }

        public int getView() {
            return view;
        }

        public void setView(int view) {
            this.view = view;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public PackEls getPackEls() {
            return packEls;
        }

        public void setPackEls(PackEls packEls) {
            this.packEls = packEls;
        }

        public Codes getCodes() {
            return codes;
        }

        public void setCodes(Codes codes) {
            this.codes = codes;
        }

        public Content.File getFile() {
            return File;
        }

        public void setFile(Content.File file) {
            File = file;
        }

        public Select getSelect() {
            return select;
        }

        public void setSelect(Select select) {
            this.select = select;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public class PackEls{
        }
        public class Codes{
        }
        public class Select{
        }
        public class File {
            String type;
            String code;
            String fileName;
            int fileSize;
            String oldFileName;
            int oldFileSize;
            String dailyFolderPath;
            boolean dailyFolder;
            String home;
            int uid;
            String fileFullPath;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getFileName() {
                return fileName;
            }

            public void setFileName(String fileName) {
                this.fileName = fileName;
            }

            public int getFileSize() {
                return fileSize;
            }

            public void setFileSize(int fileSize) {
                this.fileSize = fileSize;
            }

            public String getOldFileName() {
                return oldFileName;
            }

            public void setOldFileName(String oldFileName) {
                this.oldFileName = oldFileName;
            }

            public int getOldFileSize() {
                return oldFileSize;
            }

            public void setOldFileSize(int oldFileSize) {
                this.oldFileSize = oldFileSize;
            }

            public String getDailyFolderPath() {
                return dailyFolderPath;
            }

            public void setDailyFolderPath(String dailyFolderPath) {
                this.dailyFolderPath = dailyFolderPath;
            }

            public boolean isDailyFolder() {
                return dailyFolder;
            }

            public void setDailyFolder(boolean dailyFolder) {
                this.dailyFolder = dailyFolder;
            }

            public String getHome() {
                return home;
            }

            public void setHome(String home) {
                this.home = home;
            }

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public String getFileFullPath() {
                return fileFullPath;
            }

            public void setFileFullPath(String fileFullPath) {
                this.fileFullPath = fileFullPath;
            }
        }
    }

    public class Page {
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

        public class Sort {
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

}
