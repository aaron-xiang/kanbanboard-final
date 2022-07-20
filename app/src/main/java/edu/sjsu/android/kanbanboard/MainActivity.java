package edu.sjsu.android.kanbanboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import edu.sjsu.android.kanbanboard.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private final String AUTHORITY = "edu.sjsu.android.kanbanboard.dataprovider";
    private final Uri CONTENT_URI = Uri.parse("content://edu.sjsu.android.kanbanboard.dataprovider");
    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        setContentView(view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.logout).setVisible(false);
        super.onPrepareOptionsMenu(menu);
        return true;
    }

}