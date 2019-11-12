package com.quantec.moneypot.util.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class VerticalViewPager extends ViewPager {

    public VerticalViewPager(@NonNull Context context) {
        this(context, null);
    }

    public VerticalViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setPageTransformer(false, new DefaultTransformer());
    }

    private MotionEvent swapTouchEvent(MotionEvent event) {
        float width = getWidth();
        float height = getHeight();

        float newX = (event.getY() / height) * width;
        float newY = (event.getX() / width) * height;
        event.setLocation(newX, newY);
        return event;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = super.onInterceptTouchEvent(swapTouchEvent(ev));
        swapTouchEvent(ev);
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(swapTouchEvent(ev));
    }

    public class DefaultTransformer implements ViewPager.PageTransformer {

        @Override
        public void transformPage(@NonNull View page, float position) {

            float alpha = 0;
            if(0 <= position && position <= 1){
                alpha = 1 - position;
            }else if(-1 < position && position <= 1){
                alpha = position + 1;
            }
            page.setAlpha(alpha);
            page.setTranslationX(page.getWidth() * -position);
            float yPosition = position * page.getHeight();
            page.setTranslationY(yPosition);
        }
    }


}

//public class VerticalViewPager extends ViewPager {
//
//    public static final int HORIZONTAL = 0;
//    public static final int VERTICAL = 1;
//
//    private int mSwipeOrientation;
//
//
//    public VerticalViewPager(@NonNull Context context) {
//        super(context);
//        mSwipeOrientation = HORIZONTAL;
//        initSwipeMethode();
//    }
//
//    public VerticalViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
//        super(context, attrs);
////        initSwipeMethode();
//    }
//
//    private MotionEvent swapXY(MotionEvent event) {
//        float width = getWidth();
//        float height = getHeight();
//
//        float newX = (event.getY() / height) * width;
//        float newY = (event.getX() / width) * height;
//        event.setLocation(newX, newY);
//        return event;
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//        return super.onTouchEvent(mSwipeOrientation == VERTICAL ? swapXY(ev) : ev);
//    }
//
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//
//        if(mSwipeOrientation == VERTICAL){
//            boolean intercepted = super.onInterceptTouchEvent(swapXY(ev));
//            swapXY(ev);
//            return intercepted;
//        }
//
//        return super.onInterceptTouchEvent(ev);
//    }
//
//   private void initSwipeMethode(){
//        if(mSwipeOrientation == VERTICAL){
//            setPageTransformer(false, new VerticalPageTransformer());
//        }
//   }
//
//   private class VerticalPageTransformer implements ViewPager.PageTransformer {
//
//       @Override
//       public void transformPage(@NonNull View page, float position) {
//
//           if(position <= -1){
//               page.setAlpha(0);
//           }else if(position <= 1){
//               if(position < 0){
//                   float rate = 1f+(0.3f * position);
//                   page.setScaleX(rate);
//                   page.setScaleY(rate);
//                   page.setAlpha(rate);
//               }else{
//                   page.setTranslationY(page.getHeight()*position);
//                   page.setAlpha(1);
//                   page.setScaleX(1);
//                   page.setScaleY(1);
//               }
//               page.setTranslationX(page.getWidth() * -position);
//           }else{
//               page.setAlpha(0);
//           }
//
//       }
//   }
//
//}

//public class VerticalViewPager extends ViewPager {
//
//    public static final int HORIZONTAL = 0;
//    public static final int VERTICAL = 1;
//
//    private int mSwipeOrientation;
//
//
//    public VerticalViewPager(@NonNull Context context) {
//        super(context);
//        mSwipeOrientation = HORIZONTAL;
//        initSwipeMethode();
//    }
//
//    public VerticalViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
//        super(context, attrs);
////        initSwipeMethode();
//    }
//
//    private MotionEvent swapXY(MotionEvent event) {
//        float width = getWidth();
//        float height = getHeight();
//
//        float newX = (event.getY() / height) * width;
//        float newY = (event.getX() / width) * height;
//        event.setLocation(newX, newY);
//        return event;
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//        return super.onTouchEvent(mSwipeOrientation == VERTICAL ? swapXY(ev) : ev);
//    }
//
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//
//        if(mSwipeOrientation == VERTICAL){
//            boolean intercepted = super.onInterceptTouchEvent(swapXY(ev));
//            swapXY(ev);
//            return intercepted;
//        }
//
//        return super.onInterceptTouchEvent(ev);
//    }
//
//   private void initSwipeMethode(){
//        if(mSwipeOrientation == VERTICAL){
//            setPageTransformer(false, new VerticalPageTransformer());
//        }
//   }
//
//   private class VerticalPageTransformer implements ViewPager.PageTransformer {
//
//       @Override
//       public void transformPage(@NonNull View page, float position) {
//
//           if(position <= -1){
//               page.setAlpha(0);
//           }else if(position <= 1){
//               if(position < 0){
//                   float rate = 1f+(0.3f * position);
//                   page.setScaleX(rate);
//                   page.setScaleY(rate);
//                   page.setAlpha(rate);
//               }else{
//                   page.setTranslationY(page.getHeight()*position);
//                   page.setAlpha(1);
//                   page.setScaleX(1);
//                   page.setScaleY(1);
//               }
//               page.setTranslationX(page.getWidth() * -position);
//           }else{
//               page.setAlpha(0);
//           }
//
//       }
//   }
//
//}