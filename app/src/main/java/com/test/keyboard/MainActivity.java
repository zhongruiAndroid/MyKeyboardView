package com.test.keyboard;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.github.keyboard.KeyboardUtil;

public class MainActivity extends AppCompatActivity {
    KeyboardView keyboardView;
    KeyboardView keyboardView2;
    KeyboardView keyboardView3;
    Keyboard keyboard;
    Keyboard keyboard2;
    Keyboard keyboard3;
    EditText et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        initView();
//        initKeyBoard();
        View viewById = findViewById(R.id.tt);
          et = findViewById(R.id.et);
//        KeyboardUtil keyboardUtil=new KeyboardUtil(this,getWindow().getDecorView().getRootView());
        KeyboardUtil keyboardUtil=new KeyboardUtil(this,viewById);
        keyboardUtil.setEditText(et);
    }

    private void initKeyBoard() {
        keyboard=new Keyboard(this,R.xml.number_keyboard);
        keyboard2=new Keyboard(this,R.xml.symbol_keyboard);
        keyboard3=new Keyboard(this,R.xml.letter_keyboard);

        keyboardView.setKeyboard(keyboard);
        keyboardView2.setKeyboard(keyboard2);
        keyboardView2.setPreviewEnabled(false);

        keyboardView3.setKeyboard(keyboard3);
        keyboardView3.setPreviewEnabled(false);



    }



    private void initView() {
//        keyboardView3=findViewById(R.id.keyboardView3);
//        keyboardView2=findViewById(R.id.keyboardView2);
//        keyboardView=findViewById(R.id.keyboardView);
        int i = (int) (1080*0.1-dip2px(this, 4*4))/2;
        keyboardView.setPadding(i,0,i,i);
        keyboardView.setPreviewEnabled(false);
        keyboardView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                return false;
            }
        });

        keyboardView.setOnKeyboardActionListener(new KeyboardView.OnKeyboardActionListener() {
            @Override
            public void onPress(int primaryCode) {
                Log("==onPress="+primaryCode);
            }

            @Override
            public void onRelease(int primaryCode) {

                Log("==onRelease="+primaryCode);
            }

            @Override
            public void onKey(int primaryCode, int[] keyCodes) {
                Log("==onKey="+primaryCode+"=="+keyCodes[0]+"=="+keyCodes[1]);
            }

            @Override
            public void onText(CharSequence text) {
                Log("==onText="+text);
            }

            @Override
            public void swipeLeft() {
//                Log("==swipeLeft=");
            }

            @Override
            public void swipeRight() {
//                Log("==swipeRight=");
            }

            @Override
            public void swipeDown() {
//                Log("==swipeDown=");
            }

            @Override
            public void swipeUp() {
                Log("==swipeUp=");
            }
        });
        keyboardView2.setOnKeyboardActionListener(new KeyboardView.OnKeyboardActionListener() {
            @Override
            public void onPress(int primaryCode) {
                Log("==onPress="+primaryCode);
            }

            @Override
            public void onRelease(int primaryCode) {

                Log("==onRelease="+primaryCode);
            }

            @Override
            public void onKey(int primaryCode, int[] keyCodes) {
                Log("==onKey="+primaryCode+"=="+keyCodes[0]+"=="+keyCodes[1]);
            }

            @Override
            public void onText(CharSequence text) {
                Log("==onText="+text);
            }

            @Override
            public void swipeLeft() {
//                Log("==swipeLeft=");
            }

            @Override
            public void swipeRight() {
//                Log("==swipeRight=");
            }

            @Override
            public void swipeDown() {
//                Log("==swipeDown=");
            }

            @Override
            public void swipeUp() {
                Log("==swipeUp=");
            }
        });
        keyboardView3.setOnKeyboardActionListener(new KeyboardView.OnKeyboardActionListener() {
            @Override
            public void onPress(int primaryCode) {
                Log("==onPress="+primaryCode);
            }

            @Override
            public void onRelease(int primaryCode) {

                Log("==onRelease="+primaryCode);
            }

            @Override
            public void onKey(int primaryCode, int[] keyCodes) {
                Log("==onKey="+primaryCode+"=="+keyCodes[0]+"=="+keyCodes[1]);
            }

            @Override
            public void onText(CharSequence text) {
                Log("==onText="+text);
            }

            @Override
            public void swipeLeft() {
//                Log("==swipeLeft=");
            }

            @Override
            public void swipeRight() {
//                Log("==swipeRight=");
            }

            @Override
            public void swipeDown() {
//                Log("==swipeDown=");
            }

            @Override
            public void swipeUp() {
                Log("==swipeUp=");
            }
        });
    }

    public void Log(String string){
        Log.i("===","==="+string);
    }


    private int px2dip(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(pxValue / scale + 0.5F);
    }

    private int dip2px(Context context, float dipValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dipValue * scale + 0.5F);
    }
}
