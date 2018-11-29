package cim.fuchen.customkeyboard;

import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private KeyboardView keyboardView;
    private EditText editText;
    Button bt_hide;
    Button bt_show;
    KeyboardUtil keyboardUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.edittext);
        bt_hide = (Button) findViewById(R.id.bt_hide);
        bt_show = (Button) findViewById(R.id.bt_show);

        keyboardUtil = new KeyboardUtil(this);
        keyboardUtil.setEditText(editText);


        bt_hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hide();
            }
        });


        bt_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show();
            }
        });


    }

    private void show() {
        keyboardUtil.setShowZmOrSz(false);
    }

    private void hide() {
        keyboardUtil.setShowZmOrSz(true);


    }

}
