package quantec.com.moneypot.Activity.Login.Model.nModel;

public class ModelFlushAuth {

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
            String token;
            String status;
            String authCode;

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getAuthCode() {
                return authCode;
            }

            public void setAuthCode(String authCode) {
                this.authCode = authCode;
            }
        }

}
