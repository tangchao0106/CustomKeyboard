package cim.fuchen.customkeyboard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by wangshuo01 on 2017/4/1.
 */

public class IKeyboardView extends KeyboardView {

    /**
     * 参考别人代码，keyboard xml 不设置 行列间距。用一个.9图片
     *
     * 在.9图片上面留出间距，这样只控制key的宽高比较方便
     *
     *
     */

    private Context  mContext;
    private Keyboard mKeyBoard;

    public IKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    public IKeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mKeyBoard = this.getKeyboard();


        List<Keyboard.Key> keys = null;
        if (mKeyBoard != null) {
            keys = mKeyBoard.getKeys();
        }

        if(keys != null){
            int count = keys.size();
            for (int i=0;i<count;i++){
                if(keys.get(0).codes[0] == 47 && keys.size() == 20)
                {//数字键盘
                    if((i+1)%5 == 0 || i%5==0)
                    {
                        drawKeyBackground(R.drawable.soft_key_blue,canvas,keys.get(i));
                        drawText(canvas,keys.get(i), Color.WHITE);
                    }
                }else if("0123456789".indexOf(keys.get(0).label.toString()) != -1){
                    if(keys.get(i).codes[0] == -1 || keys.get(i).codes[0] == -5 || keys.get(i).codes.length == 5 || keys.get(i).codes.length == 3){
                       drawKeyBackground(R.drawable.soft_key_blue,canvas,keys.get(i));
                        drawText(canvas,keys.get(i), Color.WHITE);
                    }
                }else{
                    if(keys.get(i).codes[0] == -5 || keys.get(i).codes.length == 5 || keys.get(i).codes.length == 3){
                        drawKeyBackground(R.drawable.soft_key_blue,canvas,keys.get(i));
                        drawText(canvas,keys.get(i), Color.WHITE);
                    }
                }



            }
        }

    }

    private void drawKeyBackground(int drawableId, Canvas canvas, Keyboard.Key key) {
        Drawable keyBackground = mContext.getResources().getDrawable(drawableId);
        int[] drawableState = key.getCurrentDrawableState();
        keyBackground.setState(drawableState);
        keyBackground.setBounds(key.x+getPaddingTop(), key.y+getPaddingLeft(), key.x + key.width+getPaddingBottom(), key.y+ key.height+getPaddingRight());
        keyBackground.draw(canvas);
    }


    private void drawText(Canvas canvas, Keyboard.Key key,int color) {
        Rect  bounds = new Rect();
        Paint paint = new Paint();
        paint.setTextAlign(Paint.Align.CENTER);


        paint.setAntiAlias(true);

        paint.setColor(color);
        if (key.label != null) {
            String label = key.label.toString();

            Field field;

            if (label.length() > 1 && key.codes.length < 2) {
                int labelTextSize = 0;
                try {
                    field = KeyboardView.class.getDeclaredField("mLabelTextSize");
                    field.setAccessible(true);
                    labelTextSize = (int) field.get(this);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                paint.setTextSize(labelTextSize);
                paint.setTypeface(Typeface.DEFAULT_BOLD);
            } else {
                int keyTextSize = 0;
                try {
                    field = KeyboardView.class.getDeclaredField("mLabelTextSize");
                    field.setAccessible(true);
                    keyTextSize = (int) field.get(this);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                paint.setTextSize(keyTextSize);
                paint.setTypeface(Typeface.DEFAULT);
            }

            paint.getTextBounds(key.label.toString(), 0, key.label.toString().length(), bounds);
            canvas.drawText(key.label.toString(), key.x + key.width / 2 + getPaddingLeft(),
                    key.y + key.height / 2 + bounds.height()/2 + getPaddingTop(), paint);
        } else if (key.icon != null) {
            key.icon.setBounds(key.x + (key.width - key.icon.getIntrinsicWidth()) / 2 + getPaddingLeft(),
                    key.y + (key.height - key.icon.getIntrinsicHeight()) / 2+ getPaddingTop(),
                    key.x + (key.width - key.icon.getIntrinsicWidth()) / 2 + key.icon.getIntrinsicWidth() + getPaddingRight()
                    , key.y + (key.height - key.icon.getIntrinsicHeight()) / 2 + key.icon.getIntrinsicHeight()+ getPaddingBottom());
            key.icon.draw(canvas);
        }

    }

}
