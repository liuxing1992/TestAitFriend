package com.example.admin.testaitfriend;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.DynamicDrawableSpan;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.admin.testaitfriend.ait.AitManager;
import com.example.admin.testaitfriend.ait.AitTextChangeListener;

public class MainActivity extends AppCompatActivity implements AitTextChangeListener, TextWatcher {

    private AitManager mManager;
    private EditText messageEditText;
    private TextView mTextView1, mTextView2;
    private boolean likeQQ = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        messageEditText = findViewById(R.id.edit);
        mTextView1 = findViewById(R.id.text1);
        mTextView2 = findViewById(R.id.text2);
        messageEditText.addTextChangedListener(this);
        mManager = new AitManager(this, "123", false);
        mManager.setTextChangeListener(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mManager != null) {
            mManager.onActivityResult(requestCode, resultCode, data);
            mTextView1.setText("");

            mTextView1.append(mManager.getAitTeamMember() + " ");
        }
    }

    @Override
    public void onTextAdd(String content, int start, int length) {

        if (!likeQQ)
            messageEditText.getEditableText().insert(start, content);
        else{
            if (start >= 1) {
                //把输入法输入的@删除 自己添加一个@
                messageEditText.getText().replace(start - 1, start, "");
            }
            SpannableString ss = new SpannableString("@"+content);
            final Bitmap bitmap = getNameBitmap(this , "@"+content);
            ss.setSpan(new DynamicDrawableSpan() {
                @Override
                public Drawable getDrawable() {
                    BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources() , bitmap);
                    bitmapDrawable.setBounds(0, 0, bitmapDrawable.getIntrinsicWidth(), bitmapDrawable.getIntrinsicHeight());
                    return bitmapDrawable;
                }
            } ,0 , ss.length() , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            messageEditText.append(ss);
            messageEditText.append(" ");
        }

    }

    @Override
    public void onTextDelete(int start, int length) {
        int end = start + length - 1;
        messageEditText.getEditableText().replace(start, end, "");
    }

    /**
     * 把返回的人名，转换成bitmap
     *
     * @param name 人名
     * @return 图片
     */
    private static Bitmap getNameBitmap(Context context, String name) {
        /* 把@相关的字符串转换成bitmap 然后使用DynamicDrawableSpan加入输入框中 */
        name = "" + name;
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        // 设置字体画笔的颜色
         paint.setColor(Color.WHITE);
        paint.setTextSize(48);
        Rect rect = new Rect();
        paint.getTextBounds(name, 0, name.length(), rect);
        // 获取字符串在屏幕上的长度
        int width = (int) (paint.measureText(name));
        final Bitmap bmp = Bitmap.createBitmap(width + 10, rect.height() + 10, Bitmap.Config.ARGB_8888);//
        Canvas canvas = new Canvas(bmp);
        canvas.drawColor(context.getResources().getColor(R.color.colorPrimary));
        canvas.drawText(name, rect.left, rect.height() - rect.bottom, paint);
        return bmp;
    }


    @Override
    public void onSameAitMember() {
        if (messageEditText == null) return;
        messageEditText.dispatchKeyEvent(new KeyEvent(
                KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
    }

    private int start;
    private int count;

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if (mManager != null) {
            mManager.beforeTextChanged(s, start, count, after);
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        this.start = start;
        this.count = count;
        if (mManager != null) {
            mManager.onTextChanged(s, start, before, count);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (mManager != null) {
            mManager.afterTextChanged(s);
        }
    }
}
