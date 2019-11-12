package com.quantec.moneypot.activity.Main.Fragment.Tab3;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.quantec.moneypot.R;
import com.quantec.moneypot.activity.Main.ActivityMain;
import com.quantec.moneypot.activity.Main.Fragment.Tab2.fragment.ActivitySimulationSearch;

public class FgTab3 extends Fragment {

    private ActivityMain activityMain;


    FloatingSearchView floatingSearchView;
    View searchBt;

    ConstraintLayout itemEmptyLayout, itemLayout;
    ImageView userBt;

    public FgTab3() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_tab3, container, false);

        initializeViews();

        floatingSearchView = view.findViewById(R.id.floating_search_view);
        floatingSearchView.setEnabled(false);

        searchBt = view.findViewById(R.id.searchBt);

        itemEmptyLayout = view.findViewById(R.id.itemEmptyLayout);
        itemLayout = view.findViewById(R.id.itemLayout);
        itemLayout.setVisibility(View.GONE);

        userBt = view.findViewById(R.id.userBt);

        return view;
    }

    private void initializeViews(){
        activityMain = (ActivityMain) getActivity();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof ActivityMain) {
            activityMain = (ActivityMain) context;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //스테이터스 바 색상 변경 -> 화이트
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Window w = activityMain.getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            Window window = activityMain.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.main_page_status_bar_color));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        searchBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activityMain, ActivitySimulationSearch.class);
// Pass data object in the bundle and populate details activity.
//                intent.putExtra(ActivitySimulationSearch.EXTRA_CONTACT, contact);
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(activityMain, (View)floatingSearchView, "searchView");
                startActivity(intent, options.toBundle());
            }
        });

        floatingSearchView.setQueryTextSize(12);
        floatingSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {
//                floatingSearchView.swapSuggestions();
            }
        });


        userBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemEmptyLayout.setVisibility(View.GONE);
                itemLayout.setVisibility(View.VISIBLE);
            }
        });

    }
}
