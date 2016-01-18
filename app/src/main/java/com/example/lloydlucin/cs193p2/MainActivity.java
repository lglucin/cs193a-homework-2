package com.example.lloydlucin.cs193p2;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> toDos;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the listview and bind it with the ArrayList of todo's
        ListView toDoList = (ListView) findViewById(R.id.todo_list);
        toDos = new ArrayList<>();
        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                toDos
        );
        toDoList.setAdapter(adapter);

        // When a specific to-Do item is pressed and held, remove it from the listview.
        toDoList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ListView toDoList = (ListView) findViewById(R.id.todo_list);
                toDos.remove(position);
                adapter.notifyDataSetChanged();
                return false;
            }
        });

        // Handles a user adding a to-do after pressing enter on the keyboard
        EditText et = (EditText) findViewById(R.id.newtask);
        et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId== EditorInfo.IME_ACTION_DONE )   )
                {
                    addToList(null);
                    if (v != null) {
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        v.setText("");
                    }
                    return true;
                }
                return false;
            }
        });
    }

    // Handles a user adding a to-do after clicking on the Add button
    public void addToList(View view) {
        EditText et = (EditText) findViewById(R.id.newtask);
        String val = et.getText().toString();
        toDos.add(val);
        adapter.notifyDataSetChanged();
        view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        et.setText("");
    }
}
