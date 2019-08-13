package quantec.com.moneypot.Network.Retrofit;

import quantec.com.moneypot.Activity.Intro.ModelRegChk;
import quantec.com.moneypot.Activity.Intro.ModelVerifiedFido;
import quantec.com.moneypot.Activity.Intro.OneMap;
import quantec.com.moneypot.Activity.Intro.User;
import quantec.com.moneypot.Activity.Login.Model.nModel.ModelAuthEmail;
import quantec.com.moneypot.Activity.Login.Model.nModel.ModelFidoAuthCode;
import quantec.com.moneypot.Activity.Login.Model.nModel.ModelFlushAuth;
import quantec.com.moneypot.Activity.Login.Model.nModel.ModelLoginData;
import quantec.com.moneypot.Activity.Login.Model.nModel.ModelIdentifyData;
import quantec.com.moneypot.Activity.Login.Model.nModel.ModelAppInit;
import quantec.com.moneypot.Activity.Login.Model.nModel.ModelUserIdChk;
import quantec.com.moneypot.Activity.Login.Model.nModel.ModelUserInfo;
import quantec.com.moneypot.Activity.Main.Fragment.Tab2.Adapter.ModelDelPot;
import quantec.com.moneypot.DataModel.nModel.ModelAccounts;
import quantec.com.moneypot.DataModel.nModel.ModelChartData;
import quantec.com.moneypot.DataModel.nModel.ModelCommonData;
import quantec.com.moneypot.DataModel.nModel.ModelDeleteLife;
import quantec.com.moneypot.DataModel.nModel.ModelInsertLife;
import quantec.com.moneypot.DataModel.nModel.ModelLifeList;
import quantec.com.moneypot.DataModel.nModel.ModelMarketPot;
import quantec.com.moneypot.DataModel.nModel.ModelPotList;
import quantec.com.moneypot.DataModel.nModel.ModelPrevPotContent;
import quantec.com.moneypot.DataModel.nModel.ModelPrevPotSave;
import quantec.com.moneypot.DataModel.nModel.ModelRecommendList;
import quantec.com.moneypot.DataModel.nModel.ModelSearchedPageList;
import quantec.com.moneypot.DataModel.nModel.ModelZimData;
import quantec.com.moneypot.DataModel.nModel.ModelZimPotList;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitService {

//    //메인 페이지 중간위치 차트 데이터
//    @FormUrlEncoded
//    @POST("get_chart_data.php")
//    Call<ModelMiddleChartData> getChart(@Field("pcode") int pcode, @Field("dur") String dur);

    //내포트 이미지 업로드+이름
    //opt = 0
    //이미지 + 이름 + 설명
    //opt = 1
    //이름 + 설명
    //opt = 2
    //이미지 초기화 + 이름 + 설명
//    @Multipart
//    @POST("common/uploadsImg/{code}")
//    Call<ModelImageSavedData> getImageTextUpload(@Part MultipartBody.Part image, @Path("code") String ucode);

    @GET("admin/getStockRate/{stCode}/{page}/{size}")
    Call<Object> getTest(@Path("stCode") String stCode, @Path("page") int page, @Path("size") int size);

//    //포트마켓 포트 리스트 불러옴
//    @POST("pot/getStPage/{rate}/{page}/{size}")
//    Call<ModelTab13mRank> getTest2(@Header("Content-Type") String content_type, @Body Object filter, @Path("page") int page, @Path("rate") int rate, @Path("size") int size);
//
//    //포트 차트데이터 불러옴
//    @GET("pot/getRateByPeriod/{type}/{code}/{period}")
//    Call<ModelTab13mChartData> getRankPort(@Path("type") int type, @Path("code") String code, @Path("period") int period);

    //로그인시 jwt값 불러옴
    @POST("Auth/authenticate")
    Call<Object> getTestLogin(@Header("Content-Type") String content_type, @Body User user);

    //맞춤포트에서 추천포트 랭킹 탑10 불러옴
//    @GET("pack/getStHotList/{limit}")
//    Call<ModelTop10Item> getTop10(@Path("limit") int limit);
//    @POST("pot/getPage/{gubun}/{rate}/{page}/{size}")
//    Call<ModelTab13mRank> getTop10(@Header("Content-Type") String content_type, @Body Object filter, @Path("gubun") String gubun, @Path("page") int page, @Path("rate") int rate, @Path("size") int size);
//
//   //상세 페이지 불러옴
//    @GET("pot/getDetail/{stCode}")
//    Call<ModelInvestItem> getDetailTest(@Path("stCode") String stCode);
//
    //전략 포트 찜
    @POST("pot/setSelect/{zimDam}/{mode}")
    Call<ModelZimData> getSelectedPortDate(@Header("Content-Type") String content_type, @Body Object select, @Path("zimDam") int zimDam, @Path("mode") String mode);
//
//    //포트 찜, 담은 리스트 ( 찜 및 담기를 전부 호출하여 isZim / isDam 으로 추려서 리스트 만들어야됨 )
//    @GET("pot/getSelect")
//    Call<ModelFgTab4ZimData> getZimDamList();
//
//    //포트만들기 미리보기
//    @POST("pot/setPot")
//    Call<ModelPrevMyPot> getPrevMyPot(@Header("Content-Type") String content_type, @Body Object potDto);
//
//    //미리보기에서 내가만든포트의 차트데이터를 불러옴
//    @GET("pot/getRateByPeriod/{type}/{code}/{period}")
//    Call<ModelMyChartData> getMyPotChartData(@Path("type") int type, @Path("code") String code, @Path("period") int period);
//
//    //내가만든포트 최종 저장
//    @POST("pot/upPot")
//    Call<ModelPortSavedInfo> getSavedMyPot(@Header("Content-Type") String content_type, @Body Object potDto);



    //미리보기에서 내가만든포트의 차트데이터를 불러옴
    @GET("pot/getRateByPeriod/{code}/{pdate}/{propensity}")
    Call<ModelChartData> getPotChartData(@Path("code") String code, @Path("pdate") int pdate, @Path("propensity") int propensity);

    /**
     * 전략 및 포트 리스트 불러옴
     * /pack/getPage
     *     // 전체가 rate desc 누적 수익률 높은 순
     *     // R 1. 추천포트 랭킹( 사용자 투자성향별 추천 포트 10개 ) : status = 50 & invest_type = ?, rate desc, page size = 10
     *     // L 2. 포트요리 > 랭킹도전/포트만들기 대회(포트리그 상위 3개) : status = 20, rate desc, page size = 3
     *     // H 2. [포트요리 > 핫한 재료(getHotList) : random, rate desc, limit]
     *     // I 2. 포트요리 > 안정적재료 : invest_type = ?, rate desc, page size
     *     // S 2. [포트요리 > 내가담은 포트(getSelect) : rate desc, all] -> rate는 누적값
     *     // U 2. 포트요리 > 내가만든 포트(요리된포트) : uid = myid & type > 10 & status != 0, rate desc, page
     *     // M 3. 포트마켓 > 투자가능 목록 : status >= 50, rate desc, page
     *     // Z 4. [찜한포트 > 내가찜한 포트(getSelect) : rate desc, all]
     *     // L 5. 포트리그 > 포트만들기 대회 : status = 20, rate desc, page
     */
    @POST("pot/getPage/{gubun}/{rate}/{page}/{size}")
    Call<ModelSearchedPageList> getPageList(@Header("Content-Type") String content_type, @Body Object filter, @Path("gubun") String gubun, @Path("page") int page, @Path("rate") int rate, @Path("size") int size);

    /**
     *
     * 내가 담은 리스트의 경우 다른 모델클래스를 만들어줘야됨
     * 받는 데이터 내용이 다름
     *
     */
//    @POST("pot/getPage/{gubun}/{rate}/{page}/{size}")
//    Call<ModelFgTab4ZimData> getPageList2(@Header("Content-Type") String content_type, @Body Object filter, @Path("gubun") String gubun, @Path("page") int page, @Path("rate") int rate, @Path("size") int size);

    /**
     * 내가 만든 포트 삭제 ( 단일 삭제 )
     *
     *  에러코드
     *  POT_NOT_USER_DELETE		(44300, HttpStatus.NOT_ACCEPTABLE, "NOT Delete(User) !!|다른 사용자가 생성한 포트입니다."),
     * 	POT_NOT_ALLOW_DELETE	(44400, HttpStatus.NOT_ACCEPTABLE, "NOT Delete(Status) !!|삭제가 불가능한 상태 포트입니다."),
     */
    @POST("life/delLife/{code}")
    Call<ModelDeleteLife> getDelMyPot(@Path("code") String code);

    /**
     * 내가 만든 포트 삭제 ( 전체 삭제 )
     *
     */
    @POST("pot/delPotAll")
    Call<Object> getDelMyPotAll();

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
     * 회원가입 아이디 중복 체크
     */
    @POST("Auth/userIdChk/{userId}")
    Call<ModelUserIdChk> getUserIdChk(@Path("userId") String userId);


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


    //로그인시 jwt값 불러옴
    @POST("rsa/test/{plaintext}/{enctext}")
    Call<Object> getTestLogin22(@Header("Content-Type") String content_type,  @Path("plaintext") String plaintext, @Path("enctext") String enctext, @Body OneMap oneMap );

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
     * 만들어진 포트 단일 삭제
     *
     */
    @POST("pot/delPot/{code}")
    Call<ModelDelPot> getDelPot(@Header("Content-Type") String content_type, @Path("code") String code);


    /**
     *
     * 만들어진 포트 전부 삭제
     *
     */
    @POST("pot/delPotAll")
    Call<ModelDelPot> getDelPotAll(@Header("Content-Type") String content_type);


    /**
     *
     * 포트 리스트 받기
     *
     */
    @POST("pot/getPage/{gubun}/{rate}/{page}/{size}")
    Call<ModelLifeList> getLifeList(@Header("Content-Type") String content_type, @Body Object filter, @Path("gubun") String gubun, @Path("rate") int rate, @Path("page") int page, @Path("size") int size);



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

}
