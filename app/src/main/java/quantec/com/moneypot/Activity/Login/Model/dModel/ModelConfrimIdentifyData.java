package quantec.com.moneypot.Activity.Login.Model.dModel;

public class ModelConfrimIdentifyData {

    Content content = new Content();

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
    }
}
