package quantec.com.moneypot.Activity.Payment.AdapterListPopUp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import quantec.com.moneypot.R;

public class ListPopupWindowAdapter extends BaseAdapter {

    private Activity mActivity;
    private List<String> mDataSource = new ArrayList<>();
    private LayoutInflater layoutInflater;

    private OnClickItemText onClickItemText;
    public interface  OnClickItemText {
        public void onClick(int position);
    }

    public void setOnClickItemText(OnClickItemText onClickItemText) {
        this.onClickItemText = onClickItemText;
    }

    public ListPopupWindowAdapter(Activity mActivity, List<String> mDataSource, OnClickItemText onClickItemText) {
        this.mActivity = mActivity;
        this.mDataSource = mDataSource;
        layoutInflater = mActivity.getLayoutInflater();
        this.onClickItemText = onClickItemText;
    }

    @Override
    public int getCount() {
        return mDataSource.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_activitypayment_listpopupwindow, null);
            holder.window_popup_title = convertView.findViewById(R.id.window_popup_title);
            holder.layout_tt = convertView.findViewById(R.id.layout_tt);
            holder.window_popup_image = convertView.findViewById(R.id.window_popup_image);
            convertView.setTag(holder);

        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.window_popup_title.setText(getItem(position).toString());

        if(getItem(position).toString().equals("신한금융투자")) {
            holder.window_popup_image.setBackgroundResource(R.drawable.shinhan_logo);
        }else if(getItem(position).toString().equals("한화투자증권")){
            holder.window_popup_image.setBackgroundResource(R.drawable.hanhwa_logo);
        }

        holder.layout_tt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onClickItemText != null) {
                    onClickItemText.onClick(position);
                }
            }
        });
        return convertView;
    }

    public class ViewHolder{
        private TextView window_popup_title;
        private RelativeLayout layout_tt;
        private ImageView window_popup_image;
    }
}
