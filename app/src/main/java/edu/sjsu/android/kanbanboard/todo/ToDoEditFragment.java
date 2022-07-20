package edu.sjsu.android.kanbanboard.todo;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import edu.sjsu.android.kanbanboard.R;

public class ToDoEditFragment extends Fragment {

    private final String AUTHORITY = "edu.sjsu.android.kanbanboard.dataprovider";
    private final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/todo");
    private static final String ARG_SELECTED = "selected";
    private ToDo selected;
    View myView;
    int _id;


    public ToDoEditFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("selected"))
            selected = getArguments().getParcelable(ARG_SELECTED);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.fragment_to_do_edit, container, false);
        Button myButton = myView.findViewById(R.id.editToDo);
        myButton.setOnClickListener(this::editToDoClick);

        if (selected != null) {
            EditText title = myView.findViewById(R.id.title);
            EditText description = myView.findViewById(R.id.description);
            EditText date = myView.findViewById(R.id.date);

            title.setText(selected.getTitle());
            description.setText(selected.getDescription());
            date.setText(selected.getDate());
            _id = selected.getId();
        }

        return myView;

    }

    public void editToDoClick(View v) {

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

        // Toast message if successfully inserted

        if(!checkEmpty(title, description, date) ) {
            Toast.makeText(getContext(), "Empty field", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            String selectionArg = "_id='" +_id+ "'" ;
            if (getContext().getContentResolver().update(CONTENT_URI, values,selectionArg,null) > 0)  {

                Toast.makeText(getContext(), "Todo editing successfull!", Toast.LENGTH_SHORT).show();
                NavController controller = NavHostFragment.findNavController(this);
                Bundle bundle = new Bundle();
                controller.navigate(R.id.action_toDoEditFragment_to_toDoFragment, bundle);
            }
            else  {
                Toast.makeText(getContext(), "Todo editing failed!", Toast.LENGTH_SHORT).show();
                Toast.makeText(getContext(), "Todo editing failed!" + _id , Toast.LENGTH_SHORT).show();
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