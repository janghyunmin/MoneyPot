package quantec.com.moneypot.Activity.Main.Fragment.FgTab3.Fragment.Tab1_3m.Model.nModel;

import java.util.ArrayList;

public class ModelTab13mRank {

    int status;
    long timestamp;
    int totalElements;
    boolean noContent;
    ArrayList<Content> content = new ArrayList<>();
    Page page = new Page();

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public boolean isNoContent() {
        return noContent;
    }

    public void setNoContent(boolean noContent) {
        this.noContent = noContent;
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
        ArrayList<PotEls> potEls;
        Codes codes;
        //        Filter filter;
        File file;
        Select select = new Select();
        int uid;

        public class PotEls{
            int code;
            int elCode;
            String elName;
            double rate;
            String beginDate;
            String endDate;
            double weight;

            public int getCode() {
                return code;
            }

            public void setCode(int code) {
                this.code = code;
            }

            public int getElCode() {
                return elCode;
            }

            public void setElCode(int elCode) {
                this.elCode = elCode;
            }

            public String getElName() {
                return elName;
            }

            public void setElName(String elName) {
                this.elName = elName;
            }

            public double getRate() {
                return rate;
            }

            public void setRate(double rate) {
                this.rate = rate;
            }

            public String getBeginDate() {
                return beginDate;
            }

            public void setBeginDate(String beginDate) {
                this.beginDate = beginDate;
            }

            public String getEndDate() {
                return endDate;
            }

            public void setEndDate(String endDate) {
                this.endDate = endDate;
            }

            public double getWeight() {
                return weight;
            }

            public void setWeight(double weight) {
                this.weight = weight;
            }
        }
        public class Codes{
        }
        public class File{

            String code;
            String fileName;
            int fileSize;
            String oldFileName;
            int oldFileSize;
            String dailyFolderPath;
            boolean dailyFolder;
            String home;
            String fileFullPath;

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

            public String getFileFullPath() {
                return fileFullPath;
            }

            public void setFileFullPath(String fileFullPath) {
                this.fileFullPath = fileFullPath;
            }
        }

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

        public ArrayList<PotEls> getPotEls() {
            return potEls;
        }

        public void setPotEls(ArrayList<PotEls> potEls) {
            this.potEls = potEls;
        }

        public Codes getCodes() {
            return codes;
        }

        public void setCodes(Codes codes) {
            this.codes = codes;
        }

        public File getFile() {
            return file;
        }

        public void setFile(File file) {
            this.file = file;
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

        public class Select {
            int type;
            boolean isZim;
            boolean isDam;
            String code;
            String name;
            String descript;
            int minPrice;
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

            public int getMinPrice() {
                return minPrice;
            }

            public void setMinPrice(int minPrice) {
                this.minPrice = minPrice;
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



//    int errorcode;
//    int totalElements;
//    boolean noContent;
//    ArrayList<Content> content = new ArrayList<>();
//    Page page = new Page();
//
//    public int getErrorcode() {
//        return errorcode;
//    }
//
//    public void setErrorcode(int errorcode) {
//        this.errorcode = errorcode;
//    }
//
//    public int getTotalElements() {
//        return totalElements;
//    }
//
//    public void setTotalElements(int totalElements) {
//        this.totalElements = totalElements;
//    }
//
//    public boolean isNoContent() {
//        return noContent;
//    }
//
//    public void setNoContent(boolean noContent) {
//        this.noContent = noContent;
//    }
//
//    public ArrayList<Content> getContent() {
//        return content;
//    }
//
//    public void setContent(ArrayList<Content> content) {
//        this.content = content;
//    }
//
//    public Page getPage() {
//        return page;
//    }
//
//    public void setPage(Page page) {
//        this.page = page;
//    }
//
//    public class Content {
//        int type;
//        int investType;
//        String code;
//        String name;
//        String descript;
//        Long minPrice;
//        int elNum;
//        double rate;
//        double rateOne;
//        double rateThr;
//        double rateSix;
//        int active;
//        int view;
//        int status;
//        String date;
//        ArrayList<PotEls> potEls;
//        Codes codes;
////        Filter filter;
//        File file;
//        Select select = new Select();
//        int uid;
//
//        public class PotEls{
//            int code;
//            int elCode;
//            String elName;
//            double rate;
//            String beginDate;
//            String endDate;
//            double weight;
//
//            public int getCode() {
//                return code;
//            }
//
//            public void setCode(int code) {
//                this.code = code;
//            }
//
//            public int getElCode() {
//                return elCode;
//            }
//
//            public void setElCode(int elCode) {
//                this.elCode = elCode;
//            }
//
//            public String getElName() {
//                return elName;
//            }
//
//            public void setElName(String elName) {
//                this.elName = elName;
//            }
//
//            public double getRate() {
//                return rate;
//            }
//
//            public void setRate(double rate) {
//                this.rate = rate;
//            }
//
//            public String getBeginDate() {
//                return beginDate;
//            }
//
//            public void setBeginDate(String beginDate) {
//                this.beginDate = beginDate;
//            }
//
//            public String getEndDate() {
//                return endDate;
//            }
//
//            public void setEndDate(String endDate) {
//                this.endDate = endDate;
//            }
//
//            public double getWeight() {
//                return weight;
//            }
//
//            public void setWeight(double weight) {
//                this.weight = weight;
//            }
//        }
//        public class Codes{
//        }
//        public class File{
//
//            String code;
//            String fileName;
//            int fileSize;
//            String oldFileName;
//            int oldFileSize;
//            String dailyFolderPath;
//            boolean dailyFolder;
//            String home;
//            String fileFullPath;
//
//            public String getCode() {
//                return code;
//            }
//
//            public void setCode(String code) {
//                this.code = code;
//            }
//
//            public String getFileName() {
//                return fileName;
//            }
//
//            public void setFileName(String fileName) {
//                this.fileName = fileName;
//            }
//
//            public int getFileSize() {
//                return fileSize;
//            }
//
//            public void setFileSize(int fileSize) {
//                this.fileSize = fileSize;
//            }
//
//            public String getOldFileName() {
//                return oldFileName;
//            }
//
//            public void setOldFileName(String oldFileName) {
//                this.oldFileName = oldFileName;
//            }
//
//            public int getOldFileSize() {
//                return oldFileSize;
//            }
//
//            public void setOldFileSize(int oldFileSize) {
//                this.oldFileSize = oldFileSize;
//            }
//
//            public String getDailyFolderPath() {
//                return dailyFolderPath;
//            }
//
//            public void setDailyFolderPath(String dailyFolderPath) {
//                this.dailyFolderPath = dailyFolderPath;
//            }
//
//            public boolean isDailyFolder() {
//                return dailyFolder;
//            }
//
//            public void setDailyFolder(boolean dailyFolder) {
//                this.dailyFolder = dailyFolder;
//            }
//
//            public String getHome() {
//                return home;
//            }
//
//            public void setHome(String home) {
//                this.home = home;
//            }
//
//            public String getFileFullPath() {
//                return fileFullPath;
//            }
//
//            public void setFileFullPath(String fileFullPath) {
//                this.fileFullPath = fileFullPath;
//            }
//        }
//
//        public int getType() {
//            return type;
//        }
//
//        public void setType(int type) {
//            this.type = type;
//        }
//
//        public int getInvestType() {
//            return investType;
//        }
//
//        public void setInvestType(int investType) {
//            this.investType = investType;
//        }
//
//        public String getCode() {
//            return code;
//        }
//
//        public void setCode(String code) {
//            this.code = code;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public String getDescript() {
//            return descript;
//        }
//
//        public void setDescript(String descript) {
//            this.descript = descript;
//        }
//
//        public Long getMinPrice() {
//            return minPrice;
//        }
//
//        public void setMinPrice(Long minPrice) {
//            this.minPrice = minPrice;
//        }
//
//        public int getElNum() {
//            return elNum;
//        }
//
//        public void setElNum(int elNum) {
//            this.elNum = elNum;
//        }
//
//        public double getRate() {
//            return rate;
//        }
//
//        public void setRate(double rate) {
//            this.rate = rate;
//        }
//
//        public double getRateOne() {
//            return rateOne;
//        }
//
//        public void setRateOne(double rateOne) {
//            this.rateOne = rateOne;
//        }
//
//        public double getRateThr() {
//            return rateThr;
//        }
//
//        public void setRateThr(double rateThr) {
//            this.rateThr = rateThr;
//        }
//
//        public double getRateSix() {
//            return rateSix;
//        }
//
//        public void setRateSix(double rateSix) {
//            this.rateSix = rateSix;
//        }
//
//        public int getActive() {
//            return active;
//        }
//
//        public void setActive(int active) {
//            this.active = active;
//        }
//
//        public int getView() {
//            return view;
//        }
//
//        public void setView(int view) {
//            this.view = view;
//        }
//
//        public int getStatus() {
//            return status;
//        }
//
//        public void setStatus(int status) {
//            this.status = status;
//        }
//
//        public String getDate() {
//            return date;
//        }
//
//        public void setDate(String date) {
//            this.date = date;
//        }
//
//        public ArrayList<PotEls> getPotEls() {
//            return potEls;
//        }
//
//        public void setPotEls(ArrayList<PotEls> potEls) {
//            this.potEls = potEls;
//        }
//
//        public Codes getCodes() {
//            return codes;
//        }
//
//        public void setCodes(Codes codes) {
//            this.codes = codes;
//        }
//
//        public File getFile() {
//            return file;
//        }
//
//        public void setFile(File file) {
//            this.file = file;
//        }
//
//        public Select getSelect() {
//            return select;
//        }
//
//        public void setSelect(Select select) {
//            this.select = select;
//        }
//
//        public int getUid() {
//            return uid;
//        }
//
//        public void setUid(int uid) {
//            this.uid = uid;
//        }
//
//        public class Select {
//            int type;
//            boolean isZim;
//            boolean isDam;
//            String code;
//            String name;
//            String descript;
//            int minPrice;
//            double rate;
//
//            public int getType() {
//                return type;
//            }
//
//            public void setType(int type) {
//                this.type = type;
//            }
//
//            public boolean isZim() {
//                return isZim;
//            }
//
//            public void setZim(boolean zim) {
//                isZim = zim;
//            }
//
//            public boolean isDam() {
//                return isDam;
//            }
//
//            public void setDam(boolean dam) {
//                isDam = dam;
//            }
//
//            public String getCode() {
//                return code;
//            }
//
//            public void setCode(String code) {
//                this.code = code;
//            }
//
//            public String getName() {
//                return name;
//            }
//
//            public void setName(String name) {
//                this.name = name;
//            }
//
//            public String getDescript() {
//                return descript;
//            }
//
//            public void setDescript(String descript) {
//                this.descript = descript;
//            }
//
//            public int getMinPrice() {
//                return minPrice;
//            }
//
//            public void setMinPrice(int minPrice) {
//                this.minPrice = minPrice;
//            }
//
//            public double getRate() {
//                return rate;
//            }
//
//            public void setRate(double rate) {
//                this.rate = rate;
//            }
//        }
//    }
//
//
//    static public class Page {
//        int pageNumber;
//        boolean firstPage;
//        boolean lastPage;
//        int totalPages;
//        int pageSize;
//        Sort sort = new Sort();
//        int totalElements;
//
//        public int getPageNumber() {
//            return pageNumber;
//        }
//
//        public void setPageNumber(int pageNumber) {
//            this.pageNumber = pageNumber;
//        }
//
//        public boolean isFirstPage() {
//            return firstPage;
//        }
//
//        public void setFirstPage(boolean firstPage) {
//            this.firstPage = firstPage;
//        }
//
//        public boolean isLastPage() {
//            return lastPage;
//        }
//
//        public void setLastPage(boolean lastPage) {
//            this.lastPage = lastPage;
//        }
//
//        public int getTotalPages() {
//            return totalPages;
//        }
//
//        public void setTotalPages(int totalPages) {
//            this.totalPages = totalPages;
//        }
//
//        public int getPageSize() {
//            return pageSize;
//        }
//
//        public void setPageSize(int pageSize) {
//            this.pageSize = pageSize;
//        }
//
//        public Sort getSort() {
//            return sort;
//        }
//
//        public void setSort(Sort sort) {
//            this.sort = sort;
//        }
//
//        public int getTotalElements() {
//            return totalElements;
//        }
//
//        public void setTotalElements(int totalElements) {
//            this.totalElements = totalElements;
//        }
//    }
//
//    static public class Sort {
//        boolean sorted;
//        boolean unsorted;
//        boolean empty;
//
//        public boolean isSorted() {
//            return sorted;
//        }
//
//        public void setSorted(boolean sorted) {
//            this.sorted = sorted;
//        }
//
//        public boolean isUnsorted() {
//            return unsorted;
//        }
//
//        public void setUnsorted(boolean unsorted) {
//            this.unsorted = unsorted;
//        }
//
//        public boolean isEmpty() {
//            return empty;
//        }
//
//        public void setEmpty(boolean empty) {
//            this.empty = empty;
//        }
//    }

}
