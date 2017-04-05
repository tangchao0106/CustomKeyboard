package cim.fuchen.customkeyboard;

import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private KeyboardView keyboardView;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.edittext);

        KeyboardUtil keyboardUtil = new KeyboardUtil(this);
        keyboardUtil.setEditText(editText);


    }

}
