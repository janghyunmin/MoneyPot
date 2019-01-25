package quantec.com.moneypot.Network.Retrofit;

import okhttp3.MultipartBody;
import quantec.com.moneypot.Activity.DetailPort.Model.nModel.ModelDetailPage;
import quantec.com.moneypot.Activity.DetailPort.Model.nModel.ModelInvestItem;
import quantec.com.moneypot.Activity.FinishMakePort.Model.nModel.ModelPortSavedInfo;
import quantec.com.moneypot.Activity.Intro.ModelTest2;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab1.Model.nModel.ModelMiddleChartData;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab1.Model.nModel.ModelTop10Item;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.Cookpage1.Model.nModel.ModelCookpage1Item;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.Cookpage2.Model.nModel.ModelCookPage2;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.Cookpage3.Model.nModel.ModelDeleteMyPort;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.Cookpage3.Model.nModel.ModelMyChartItem;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.Cookpage3.Model.nModel.ModelgetMyPortList;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab3.Fragment.Tab1_3m.Model.nModel.ModelTab13mRank;
import quantec.com.moneypot.Activity.PortProfileModify.Model.nModel.ModelImageSavedData;
import quantec.com.moneypot.Activity.SearchPort.BasicPage.Fragment.Model.nModel.ModelRecommendHotPort;
import quantec.com.moneypot.Activity.SearchPort.SearchedPage.Fragment.AllPageTab.Model.nModel.ModelPortZzim;
import quantec.com.moneypot.Activity.SearchPort.SearchedPage.Model.nModel.ModelSearchPage;
import quantec.com.moneypot.Model.nModel.ModelZzimCount;
import quantec.com.moneypot.Model.nModel.PortChartModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitService {

    //저장된 찜한 포트 불러오기
    @GET("recv_pot_list.php")
    Call<ModelZzimCount> getPortCallData(@Query("bIdx") int bIdx);

    //내포트 만들기 미리보기 차트
    @FormUrlEncoded
    @POST("test_php.php")
    Call<PortChartModel> getMyPortChart(@Field("json_string") String json_string);

    //메인 페이지 탑 10 포트 불러오기
    @GET("get_product_list.php")
    Call<ModelTop10Item> getTop10Data(@Query("dur") int pcode, @Query("bIdx") int bidx);

    //메인 페이지 중간위치 차트 데이터
    @FormUrlEncoded
    @POST("get_chart_data.php")
    Call<ModelMiddleChartData> getChart(@Field("pcode") int pcode, @Field("dur") String dur);

    //검색 페이지에서 지금 뜨는 포트
    @GET("get_product_hot.php")
    Call<ModelRecommendHotPort> getHotPort();

    //검색페이지
    @GET("get_search_result.php")
    Call<ModelSearchPage> getSearch(@Query("key") String key, @Query("bIdx") int bIdx);

    //찜한 포트 저장( 수정 버전 - 단일 찜 코드 업로드 )
    @FormUrlEncoded
    @POST("send_pot_list.php")
    Call<ModelPortZzim> getPortSaveData(@Field("pcode") int pcode, @Field("del") int del);

    //상세페이지에서 코드값을 기준으로 이름과 팁을 불러옴
    @FormUrlEncoded
    @POST("get_info.php")
    Call<ModelDetailPage> getDetailPage(@Field("pcode") int pcode);

    //상세페이지에서 코드값을 기준으로 종목 리스트를 불러옴
    @FormUrlEncoded
    @POST("get_invest_list.php")
    Call<ModelInvestItem> getDetailPagePort(@Field("pcode") int pcode);

    //임시 -> 포트요리 홈에서 재료들
    @GET("get_product_list_res.php")
    Call<ModelCookpage1Item> getHotData();

    //포트요리 담기
    //0 -> 담기 / 1 -> 삭제 / 2 -> 전체삭제
    @FormUrlEncoded
    @POST("send_res_pot_list.php")
    Call<Object> getCookBasketData(@Field("pcode") int pcode, @Field("del") int del);

    //내가 담은 포트요리 전체 데이터
    @GET("recv_res_pot_list.php")
    Call<ModelCookPage2> getCookData(@Query("opt") int opt);

    //내포트 차트 데이터
    @FormUrlEncoded
    @POST("get_my_chart_data.php")
    Call<ModelMyChartItem> getChartMyPortData(@Field("ucode") int ucode, @Field("dur") String dur);


    //내 포트 삭제
    //하나지울때는 opt=0
    //전체 지울 때는 opt=1
    @FormUrlEncoded
    @POST("del_my_product.php")
    Call<ModelDeleteMyPort> getDeleteMyPortData(@Field("ucode") int ucode, @Field("opt") int opt);

    //내 포트 데이터 불러오기
    //받아오는 데이터
    // photo = 0 -> 초기이미지 / photo = 1 -> 커스텀 이미지
    @GET("get_my_product.php")
    Call<ModelgetMyPortList> getMyPortData();

    //내포트 만들기 저장
    @FormUrlEncoded
    @POST("mypot_insert.php")
    Call<ModelPortSavedInfo> getMyportInsert(@Field("mpName") String mpName, @Field("wch") String wch);

    //내포트 만들기 취소
    @POST("mypot_del.php")
    Call<Object> getMyportCancle();

    // 포트마켓 랭킹 3개월 리스트
    @GET("get_product_list.php")
    Call<ModelTab13mRank> getRankData(@Query("dur") String pcode, @Query("bIdx") int bidx);


    //내포트 이미지 업로드+이름
    //opt = 0
    //이미지 + 이름 + 설명
    //opt = 1
    //이름 + 설명
    //opt = 2
    //이미지 초기화 + 이름 + 설명
    @Multipart
    @POST("icon_upload.php")
    Call<ModelImageSavedData> getImageTextUpload(@Part MultipartBody.Part opt, @Part MultipartBody.Part ucode, @Part MultipartBody.Part name, @Part MultipartBody.Part desc, @Part MultipartBody.Part file, @Part MultipartBody.Part wch);


    //내포트 이름 업로드
    //opt = 0
    //이미지 + 이름 + 설명
    //opt = 1
    //이름 + 설명
    //opt = 2
    //이미지 초기화 + 이름 + 설명
    @Multipart
    @POST("icon_upload.php")
    Call<ModelImageSavedData> getTextUpload(@Part MultipartBody.Part opt, @Part MultipartBody.Part ucode, @Part MultipartBody.Part name, @Part MultipartBody.Part desc, @Part MultipartBody.Part wch);


//    @GET("admin/getStockRate")
//    Call<Object> getTest(@Query("stCode") String stCode, @Query("page") int page, @Query("size") int size);

    @GET("admin/getStockRate/{stCode}/{page}/{size}")
    Call<Object> getTest(@Path("stCode") String stCode, @Path("page") int page, @Path("size") int size);

    @GET("strategy/getStPage/{page}/{rate}/{size}")
    Call<ModelTest2> getTest2(@Path("page") int page, @Path("rate") int rate, @Path("size") int size);
}
