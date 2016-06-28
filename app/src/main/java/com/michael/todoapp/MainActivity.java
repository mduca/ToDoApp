package com.michael.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> todoItems;
    ArrayAdapter<String> aTodoAdapter;
    ListView lvItems;
    EditText etEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateArrayItems();
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(aTodoAdapter);
        etEditText = (EditText) findViewById(R.id.etEditText);
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                todoItems.remove(position);
                aTodoAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> av, View v, int pos, long id) {
                String text = (lvItems.getItemAtPosition(pos).toString());
                launchEditView(text, pos);
            }
        });
    }

    public void populateArrayItems() {
        readItems();
        aTodoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoItems);
    }

    private void readItems() {
        File filesDir = getFilesDir();
        File file = new File(filesDir, "todo.txt");
        try {
            todoItems = new ArrayList<String>(FileUtils.readLines(file));
        } catch (IOException e) {

        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File file = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(file, todoItems);
        } catch (IOException e) {

        }
    }

    public void onAddItem(View view) {
        aTodoAdapter.add(etEditText.getText().toString());
        etEditText.setText("");
        writeItems();
    }

    private final int REQUEST_CODE = 20;

    public void launchEditView(String text, int pos ) {
        Intent i = new Intent(this, EditItemActivity.class);
        i.putExtra("text", text);
        i.putExtra("pos", pos);
        startActivityForResult(i, REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            String text = data.getExtras().getString("text");
            int pos = data.getExtras().getInt("pos", 0);
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
            todoItems.set(pos, text);
            aTodoAdapter.notifyDataSetChanged();
            writeItems();
        }
    }
}
