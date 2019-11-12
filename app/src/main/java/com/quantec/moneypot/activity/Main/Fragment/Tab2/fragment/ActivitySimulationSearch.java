package com.quantec.moneypot.activity.Main.Fragment.Tab2.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.google.gson.Gson;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.quantec.moneypot.R;
import com.quantec.moneypot.rxandroid.RxEvent;
import com.quantec.moneypot.rxandroid.RxEventBus;

public class ActivitySimulationSearch extends AppCompatActivity {

    FloatingSearchView floatingSearchView;
    Bundle filterValue;
    List<ModelBlog> name;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<ModelSearchItem> modelSearchItems;
    AdapterSimulationSearch adapterSimulationSearch;

    boolean empty = true;
    InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulation_search);

        //스테이터스 바 색상 변경 -> 화이트
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.main_page_status_bar_color));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);


        floatingSearchView = findViewById(R.id.floating_search_view);
        floatingSearchView.setQueryTextSize(12);

        filterValue = new Bundle();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        modelSearchItems = new ArrayList<>();
        adapterSimulationSearch = new AdapterSimulationSearch(modelSearchItems, this);
        recyclerView.setAdapter(adapterSimulationSearch);

        floatingSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {

                modelSearchItems.clear();
                empty = true;

                for(ModelBlog modelBlog : name){

                    String title = modelBlog.getTitle();
                    if(title.trim().toLowerCase().contains(newQuery.trim()) && !newQuery.isEmpty()){
                        empty = false;
                        modelSearchItems.add(new ModelSearchItem(title, "", false));
                    }
                }
                if(empty & newQuery.length()!=0){
                    modelSearchItems.clear();
                    modelSearchItems.add(new ModelSearchItem("", "", true));
                }
                adapterSimulationSearch.notifyDataSetChanged();
            }
        });

        InputStream XmlFileInputStream = getResources().openRawResource(R.raw.blog);
        //2 This reads your JSON file
        String jsonString = readTextFile(XmlFileInputStream);

        // create a gson object
        Gson gson = new Gson();
        // read your json file into an array
        ModelBlog[] modelBlogs =  gson.fromJson(jsonString, ModelBlog[].class);
        // convert your array to a list using the Arrays utility class
        name = Arrays.asList(modelBlogs);

        adapterSimulationSearch.setSearchItemClick(new AdapterSimulationSearch.SearchItemClick() {
            @Override
            public void onClick(int position) {
                imm.hideSoftInputFromWindow(floatingSearchView.getWindowToken(), 0);
                filterValue.putString("filter", modelSearchItems.get(position).getTitle());
                RxEventBus.getInstance().post(new RxEvent(RxEvent.FILTER_VALUE, filterValue));
                supportFinishAfterTransition();
            }
        });

    }//onCreate 끝

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        imm.hideSoftInputFromWindow(floatingSearchView.getWindowToken(), 0);
    }

    //3 Converts the input stream into a String
    public String readTextFile(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte buf[] = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {

        }
        return outputStream.toString();
    }
}
