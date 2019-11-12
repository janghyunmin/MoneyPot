package com.quantec.moneypot.network.retrofit;

import com.quantec.moneypot.activity.Intro.ModelRegChk;
import com.quantec.moneypot.activity.Intro.ModelVerifiedFido;
import com.quantec.moneypot.activity.Login.Model.nModel.ModelAuthEmail;
import com.quantec.moneypot.activity.Login.Model.nModel.ModelFidoAuthCode;
import com.quantec.moneypot.activity.Login.Model.nModel.ModelFlushAuth;
import com.quantec.moneypot.activity.Login.Model.nModel.ModelLoginData;
import com.quantec.moneypot.activity.Login.Model.nModel.ModelIdentifyData;
import com.quantec.moneypot.activity.Login.Model.nModel.ModelAppInit;
import com.quantec.moneypot.activity.Login.Model.nModel.ModelUserInfo;
import com.quantec.moneypot.datamodel.nmodel.ModelAccounts;
import com.quantec.moneypot.datamodel.nmodel.ModelChartData;
import com.quantec.moneypot.datamodel.nmodel.ModelChkNicName;
import com.quantec.moneypot.datamodel.nmodel.ModelCommonData;
import com.quantec.moneypot.datamodel.nmodel.ModelDeleteLife;
import com.quantec.moneypot.datamodel.nmodel.ModelInsertLife;
import com.quantec.moneypot.datamodel.nmodel.ModelLifeList;
import com.quantec.moneypot.datamodel.nmodel.ModelMarketPot;
import com.quantec.moneypot.datamodel.nmodel.ModelPotList;
import com.quantec.moneypot.datamodel.nmodel.ModelPrevPotContent;
import com.quantec.moneypot.datamodel.nmodel.ModelPrevPotSave;
import com.quantec.moneypot.datamodel.nmodel.ModelRecommendList;
import com.quantec.moneypot.datamodel.nmodel.ModelSearchedPageList;
import com.quantec.moneypot.datamodel.nmodel.ModelZimData;
import com.quantec.moneypot.datamodel.nmodel.ModelZimPotList;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitService {

    //전략 포트 찜
    @POST("pot/setSelect/{zimDam}/{mode}")
    Call<ModelZimData> getSelectedPortDate(@Header("Content-Type") String content_type, @Body Object select, @Path("zimDam") int zimDam, @Path("mode") String mode);

    //미리보기에서 내가만든포트의 차트데이터를 불러옴
    @GET("pot/getRateByPeriod/{code}/{pdate}/{propensity}")
    Call<ModelChartData> getPotChartData(@Path("code") String code, @Path("pdate") int pdate, @Path("propensity") int propensity);


    @POST("pot/getPage/{gubun}/{rate}/{page}/{size}")
    Call<ModelSearchedPageList> getPageList(@Header("Content-Type") String content_type, @Body Object filter, @Path("gubun") String gubun, @Path("page") int page, @Path("rate") int rate, @Path("size") int size);

    @POST("life/delLife/{code}")
    Call<ModelDeleteLife> getDelMyPot(@Path("code") String code);

    /**
     *
     * 내가 만든 포트 이미지 초기화
     */
    @POST("common/deleteImg/{code}")
    Call<Object> getInitMyPotImage(@Path("code") String code);

    /**
     *
     *검색 초기 페이지 지금 뜨는 상품 리스트
     *
     */
    @POST("pot/getPage/{gubun}/{rate}/{page}/{size}")
    Call<ModelRecommendList> getSearchRecomList(@Header("Content-Type") String content_type, @Body Object filter, @Path("gubun") String gubun, @Path("page") int page, @Path("rate") int rate, @Path("size") int size);

    /**
     *
     *회원가입 사용자 등록
     *
     */
    @POST("Auth/register")
    Call<ModelUserInfo> getConfirmedUserInfo(@Header("Content-Type") String content_type, @Body Object user);

    /**
     *
     *앱실행 시작
     *
     */
    @POST("Auth/init")
    Call<ModelAppInit> getAppInitData(@Header("Content-Type") String content_type, @Body Object InitReqDto);

    /**
     *
     *Fido 토큰 검증
     *
     */
    @POST("fido/verify")
    Call<ModelVerifiedFido> getVerifyData(@Header("Content-Type") String content_type, @Body Object fidoReq);

    /**
     *
     *FIdo 등록 확인
     *
     */
    @POST("fido/regChk")
    Call<ModelRegChk> getRegChkData(@Header("Content-Type") String content_type, @Body Object fidoReq);

    /**
     *
     *FIDO 미사용 JWT 로그인
     *
     */
    @POST("Auth/authenticate")
    Call<ModelLoginData> getLoginData(@Header("Content-Type") String content_type, @Body Object login);

    /**
     *
     *SMS 인증
     *
     */
    @POST("Auth/identify")
    Call<ModelIdentifyData> getIdentifyData(@Header("Content-Type") String content_type, @Body Object identifyDto);

    /**
     *
     *FIDO 등록 해제
     *
     */
    @POST("fido/withdraw")
    Call<ModelRegChk> getWithdrawFidoData(@Header("Content-Type") String content_type, @Body Object identifyDto);

    /**
     *
     * 토큰 갱신
     * Url 끝에  /  를 붙여준다 ( 안붙여주면 404 에러뜨는데 원인 불명 )
     *
     */
    @POST("Auth/flushAuth/")
    Call<ModelFlushAuth> getFlushAuthData(@Header("Content-Type") String content_type, @Body Object identifyDto);

    /**
     *
     * FIDO AuthCode 갱신 데이터
     *
     */
    @POST("fido/getAuthCode")
    Call<ModelFidoAuthCode> getFidoAuthCode(@Header("Content-Type") String content_type, @Body Object authReqDto);

    /**
     *
     * Email 인증
     *
     */
    @POST("Auth/sendAuthEmail")
    Call<ModelAuthEmail> getSendAuthEmailData(@Header("Content-Type") String content_type, @Body Object authReqDto);

    //공통코드 목록
    @GET("common/getCode")
    Call<ModelCommonData> getCommonData();

    /**
     *
     * 포트 리스트 받기
     *
     */
    @POST("pot/getPage/{gubun}/{rate}/{page}/{size}")
    Call<ModelPotList> getPotList(@Header("Content-Type") String content_type, @Body Object filter, @Path("gubun") String gubun, @Path("rate") int rate, @Path("page") int page, @Path("size") int size);
    /**
     *
     * 포트 리스트 받기
     *
     */
    @POST("pot/getPage/{gubun}/{rate}/{page}/{size}")
    Call<ModelMarketPot> getPotList2(@Header("Content-Type") String content_type, @Body Object filter, @Path("gubun") String gubun, @Path("rate") int rate, @Path("page") int page, @Path("size") int size);

    /**
     *
     * 찜한 포트 리스트 받기
     *
     */
    @POST("pot/getPage/{gubun}/{rate}/{page}/{size}")
    Call<ModelZimPotList> getZimPotList(@Header("Content-Type") String content_type, @Body Object filter, @Path("gubun") String gubun, @Path("rate") int rate, @Path("page") int page, @Path("size") int size);

    /**
     *
     * 포트 만들기시 코드값 받기
     *
     */
    @POST("pot/setPot")
    Call<ModelPrevPotContent> getCookPotCode(@Header("Content-Type") String content_type, @Body Object potMap);

    /**
     *
     * 만들어진 포트 업로드
     *
     */
    @POST("pot/upPot")
    Call<ModelPrevPotSave> getUpPot(@Header("Content-Type") String content_type, @Body Object upPotMap);


    /**
     *
     * 라이프챌린지 저장
     * investType은 704로 고정 값임 -> 넣어주는 값으로 변뎡되지 않는다.
     *
     */
    @POST("life/setLife")
    Call<ModelInsertLife> getSaveLife(@Header("Content-Type") String content_type, @Body Object lifeMap);

    /**
     *
     * 라이프챌린지 데이터 불러오기
     *
     */
    @GET("life/getLifes")
    Call<ModelLifeList> getLifes();

    /**
     *
     * 내 계좌정보 데이터 불러오기
     *
     */
    @GET("invest/getAccount")
    Call<ModelAccounts> getAccounts();

    /**
     *
     * 라이프챌린지 저장
     * investType은 704로 고정 값임 -> 넣어주는 값으로 변뎡되지 않는다.
     *
     */
    @POST("Auth/chkNickName/{nick}")
    Call<ModelChkNicName> getChkNicName(@Header("Content-Type") String content_type, @Path("nick") String nick);

//    해당 json 파일을 불러오는 로직은 완전히 따로 부르는 시스템을 만들어야된다.
//    @Streaming
//    @GET
//    Call<Object> dw(@Url String url);

}