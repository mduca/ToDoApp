package com.michael.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    EditText editValue;
    String text;
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        text = getIntent().getStringExtra("text");
        pos = getIntent().getIntExtra("pos", 0);


        editValue = (EditText) findViewById(R.id.editText);
        editValue.setText(text);
        editValue.setSelection(editValue.getText().length());

    }

    public void onSave(View v) {

        Intent data = new Intent();
        data.putExtra("text", editValue.getText().toString());
        data.putExtra("pos", pos);
        setResult(RESULT_OK, data);
        this.finish();
    }
}
