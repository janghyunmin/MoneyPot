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

public class Fg_StoryImage extends Fragment {

    ImageView fg_storyimage_view;
    int page;

    public Fg_StoryImage() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_fgtab2_cookpage1storyimage, container, false);

        fg_storyimage_view = view.findViewById(R.id.fg_storyimage_view);
        if (getArguments() != null) {
            Bundle args = getArguments();

            if(args.getInt("imgRes") == 0) {

                Glide.with(getContext())
                        .load("11")
                        .placeholder(R.drawable.img_userstory_01)
                        .error(R.drawable.img_userstory_01)
                        .crossFade()
                        .into(fg_storyimage_view);
            }
            else {
                Glide.with(getContext())
                        .load("11")
                        .placeholder(R.drawable.img_userstory_02)
                        .error(R.drawable.img_userstory_02)
                        .crossFade()
                        .into(fg_storyimage_view);
            }

            page = args.getInt("imgRes");
            fg_storyimage_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "그림 번호 : "+ page, Toast.LENGTH_SHORT).show();
                }
            });
        }
        return view;
    }
}