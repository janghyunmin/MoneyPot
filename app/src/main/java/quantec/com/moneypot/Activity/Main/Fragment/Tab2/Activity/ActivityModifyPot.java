package quantec.com.moneypot.Activity.Main.Fragment.Tab2.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import quantec.com.moneypot.Activity.Main.Fragment.Tab2.Adapter.AdapterModifyPot;
import quantec.com.moneypot.Activity.Main.Fragment.Tab2.Adapter.ModelDelPot;
import quantec.com.moneypot.Activity.Main.Fragment.Tab2.Adapter.ModelModifyPot;
import quantec.com.moneypot.Activity.Main.Fragment.Tab2.Adapter.ModelMyPotPage;
import quantec.com.moneypot.Activity.Main.Fragment.Tab2.FgMyPotPage;
import quantec.com.moneypot.Activity.PotDetail.ActivityPotDetail;
import quantec.com.moneypot.DataModel.dModel.Filter;
import quantec.com.moneypot.DataModel.nModel.ModelPotList;
import quantec.com.moneypot.Dialog.DialogModifyPotDelete;
import quantec.com.moneypot.Network.Retrofit.RetrofitClient;
import quantec.com.moneypot.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityModifyPot extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    AdapterModifyPot adapterModifyPot;
    ArrayList<ModelModifyPot> modelModifyPots;

    private DialogModifyPotDelete dialogModifyPotDelete;
    String delCode;
    int delPosition;

    boolean changedPot = false;

    TextView deleteBt, number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_pot);

        deleteBt = findViewById(R.id.deleteBt);
        number = findViewById(R.id.number);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        modelModifyPots = new ArrayList<>();

        adapterModifyPot = new AdapterModifyPot(modelModifyPots, this);
        recyclerView.setAdapter(adapterModifyPot);

        Filter filter = new Filter();
        Call<ModelPotList> getReList = RetrofitClient.getInstance().getService().getPotList("application/json", filter, "U", 0,0,10);
        getReList.enqueue(new Callback<ModelPotList>() {
            @Override
            public void onResponse(Call<ModelPotList> call, Response<ModelPotList> response) {
                if (response.code() == 200) {

                    if(response.body().getTotalElements() == 0){

                      modelModifyPots.add(new ModelModifyPot(true, "", "","", "", 0));
                      deleteBt.setVisibility(View.INVISIBLE);
                    }
                    else{

                        for(int index = 0; index < response.body().getContent().size() ; index++){

                            modelModifyPots.add(new ModelModifyPot(false, response.body().getContent().get(index).getName(),
                                    "",""
                                    , response.body().getContent().get(index).getCode(), 0));
                        }
                        deleteBt.setVisibility(View.VISIBLE);
                    }

                    number.setText(String.valueOf(response.body().getTotalElements()));
                    adapterModifyPot.notifyDataSetChanged();

                }
            }
            @Override
            public void onFailure(Call<ModelPotList> call, Throwable t) {
            }
        });


        adapterModifyPot.setModifyPotDeleteClick(new AdapterModifyPot.ModifyPotDeleteClick() {
            @Override
            public void onClick(int position) {

                dialogModifyPotDelete = new DialogModifyPotDelete(ActivityModifyPot.this, ALLleftListener, ALLrightListener, modelModifyPots.get(position).getTitle());
                dialogModifyPotDelete.show();

                delCode = modelModifyPots.get(position).getCode();
                delPosition = position;

            }
        });

        adapterModifyPot.setModifyPotItemClick(new AdapterModifyPot.ModifyPotItemClick() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(ActivityModifyPot.this, ActivityPotDetail.class);
                intent.putExtra("potCode", modelModifyPots.get(position).getCode());
                startActivity(intent);
            }
        });


        deleteBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogModifyPotDelete = new DialogModifyPotDelete(ActivityModifyPot.this, ALLleftListener, AllDelPotOk, "내가 만든 모든 포트");
                dialogModifyPotDelete.show();

            }
        });

    }//onCreate 끝


    private View.OnClickListener ALLleftListener = new View.OnClickListener() {
        public void onClick(View v) {
            dialogModifyPotDelete.dismiss();
        }
    };

    private View.OnClickListener ALLrightListener = new View.OnClickListener() {
        public void onClick(View v) {

            Call<ModelDelPot> getchartItem = RetrofitClient.getInstance().getService().getDelPot("application/json", delCode);
            getchartItem.enqueue(new Callback<ModelDelPot>() {
                @Override
                public void onResponse(Call<ModelDelPot> call, Response<ModelDelPot> response) {

                    if(response.code() == 200) {

                        modelModifyPots.remove(delPosition);

                        if(modelModifyPots.size() == 0){

                            number.setText(String.valueOf(0));
                            deleteBt.setVisibility(View.INVISIBLE);
                            modelModifyPots.add(new ModelModifyPot(true, "", "","", "", 0));
                        }
                        else{

                            number.setText(String.valueOf(modelModifyPots.size()));
                            deleteBt.setVisibility(View.VISIBLE);
                        }

                        dialogModifyPotDelete.dismiss();
                        changedPot = true;
                        adapterModifyPot.notifyDataSetChanged();
                    }
                }
                @Override
                public void onFailure(Call<ModelDelPot> call, Throwable t) {
                    dialogModifyPotDelete.dismiss();
                    Log.e("레트로핏 실패","값 : "+t.getMessage());
                }
            });
        }
    };

    private View.OnClickListener AllDelPotOk = new View.OnClickListener() {
        public void onClick(View v) {

            Call<ModelDelPot> getchartItem = RetrofitClient.getInstance().getService().getDelPotAll("application/json");
            getchartItem.enqueue(new Callback<ModelDelPot>() {
                @Override
                public void onResponse(Call<ModelDelPot> call, Response<ModelDelPot> response) {

                    if(response.code() == 200) {
                        number.setText(String.valueOf(0));
                        modelModifyPots.clear();
                        modelModifyPots.add(new ModelModifyPot(true, "", "","", "", 0));
                        deleteBt.setVisibility(View.INVISIBLE);
                        dialogModifyPotDelete.dismiss();
                        changedPot = true;
                        adapterModifyPot.notifyDataSetChanged();
                    }
                }
                @Override
                public void onFailure(Call<ModelDelPot> call, Throwable t) {
                    dialogModifyPotDelete.dismiss();
                    Log.e("레트로핏 실패","값 : "+t.getMessage());
                }
            });
        }
    };


    @Override
    public void onBackPressed() {

        if(changedPot){
            Intent intent1 = new Intent(ActivityModifyPot.this, FgMyPotPage.class);
            setResult(100, intent1);
            finish();
        }else{
            finish();
        }

    }
}
