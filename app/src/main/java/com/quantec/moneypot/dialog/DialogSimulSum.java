package com.quantec.moneypot.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.quantec.moneypot.R;
import com.quantec.moneypot.activity.Main.Fragment.Tab3.ModelPreChartList;
import com.quantec.moneypot.activity.simulationsearch.ModelPreSimulSum;
import com.quantec.moneypot.activity.simulationsearch.ModelSimulSum;

import java.util.ArrayList;

public class DialogSimulSum extends Dialog {
    private View.OnClickListener closeListen;
    private ImageView closeBt;
    private Context context;
    private String title;
    TextView titleTextView;

    RecyclerView recyclerView;
    ArrayList<ModelSimulList> modelSimulLists;
    RecyclerView.LayoutManager layoutManager;
    AdapterDialogSimulSum adapterDialogSimul;

    OnDialogSimuResult dialogSimulResult;

        ArrayList<String> codeList;
    ArrayList<ModelPreSimulSum> modelPreChartLists;
    int count;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.dialog_simul);

        titleTextView = findViewById(R.id.title);
        titleTextView.setText(title);

        closeBt = findViewById(R.id.closeBt);
        closeBt.setOnClickListener(closeListen);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        adapterDialogSimul = new AdapterDialogSimulSum(modelSimulLists, context);

        recyclerView.setAdapter(adapterDialogSimul);

        adapterDialogSimul.setDialogSimulItemClick(new AdapterDialogSimulSum.DialogSimulItemClick() {
            @Override
            public void onClick(int position) {
                if(dialogSimulResult != null){

                    if(count < 10){

                        if(CheckedList(modelSimulLists.get(position).getCode())){
                            Toast.makeText(context, "중복된 기업입니다.", Toast.LENGTH_SHORT).show();
                            dialogSimulResult.finish(modelSimulLists.get(position).getTitle(), modelSimulLists.get(position).getCode(), modelSimulLists.get(position).getRate());
                        }else{
                            dialogSimulResult.finish(modelSimulLists.get(position).getTitle(), modelSimulLists.get(position).getCode(), modelSimulLists.get(position).getRate());
                        }

                    }else{
                        Toast.makeText(context, "최대 허용 갯수를 초과하였습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        adapterDialogSimul.setDialogSimulItemAllClick(new AdapterDialogSimulSum.DialogSimulItemAllClick() {
            @Override
            public void onClick(int position) {

            }
        });
    }

    public DialogSimulSum(Context context, int count, ArrayList<ModelSimulList> modelSimulLists, String title, ArrayList<String> codeList,
                       View.OnClickListener closeListen) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.context = context;
        this.count = count;
        this.closeListen = closeListen;
        this.title = title;
        this.modelSimulLists = modelSimulLists;
        this.codeList = codeList;

    }

    public void setDialogSimulResult(OnDialogSimuResult dialogSimulResult){
        this.dialogSimulResult = dialogSimulResult;
    }

    public interface OnDialogSimuResult{
        void finish(String title, String code, double rate);
    }

    boolean CheckedList(String code){

        for(String codeList : codeList){
            if(code.equals(codeList)){
                return true;
            }
        }
        return false;
    }
}