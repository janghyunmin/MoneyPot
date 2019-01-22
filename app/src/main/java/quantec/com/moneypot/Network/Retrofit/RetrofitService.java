package quantec.com.moneypot.Network.Retrofit;

import quantec.com.moneypot.Activity.DetailPort.Model.nModel.ModelDetailPage;
import quantec.com.moneypot.Activity.DetailPort.Model.nModel.ModelInvestItem;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab1.Model.nModel.ModelMiddleChartData;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab1.Model.nModel.ModelTop10Item;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.Cookpage1.Model.nModel.ModelCookpage1Item;
import quantec.com.moneypot.Activity.SearchPort.BasicPage.Fragment.Model.nModel.ModelRecommendHotPort;
import quantec.com.moneypot.Activity.SearchPort.SearchedPage.Fragment.AllPageTab.Model.nModel.ModelPortZzim;
import quantec.com.moneypot.Activity.SearchPort.SearchedPage.Model.nModel.ModelSearchPage;
import quantec.com.moneypot.Model.nModel.ModelZzimCount;
import quantec.com.moneypot.Model.nModel.PortChartModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
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
}
