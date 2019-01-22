package quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.Cookpage1.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import quantec.com.moneypot.R;

public class Fg_NewItem extends Fragment {

    ImageView Fg_NewItem_Image;
    int page;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_fgtab2_cookpage1newitem, container, false);

        Fg_NewItem_Image = view.findViewById(R.id.Fg_NewItem_Image);
        if (getArguments() != null) {
            Bundle args = getArguments();

            if (args.getInt("imgRes") == 0) {
                Glide.with(getContext())
                        .load("11")
                        .placeholder(R.drawable.banner_01)
                        .error(R.drawable.banner_01)
                        .crossFade()
                        .into(Fg_NewItem_Image);
            } else {
                Glide.with(getContext())
                        .load("11")
                        .placeholder(R.drawable.item_banner_tuto)
                        .error(R.drawable.item_banner_tuto)
                        .crossFade()
                        .into(Fg_NewItem_Image);
            }
            page = args.getInt("imgRes");
            Fg_NewItem_Image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "그림 번호 : " + page, Toast.LENGTH_SHORT).show();
                }
            });
        }
        return view;
    }
}
