package quantec.com.moneypot.Activity.Main.Fragment.FgTab2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class AdapterPotCookAllocation extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<ModelPotCookAllocation> modelPotCookAllocations;
    Context context;

    List<Entry> entries;
    LineDataSet lineDataSet;
    LineData lineData;

    public AdapterPotCookAllocation(ArrayList<ModelPotCookAllocation> modelPotCookAllocations, Context context, List<Entry> entries, LineDataSet lineDataSet, LineData lineData) {
        this.modelPotCookAllocations = modelPotCookAllocations;
        this.context = context;
        this.entries = entries;
        this.lineDataSet = lineDataSet;
        this.lineData = lineData;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return modelPotCookAllocations.size();
    }
}
