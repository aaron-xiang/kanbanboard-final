package edu.sjsu.android.kanbanboard.todo;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

import edu.sjsu.android.kanbanboard.R;


public class NewToDoFragment extends Fragment {

    private final String AUTHORITY = "edu.sjsu.android.kanbanboard.dataprovider";
    private final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/todo");
    View myView;

    public NewToDoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView =  inflater.inflate(R.layout.fragment_to_do_new, container, false);

        Button saveToDo = myView.findViewById(R.id.saveToDo);
        saveToDo.setOnClickListener(this::saveToDoClick);

        return myView;

    }

    public void saveToDoClick(View view) {

        ContentValues values = new ContentValues();

        EditText EditTextTitle = myView.findViewById(R.id.title);
        EditText  EditTextDescription = myView.findViewById(R.id.description);
        EditText  EditTextDate = myView.findViewById(R.id.date);

        String title  = EditTextTitle.getText().toString();
        String description = EditTextDescription.getText().toString();
        String date = EditTextDate.getText().toString();

        values.put("title", title);
        values.put("description", description);
        values.put("date", date);

        if(!checkEmpty(title, description, date) ) {
            Toast.makeText(getContext(), "Empty field", Toast.LENGTH_SHORT).show();
            return;
        }
        else {

            if (getContext().getContentResolver().insert(CONTENT_URI, values) != null)  {

                NavController controller = NavHostFragment.findNavController(this);
                Bundle bundle = new Bundle();
                controller.navigate(R.id.action_newToDoFragment_to_toDoFragment, bundle);
            }
            else  {
                Toast.makeText(getContext(), "Todo saving failed!", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public boolean checkEmpty(String title, String description,String date){
        if((title != null && !title.trim().isEmpty()) && (description != null && !description.trim().isEmpty()) && (date != null && !date.trim().isEmpty())){
            return true;
        } else {
            return false;
        }
    }
}