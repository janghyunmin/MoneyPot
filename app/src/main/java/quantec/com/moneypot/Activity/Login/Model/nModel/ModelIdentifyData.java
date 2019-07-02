package quantec.com.moneypot.Activity.Login.Model.nModel;

public class ModelIdentifyData {
    int status;
    long timestamp;
    int totalElements;
    boolean noContent;
    Content content = new Content();

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

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public class Content {

        String uid;
        String type;
        String name;
        String mobileNum;
        String telco;
        String birth;
        String gender;
        String nation;
        String inputCode;
        String reqNum;
        String ci;
        String di;
        String code;
        String message;
        int activeStep;
        String authCode;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMobileNum() {
            return mobileNum;
        }

        public void setMobileNum(String mobileNum) {
            this.mobileNum = mobileNum;
        }

        public String getTelco() {
            return telco;
        }

        public void setTelco(String telco) {
            this.telco = telco;
        }

        public String getBirth() {
            return birth;
        }

        public void setBirth(String birth) {
            this.birth = birth;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getNation() {
            return nation;
        }

        public void setNation(String nation) {
            this.nation = nation;
        }

        public String getInputCode() {
            return inputCode;
        }

        public void setInputCode(String inputCode) {
            this.inputCode = inputCode;
        }

        public String getReqNum() {
            return reqNum;
        }

        public void setReqNum(String reqNum) {
            this.reqNum = reqNum;
        }

        public String getCi() {
            return ci;
        }

        public void setCi(String ci) {
            this.ci = ci;
        }

        public String getDi() {
            return di;
        }

        public void setDi(String di) {
            this.di = di;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getActiveStep() {
            return activeStep;
        }

        public void setActiveStep(int activeStep) {
            this.activeStep = activeStep;
        }

        public String getAuthCode() {
            return authCode;
        }

        public void setAuthCode(String authCode) {
            this.authCode = authCode;
        }
    }

}
