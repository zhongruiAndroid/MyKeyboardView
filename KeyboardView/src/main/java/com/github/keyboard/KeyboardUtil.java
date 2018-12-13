package com.github.keyboard;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.support.annotation.ColorInt;
import android.support.annotation.IntDef;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.Selection;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

/**
 * @createBy r-zhong
 * @time 2018-12-11 13:53
 */
public class KeyboardUtil {
    /*键盘view*/
    private KeyboardView keyboardView;
    /*数字键盘*/
    private Keyboard numberKeyboard;
    /*字母键盘*/
    private Keyboard letterKeyboard;
    /*符号键盘*/
    private Keyboard symbolKeyboard;

    private TextView tvNumber;
    private TextView tvSymbol;
    private TextView tvLetter;
    private ImageButton ibKeyboardDown;
    private List<String> numberList;
    private View keyViewGroup;
    private EditText editText;
    private Activity activity;
    /**
     * 是否大写
     */
    private boolean isUpper = false;

    public KeyboardUtil(Activity context, View keyboardView) {
        this.keyViewGroup = keyboardView;
        this.activity = context;
        initKeyboard(context);

    }
    public void showKeyBoard(){
        keyViewGroup.setVisibility(View.VISIBLE);
    }
    public EditText getEditText() {
        return editText;
    }

    public void setEditText(EditText editText) {
        this.editText = editText;

        editText.requestFocus();
        Editable text = editText.getText();
        Selection.setSelection(text, text.length());
        hiddenKeyBoard(activity);

    }

    private void initKeyboard(Context context) {
        numberKeyboard = new Keyboard(context, R.xml.number_keyboard);
        letterKeyboard = new Keyboard(context, R.xml.letter_keyboard);
        symbolKeyboard = new Keyboard(context, R.xml.symbol_keyboard);


        tvNumber = keyViewGroup.findViewById(R.id.tvNumber);
        tvLetter = keyViewGroup.findViewById(R.id.tvLetter);
        tvSymbol = keyViewGroup.findViewById(R.id.tvSymbol);
        ibKeyboardDown = keyViewGroup.findViewById(R.id.ibKeyboardDown);

        tvNumber.setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                setCheckType(type_number);
                setKeyboardType();
                keyboardView.setKeyboard(numberKeyboard);
            }
        });
        tvLetter.setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                setCheckType(type_letter);
                setKeyboardType();
                keyboardView.setKeyboard(letterKeyboard);
            }
        });
        tvSymbol.setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                setCheckType(type_symbol);
                setKeyboardType();
                keyboardView.setKeyboard(symbolKeyboard);
            }
        });
        ibKeyboardDown.setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                setGone();
            }
        });

        keyboardView = keyViewGroup.findViewById(R.id.keyboardView);
        keyboardView.setPreviewEnabled(false);
        keyboardView.setOnKeyboardActionListener(listener);

        setViewVisibility();
        setKeyboardType();
        if (isNumberRandom) {
            setNumberToRandom();
        }

        if(defaultType==type_number){
            keyboardView.setKeyboard(numberKeyboard);
        }else if(defaultType==type_letter){
            keyboardView.setKeyboard(letterKeyboard);
        }else{
            keyboardView.setKeyboard(symbolKeyboard);
        }

        keyboardView.setVisibility(View.VISIBLE);


    }

    private void setNumberToRandom() {
        List<Keyboard.Key> keys = numberKeyboard.getKeys();
        if (numberList == null) {
            numberList = new ArrayList<>();
        } else {
            numberList.clear();
        }
        numberList.add("51#1");
        numberList.add("52#2");
        numberList.add("53#3");
        numberList.add("54#4");
        numberList.add("55#5");
        numberList.add("56#6");
        numberList.add("57#7");
        numberList.add("58#8");
        numberList.add("59#9");
        numberList.add("60#0");

        for (Keyboard.Key key : keys) {
            if (key.label != null && isNumber(key.label.toString())) {
                Random random = new Random();
                int index = random.nextInt(numberList.size());
                String[] split = numberList.get(index).split("#");
                key.codes[0] = Integer.parseInt(split[0], 1);
                key.label = split[1];
                numberList.remove(index);
            }
        }
    }

    private boolean isNumber(String str) {
        String numStr = "1234567890";
        return numStr.contains(str.toLowerCase());
    }
    private boolean isLetter(String str) {
        String letterStr = "qwertyuiopasdfghjklzxcvbnm";
        return letterStr.contains(str.toLowerCase());
    }

    private void setViewVisibility() {
        if (!isNumberEnabled()) {
            tvNumber.setVisibility(View.GONE);
        }
        if (!isLetterEnabled()) {
            tvLetter.setVisibility(View.GONE);
        }
        if (!isSymbolEnabled()) {
            tvSymbol.setVisibility(View.GONE);
        }
    }

    private void setKeyboardType() {
        switch (getCheckType()) {
            case type_number:
                tvNumber.setTextColor(selectedColor);
                tvLetter.setTextColor(unSelectedColor);
                tvSymbol.setTextColor(unSelectedColor);
                break;
            case type_letter:

                tvNumber.setTextColor(unSelectedColor);
                tvLetter.setTextColor(selectedColor);
                tvSymbol.setTextColor(unSelectedColor);
                break;
            case type_symbol:
                tvNumber.setTextColor(unSelectedColor);
                tvLetter.setTextColor(unSelectedColor);
                tvSymbol.setTextColor(selectedColor);
                break;
        }
    }

    private void setGone() {
        keyViewGroup.setVisibility(View.GONE);
    }

    private void changeKey() {
        List<Keyboard.Key> keys = letterKeyboard.getKeys();
        if(isUpper){
            isUpper=!isUpper;
            //大写
            for (Keyboard.Key key:keys){
                if(key.label!=null&&isLetter(key.label.toString())){
                    key.label=key.label.toString().toLowerCase();
                    key.codes[0] = key.codes[0] + 32;
                }
                if(key.codes[0]==Keyboard.KEYCODE_SHIFT){
                    key.icon=ContextCompat.getDrawable(activity,R.drawable.shift_normal);
                }
            }
        }else{
            //小写
            isUpper=!isUpper;
            for (Keyboard.Key key:keys){
                if(key.label!=null&&isLetter(key.label.toString())){
                    key.label=key.label.toString().toUpperCase();
                    key.codes[0] = key.codes[0] - 32;
                }
                if(key.codes[0]==Keyboard.KEYCODE_SHIFT){
                    key.icon=ContextCompat.getDrawable(activity,R.drawable.shift_select);
                }
            }
        }
    }

    private KeyboardView.OnKeyboardActionListener listener = new KeyboardView.OnKeyboardActionListener() {
        @Override
        public void onPress(int primaryCode) {
        }

        @Override
        public void onRelease(int primaryCode) {

        }

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            if (editText == null) {
                return;
            }
            Editable editable = editText.getText();
            int start = editText.getSelectionStart();
            /*if(primaryCode==Keyboard.KEYCODE_CANCEL){
                setGone();
            }else */
            if (primaryCode == Keyboard.KEYCODE_DELETE) {
                if (editable != null && editable.length() > 0) {
                    if(start>0){
                        editable.delete(start-1,start);
                    }
                }
            }else if(primaryCode == Keyboard.KEYCODE_SHIFT){
                // 大小写切换
                changeKey();
                keyboardView.setKeyboard(letterKeyboard);

            } else if(primaryCode==13){
                editable.insert(start,"\n");
            }else {
                editable.insert(start, Character.toString((char) primaryCode));
            }
        }

        @Override
        public void onText(CharSequence text) {

        }

        @Override
        public void swipeLeft() {

        }

        @Override
        public void swipeRight() {

        }

        @Override
        public void swipeDown() {

        }

        @Override
        public void swipeUp() {

        }
    };


    /*数字是否随机*/
    private boolean isNumberRandom;
    /**
     * 键盘类型选中颜色
     */
    private int selectedColor = 0xff66aeff;

    /**
     * 键盘类型未选中颜色
     */
    private int unSelectedColor = Color.BLACK;

    /*0:数字，1：字母，2：符号*/
    private int checkType = 0;
    private int defaultType = 0;
    public static final int type_number = 0;
    public static final int type_letter = 1;
    public static final int type_symbol = 2;

    @IntDef({type_number, type_letter, type_symbol})
    @Retention(RetentionPolicy.SOURCE)
    public @interface type {

    }

    /**
     * 是否启用数字键盘
     */
    private boolean isNumberEnabled = true;

    /**
     * 是否启用字母键盘
     */
    private boolean isLetterEnabled = true;

    /**
     * 是否启用符号键盘
     */
    private boolean isSymbolEnabled = true;

    public int getDefaultType() {
        return defaultType;
    }

    public void setDefaultType(@type int defaultType) {
        this.defaultType = defaultType;
    }

    public int getCheckType() {
        return checkType;
    }

    public void setCheckType(@type int checkType) {
        this.checkType = checkType;
    }

    public boolean isNumberRandom() {
        return isNumberRandom;
    }

    public void setNumberRandom(boolean numberRandom) {
        isNumberRandom = numberRandom;
    }

    public int getSelectedColor() {
        return selectedColor;
    }

    public void setSelectedColor(@ColorInt int selectedColor) {
        this.selectedColor = selectedColor;
    }

    public int getUnSelectedColor() {
        return unSelectedColor;
    }

    public void setUnSelectedColor(int unSelectedColor) {
        this.unSelectedColor = unSelectedColor;
    }

    public boolean isNumberEnabled() {
        return isNumberEnabled;
    }

    public void setNumberEnabled(boolean numberEnabled) {
        isNumberEnabled = numberEnabled;
    }

    public boolean isLetterEnabled() {
        return isLetterEnabled;
    }

    public void setLetterEnabled(boolean letterEnabled) {
        isLetterEnabled = letterEnabled;
    }

    public boolean isSymbolEnabled() {
        return isSymbolEnabled;
    }

    public void setSymbolEnabled(boolean symbolEnabled) {
        isSymbolEnabled = symbolEnabled;
    }

    private void hiddenKeyBoard(Activity activity) {
        if (activity.getCurrentFocus() == null) {
            return;
        }
        ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    private abstract class MyOnClickListener implements View.OnClickListener {
        private static final int MIN_CLICK_DELAY_TIME = 900;
        private long lastClickTime = 0L;

        public MyOnClickListener() {
        }

        public void onClick(View v) {
            long currentTime = Calendar.getInstance().getTimeInMillis();
            if (currentTime - this.lastClickTime > 900L) {
                this.lastClickTime = currentTime;
                this.onNoDoubleClick(v);
            }

        }

        protected abstract void onNoDoubleClick(View view);
    }

}
