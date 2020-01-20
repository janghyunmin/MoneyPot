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
import com.quantec.moneypot.activity.Main.Fragment.Tab3.ModelPotSimul;
import com.quantec.moneypot.datamodel.nmodel.ModelAccountExist;
import com.quantec.moneypot.datamodel.nmodel.ModelAccounts;
import com.quantec.moneypot.datamodel.nmodel.ModelApiToken;
import com.quantec.moneypot.datamodel.nmodel.ModelAssetsCustom;
import com.quantec.moneypot.datamodel.nmodel.ModelChartData;
import com.quantec.moneypot.datamodel.nmodel.ModelChkNicName;
import com.quantec.moneypot.datamodel.nmodel.ModelCommonData;
import com.quantec.moneypot.datamodel.nmodel.ModelNCustomDel;
import com.quantec.moneypot.datamodel.nmodel.ModelDeleteLife;
import com.quantec.moneypot.datamodel.nmodel.ModelInsertLife;
import com.quantec.moneypot.datamodel.nmodel.ModelLifeList;
import com.quantec.moneypot.datamodel.nmodel.ModelMarketPot;
import com.quantec.moneypot.datamodel.nmodel.ModelPotList;
import com.quantec.moneypot.datamodel.nmodel.ModelPrevPotContent;
import com.quantec.moneypot.datamodel.nmodel.ModelPrevPotSave;
import com.quantec.moneypot.datamodel.nmodel.ModelRecommendList;
import com.quantec.moneypot.datamodel.nmodel.ModelSearchDb;
import com.quantec.moneypot.datamodel.nmodel.ModelSearchOrder;
import com.quantec.moneypot.datamodel.nmodel.ModelSearchedPageList;
import com.quantec.moneypot.datamodel.nmodel.ModelUserFollow;
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
    @POST("auth/register")
    Call<ModelUserInfo> getConfirmedUserInfo(@Header("Content-Type") String content_type, @Body Object user);

    /**
     *
     *앱실행 시작
     *
     */
    @POST("auth/init")
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
    @POST("auth/authenticate")
    Call<ModelLoginData> getLoginData(@Header("Content-Type") String content_type, @Body Object login);

    /**
     *
     *SMS 인증
     *
     */
    @POST("auth/identify")
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
    @POST("auth/flushAuth/")
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
    @POST("auth/sendAuthEmail")
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
    @POST("auth/chkNickName/{nick}")
    Call<ModelChkNicName> getChkNicName(@Header("Content-Type") String content_type, @Path("nick") String nick);

//    해당 json 파일을 불러오는 로직은 완전히 따로 부르는 시스템을 만들어야된다.
//    @Streaming
//    @GET
//    Call<Object> dw(@Url String url);

    /**
     *
     * 시뮬레이션 차트 데이터
     *
     */
    @POST("pot/getCoreData")
    Call<ModelPotSimul> getPotSimul(@Header("Content-Type") String content_type, @Body Object ex);


    /**
     *
     *검색 로컬 DB에 넣을 단일 묶음 데이터 호출
     *
     */
    @GET("home/init")
    Call<ModelSearchDb> getStockDb();


    /**
     *
     * 계좌여부 체크
     *
     */
    @POST("partner/sh/accountExist")
    Call<ModelAccountExist> getAccountExist(@Header("Content-Type") String content_type);


    /**
     *
     * 제휴사별 토큰 발행
     *
     */
    @POST("partner/sh/getApiToken")
    Call<ModelApiToken> getApiToken(@Header("Content-Type") String content_type);

    /**
     *
     * 계좌개설 처리 상태 저장 0(임시)/1(정상)
     *
     */
    @POST("partner/sh/setAccountStatus/{type}/{account}")
    Call<Object> setAccountStatus(@Header("Content-Type") String content_type, @Path("type") int type, @Path("account") String account);


    /**
     *
     * 검색 없을때 검색 제안 단어 불러오기
     *
     */
    @GET("home/getRecommendation/{type}")
    Call<Object> getRecommendation(@Path("type")int type);

    /**
     *
     * 인기검색어 불러오기
     *
     */
    @GET("common/getSearchOrder/{limit}")
    Call<ModelSearchOrder> getSearchOrder(@Path("limit")int limit);

    /**
     *
     * 검색어 저장
     *
     */
    @GET("common/setSearch/{limit}/{search}")
    Call<Object> setSearch(@Path("limit")int limit, @Path("search")String search);

    /**
     *
     * 선호도/팔로우/찜/담 선호도 리턴
     *
     */
    @POST("pot/getUserSelect/{type}")
    Call<ModelUserFollow> getUserSelect(@Header("Content-Type") String content_type, @Path("type") String type);


    /**
     *
     * 선호도/팔로우/찜/담 선호도 저장
     *
     */
    @POST("pot/setUserSelect/{type}")
    Call<Object> setUserSelect(@Header("Content-Type") String content_type, @Path("type") String type, @Body Object userSelectDto);


    /**
     *
     * 자산커스텀 목록
     * code : all / code number
     *
     */
    @POST("pot/getAssetsCustom/{code}")
    Call<ModelAssetsCustom> getAssetsCustom(@Header("Content-Type") String content_type, @Path("code") String type);


    /**
     *
     * 사용자 투자성향 저장
     * {’propensity’: '1|1|6|3|1,4|0|0’} 값만 전달, 별도 계산 없이 적극형으로 적용 or 신한에서 계산로직 전달 받아야함.
     * 앞두자리는 동의항목임
     * 투자성향 파트너사로 전달하는 sh_api필요
     *
     */
    @POST("auth/setPropensity")
    Call<Object> setPropensity(@Header("Content-Type") String content_type, @Body Object juserPropensity);


    /**
     *
     * type: 11(자산)|12(챌린지), propensity: 701|703|704, period: one|thr|six|all
     * code: 시뮬레이션(getCoreData)때 내려준 코드, name: 제목, descript: 내용
     * codes [{ type: 0(개별종목)|1(묶음) , code: 코드, ptCode: 묶음코드(없으면 공백) }]
     *
     */
    @POST("pot/setAssetsCustom")
    Call<Object> setAssetsCustom(@Header("Content-Type") String content_type, @Body Object request);


    /**
     * 자산커스텀 포트 수정
     *
     * name, descript만 수정 / 이미지 수정은 Common/uploadsImg/{type}/{code} 사용
     * {"code":"", "name":"", "descript":""}
     *
     */
    @POST("pot/upAssetsCustom")
    Call<Object> upAssetsCustom(@Header("Content-Type") String content_type, @Body Object upPotMap);

    /**
     * 자산커스텀 포트 DeActive
     *
     * 초기화는 getAssetsCustom 리스트 전체 코드를 주면됨.
     *
     */
    @POST("pot/delAssetsCustom")
    Call<ModelNCustomDel> delAssetsCustom(@Header("Content-Type") String content_type, @Body Object deActiveAssetsMap);
}

