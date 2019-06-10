package quantec.com.moneypot.util.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;

public class KeyPressEditText extends androidx.appcompat.widget.AppCompatEditText
{
    private OnPressListener _listener;
    private OnPressDoneListener onPressDoneListener;

    public KeyPressEditText(Context context)
    {
        super(context);
    }
    public KeyPressEditText(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }
    public KeyPressEditText(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }
    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK && _listener != null)
        {
            _listener.onPress();
        }

        if(keyCode == KeyEvent.ACTION_DOWN){
            _listener.onPress();
        }

        if(keyCode == KeyEvent.ACTION_UP){
            _listener.onPress();
        }

        if(event.getAction() == KeyEvent.ACTION_DOWN){
            onPressDoneListener.onPressDone();
        }

        return super.onKeyPreIme(keyCode, event);
    }


    public void setOnPressListener(OnPressListener $listener)
    {
        _listener = $listener;
    }
    public interface OnPressListener
    {
        public void onPress();
    }

    public void setOnDoneListener(OnPressDoneListener onDoneListener){
        onPressDoneListener = onDoneListener;
    }
    public interface OnPressDoneListener{
        public void onPressDone();
    }
}
