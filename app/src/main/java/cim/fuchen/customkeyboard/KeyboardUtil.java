package cim.fuchen.customkeyboard;

import android.app.Activity;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.SystemClock;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by wangshuo01 on 2017/4/2.
 */

public class KeyboardUtil {

    public static final int DIGITAL = 1;
    public static final int LETTER = 2;
    public static final int SYMBOL = 3;

    public Activity mContext;
    private KeyboardView keyboardView;
    //    private Keyboard digitalKeyboard;
    private Keyboard letterKeyboard;
    //    private Keyboard symbolKeyboard;
    private EditText editText;

    private boolean isUpper = false;// 是否大写
    private int curType = LETTER;

    private boolean isRandomDigital = false;
    private boolean isRandomLetter = false;
    private boolean isRandomsymbol = false;
    private boolean showzm = false;


    public KeyboardUtil(Activity context) {
        this(context, false, false);
    }

    public void setShowZmOrSz(boolean sz) {
        if (sz) {
            letterKeyboard = new Keyboard(mContext, R.xml.i_letter_keyboard2);
            randomDigitalKey(letterKeyboard);
            switchLettersCase();

            keyboardView.setKeyboard(letterKeyboard);
            keyboardView.setPreviewEnabled(false);
            keyboardView.setOnKeyboardActionListener(actionListener);
        } else {
            letterKeyboard = new Keyboard(mContext, R.xml.i_letter_keyboard);
            randomDigitalKey(letterKeyboard);
            switchLettersCase();

            keyboardView.setKeyboard(letterKeyboard);
            keyboardView.setPreviewEnabled(false);
            keyboardView.setOnKeyboardActionListener(actionListener);
        }
    }


    public KeyboardUtil(Activity context, boolean isRandomDigital, boolean isRandomLetter) {
        this.mContext = context;
        keyboardView = (KeyboardView) context.findViewById(R.id.keyboardview);
        letterKeyboard = new Keyboard(context, R.xml.i_letter_keyboard2);
        this.isRandomDigital = isRandomDigital;
        this.isRandomLetter = isRandomLetter;

        randomDigitalKey(letterKeyboard);
        switchLettersCase();

        keyboardView.setKeyboard(letterKeyboard);
        keyboardView.setPreviewEnabled(false);
        keyboardView.setOnKeyboardActionListener(actionListener);
    }

    private KeyboardView.OnKeyboardActionListener actionListener = new KeyboardView.OnKeyboardActionListener() {
        @Override
        public void onPress(int primaryCode) {

        }

        @Override
        public void onRelease(int primaryCode) {

        }

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            if (curType == DIGITAL) {
                if (primaryCode == 97) {//字母
                    curType = LETTER;
                    if (isUpper) switchLettersCase();
                    randomDigitalKey(letterKeyboard);
                    randomLetterKey(letterKeyboard);
                    keyboardView.setKeyboard(letterKeyboard);
                } else if (primaryCode == Keyboard.KEYCODE_DELETE) {//回退
                    fallBack();
                } else if (primaryCode == 35) {//符号
                    curType = SYMBOL;
//                    keyboardView.setKeyboard(symbolKeyboard);
                } else {
                    intputContent(primaryCode);
                }

            } else if (curType == LETTER) {
                if (primaryCode == 46) {//数字
                    curType = DIGITAL;
//                    randomDigitalKey(digitalKeyboard);
//                    keyboardView.setKeyboard(digitalKeyboard);
                } else if (primaryCode == 35) {//符号
                    curType = SYMBOL;
//                    keyboardView.setKeyboard(symbolKeyboard);
                } else if (primaryCode == Keyboard.KEYCODE_DELETE) {//回退
                    fallBack();
                } else if (primaryCode == Keyboard.KEYCODE_SHIFT) {//切换大小写
                    switchLettersCase();
                } else {
                    intputContent(primaryCode);
                }
            } else {
                if (primaryCode == 46) {//数字
                    curType = DIGITAL;
//                    randomDigitalKey(digitalKeyboard);
//                    keyboardView.setKeyboard(digitalKeyboard);
                } else if (primaryCode == 97) {//字母
                    curType = LETTER;
                    if (isUpper) switchLettersCase();
                    randomDigitalKey(letterKeyboard);
                    randomLetterKey(letterKeyboard);
                    keyboardView.setKeyboard(letterKeyboard);
                } else if (primaryCode == Keyboard.KEYCODE_DELETE) {//回退
                    fallBack();
                } else {
                    intputContent(primaryCode);
                }
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

    /**
     * 回退
     */
    private void fallBack() {
        if (editText != null) {
            Editable editable = editText.getText();
            int start = editText.getSelectionStart();
            if (editable != null && editable.length() > 0) {
                if (start > 0) {
                    editable.delete(start - 1, start);
                }
            }
        }

    }

    /**
     * 输入内容
     */
    private void intputContent(int primaryCode) {
        if (editText != null) {
            Editable editable = editText.getText();
            int start = editText.getSelectionStart();
            editable.insert(start, Character.toString((char) primaryCode));
        }
    }

    public void setEditText(EditText editText) {
        this.editText = editText;
    }

    /**
     * 切换字母大小写
     */
    private void switchLettersCase() {
        List<Keyboard.Key> keylist = letterKeyboard.getKeys();
        if (isUpper) {//大写切换小写
//            isUpper = false;
            for (Keyboard.Key key : keylist) {
                if (key.label != null && isword(key.label.toString())) {
                    key.label = key.label.toString().toLowerCase();
                    key.codes[0] = key.codes[0] + 32;
                }
            }
        } else {//小写切换大写
//            isUpper = true;
            for (Keyboard.Key key : keylist) {
                if (key.label != null && isword(key.label.toString())) {
                    key.label = key.label.toString().toUpperCase();
                    key.codes[0] = key.codes[0] - 32;
                }
            }
        }
        keyboardView.setKeyboard(letterKeyboard);
    }

    private boolean isword(String str) {
        String wordstr = "abcdefghijklmnopqrstuvwxyz";
        if (wordstr.indexOf(str.toLowerCase()) > -1) {
            return true;
        }
        return false;
    }


    public void showKeyboard() {
        int visibility = keyboardView.getVisibility();
        if (visibility == View.GONE || visibility == View.INVISIBLE) {
            keyboardView.setVisibility(View.VISIBLE);
        }
    }

    public void hideKeyboard() {
        int visibility = keyboardView.getVisibility();
        if (visibility == View.VISIBLE) {
            keyboardView.setVisibility(View.GONE);
        }
    }

    /**
     * 数字键盘随机
     */
    private void randomDigitalKey(Keyboard keyboard) {

        if (!isRandomDigital) return;

        List<Keyboard.Key> keys = keyboard.getKeys();
        List<Keyboard.Key> newKeys = new ArrayList<>();
        for (Keyboard.Key key : keys) {
            if (key.label != null && "0123456789".indexOf(key.label.toString()) != -1) {
                newKeys.add(key);
            }
        }
        List<CodeAndLabel> temp = new ArrayList<>();
        int count = newKeys.size();
        for (int i = 0; i < count; i++) {
            temp.add(new CodeAndLabel(48 + i, String.valueOf(i)));
        }

        Random rand = new SecureRandom();
        rand.setSeed(SystemClock.currentThreadTimeMillis());

        List<CodeAndLabel> result = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            int num = rand.nextInt(count - i);
            CodeAndLabel model = temp.get(num);
            result.add(model);
            temp.remove(num);
        }

        for (int i = 0; i < newKeys.size(); i++) {
            Keyboard.Key key = newKeys.get(i);
            CodeAndLabel codeAndLabel = result.get(i);
            key.label = codeAndLabel.label;
            key.codes[0] = codeAndLabel.code;
        }
    }

    /**
     * 字母键盘随机
     */
    private void randomLetterKey(Keyboard keyboard) {
        if (!isRandomLetter) return;
        List<Keyboard.Key> keys = keyboard.getKeys();
        List<Keyboard.Key> newKeys = new ArrayList<>();
        for (Keyboard.Key key : keys) {
            if (key.label != null && "abcdefghijklmnopqrstuvwxyz".indexOf(key.label.toString()) != -1) {
                newKeys.add(key);
            }
        }
        List<CodeAndLabel> temp = new ArrayList<>();
        int count = newKeys.size();
        for (int i = 0; i < count; i++) {
            temp.add(new CodeAndLabel(97 + i, Character.toString((char) (97 + i))));
        }

        Random rand = new SecureRandom();
        rand.setSeed(SystemClock.currentThreadTimeMillis());

        List<CodeAndLabel> result = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            int num = rand.nextInt(count - i);
            CodeAndLabel model = temp.get(num);
            result.add(model);
            temp.remove(num);
        }

        for (int i = 0; i < newKeys.size(); i++) {
            Keyboard.Key key = newKeys.get(i);
            CodeAndLabel codeAndLabel = result.get(i);
            key.label = codeAndLabel.label;
            key.codes[0] = codeAndLabel.code;
        }
    }

}
