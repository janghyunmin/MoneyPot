package quantec.com.moneypot.Network.Retrofit;

import okhttp3.MultipartBody;
import quantec.com.moneypot.Activity.DetailPort.Model.nModel.ModelDetailPage;
import quantec.com.moneypot.Activity.DetailPort.Model.nModel.ModelInvestItem;
import quantec.com.moneypot.Activity.FinishMakePort.Model.nModel.ModelPortSavedInfo;
import quantec.com.moneypot.Activity.Intro.User;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab1.Model.nModel.ModelMiddleChartData;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab1.Model.nModel.ModelTop10Item;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.Cookpage1.Model.nModel.ModelCookpage1Item;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.Cookpage2.Model.nModel.ModelCookPage2;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.Cookpage3.Model.nModel.ModelDeleteMyPort;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.Cookpage3.Model.nModel.ModelMyChartItem;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.Cookpage3.Model.nModel.ModelMyPotList;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.Cookpage3.Model.nModel.ModelgetMyPortList;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab3.Fragment.Tab1_3m.Model.nModel.ModelTab13mChartData;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab3.Fragment.Tab1_3m.Model.nModel.ModelTab13mRank;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab3.Fragment.Tab1_3m.Model.nModel.ModelZimData;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab4.Model.nModel.ModelFgTab4ZimData;
import quantec.com.moneypot.Activity.Main.ModelMyChartData;
import quantec.com.moneypot.Activity.Main.ModelPrevMyPot;
import quantec.com.moneypot.Activity.PortProfileModify.Model.nModel.ModelImageSavedData;
import quantec.com.moneypot.Activity.SearchPort.BasicPage.Fragment.Model.nModel.ModelRecommendHotPort;
import quantec.com.moneypot.Activity.SearchPort.SearchedPage.Fragment.AllPageTab.Model.nModel.ModelPortZzim;
import quantec.com.moneypot.Activity.SearchPort.SearchedPage.Model.nModel.ModelSearchPage;
import quantec.com.moneypot.ModelCommon.nModel.ModelZzimCount;
import quantec.com.moneypot.ModelCommon.nModel.PortChartModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitService {

    //메인 페이지 중간위치 차트 데이터
    @FormUrlEncoded
    @POST("get_chart_data.php")
    Call<ModelMiddleChartData> getChart(@Field("pcode") int pcode, @Field("dur") String dur);

    //내포트 이미지 업로드+이름
    //opt = 0
    //이미지 + 이름 + 설명
    //opt = 1
    //이름 + 설명
    //opt = 2
    //이미지 초기화 + 이름 + 설명
    @Multipart
    @POST("common/uploadsImg/{code}")
    Call<ModelImageSavedData> getImageTextUpload(@Part MultipartBody.Part image, @Path("code") String ucode);

    @GET("admin/getStockRate/{stCode}/{page}/{size}")
    Call<Object> getTest(@Path("stCode") String stCode, @Path("page") int page, @Path("size") int size);

    //포트마켓 포트 리스트 불러옴
    @POST("pack/getStPage/{rate}/{page}/{size}")
    Call<ModelTab13mRank> getTest2(@Header("Content-Type") String content_type, @Body Object filter, @Path("page") int page, @Path("rate") int rate, @Path("size") int size);

    //포트 차트데이터 불러옴
    @GET("pack/getRateByPeriod/{type}/{code}/{period}")
    Call<ModelTab13mChartData> getRankPort(@Path("type") int type, @Path("code") String code, @Path("period") int period);

    //로그인시 jwt값 불러옴
    @POST("Auth/authenticate")
    Call<Object> getTestLogin(@Header("Content-Type") String content_type, @Body User user);

    //맞춤포트에서 추천포트 랭킹 탑10 불러옴
    @GET("pack/getStHotList/{limit}")
    Call<ModelTop10Item> getTop10(@Path("limit") int limit);

   //상세 페이지 불러옴
    @GET("pack/getDetail/{stCode}")
    Call<ModelInvestItem> getDetailTest(@Path("stCode") String stCode);

    //전략 포트 찜
    @POST("pack/setSelect/{zimDam}/{mode}")
    Call<ModelZimData> getSelectedPortDate(@Header("Content-Type") String content_type, @Body Object select, @Path("zimDam") int zimDam, @Path("mode") String mode);

    //포트 찜, 담은 리스트 ( 찜 및 담기를 전부 호출하여 isZim / isDam 으로 추려서 리스트 만들어야됨 )
    @GET("pack/getSelect")
    Call<ModelFgTab4ZimData> getZimDamList();

    //포트만들기 미리보기
    @POST("pack/setPot")
    Call<ModelPrevMyPot> getPrevMyPot(@Header("Content-Type") String content_type, @Body Object potDto);

    //미리보기에서 내가만든포트의 차트데이터를 불러옴
    @GET("pack/getRateByPeriod/{type}/{code}/{period}")
    Call<ModelMyChartData> getMyPotChartData(@Path("type") int type, @Path("code") String code, @Path("period") int period);

    //내가만든포트 최종 저장
    @POST("pack/upPot")
    Call<ModelPortSavedInfo> getSavedMyPot(@Header("Content-Type") String content_type, @Body Object potDto);

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
    @POST("pack/getPage/{gubun}/{rate}/{page}/{size}")
    Call<ModelTab13mRank> getPageList(@Header("Content-Type") String content_type, @Body Object filter, @Path("gubun") String gubun, @Path("page") int page, @Path("rate") int rate, @Path("size") int size);

    /**
     *
     * 내가 담은 리스트의 경우 다른 모델클래스를 만들어줘야됨
     * 받는 데이터 내용이 다름
     *
     */
    @POST("pack/getPage/{gubun}/{rate}/{page}/{size}")
    Call<ModelFgTab4ZimData> getPageList2(@Header("Content-Type") String content_type, @Body Object filter, @Path("gubun") String gubun, @Path("page") int page, @Path("rate") int rate, @Path("size") int size);

    /**
     * 내가 만든 포트 삭제
     *
     *  에러코드
     *  POT_NOT_USER_DELETE		(44300, HttpStatus.NOT_ACCEPTABLE, "NOT Delete(User) !!|다른 사용자가 생성한 포트입니다."),
     * 	POT_NOT_ALLOW_DELETE	(44400, HttpStatus.NOT_ACCEPTABLE, "NOT Delete(Status) !!|삭제가 불가능한 상태 포트입니다."),
     */
    @POST("pack/delPot/{code}")
    Call<Object> getDelMyPot(@Path("code") String code);

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
    @POST("pack/getPage/{gubun}/{rate}/{page}/{size}")
    Call<ModelTab13mRank> getSearchRecomList(@Header("Content-Type") String content_type, @Body Object filter, @Path("gubun") String gubun, @Path("page") int page, @Path("rate") int rate, @Path("size") int size);
}
