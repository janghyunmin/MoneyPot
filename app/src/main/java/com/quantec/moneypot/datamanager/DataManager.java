package com.quantec.moneypot.datamanager;

public class DataManager {

    private static DataManager _Instance;
    public static DataManager get_INstance() {
        if(_Instance == null)
        {
            _Instance = new DataManager();
        }
        return _Instance;
    }

    private boolean CheckTab1 = false;
    private boolean ModifyPort = false;
    private boolean CheckCookPage = false;

    //체크 값을 통해서 찜한포트 갱신 여부 정함
    public boolean isCheckTab1() {
        return CheckTab1;
    }
    public void setCheckTab1(boolean checkTab1) {
        CheckTab1 = checkTab1;
    }

    //찜한포트에서 찜 전체 취소시 포트랭킹 갱신 함 ( 서버 갱신 없이 현재 불러온 랭킹 데이터에서 찜 여부만 전부 갱신됨 )
    public boolean isModifyPort() {
        return ModifyPort;
    }
    public void setModifyPort(boolean modifyPort) {
        ModifyPort = modifyPort;
    }

    public boolean isCheckCookPage() {
        return CheckCookPage;
    }
    public void setCheckCookPage(boolean checkCookPage) {
        CheckCookPage = checkCookPage;
    }
}