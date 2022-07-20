package edu.sjsu.android.kanbanboard.todo;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import edu.sjsu.android.kanbanboard.R;

public class ToDoDetailFragment extends Fragment {

    private static final String ARG_SELECTED = "selected";
    private ToDo selected;

    public ToDoDetailFragment() {
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

        View myView = inflater.inflate(R.layout.fragment_to_do_detail, container, false);
        Button editToDoButton = myView.findViewById(R.id.editToDo);
        editToDoButton.setOnClickListener(this::editToDoClick);


        if (selected != null) {
            TextView title = myView.findViewById(R.id.title);
            TextView description = myView.findViewById(R.id.description);
            TextView date = myView.findViewById(R.id.date);

            title.setText(selected.getTitle());
            description.setText(selected.getDescription());
            date.setText(selected.getDate());
        }

        return myView;
    }

    public void editToDoClick(View v) {
        Toast.makeText(getActivity(), "Edit", Toast.LENGTH_LONG).show();

        NavController controller = NavHostFragment.findNavController(this);

        Bundle bundle = new Bundle();
        bundle.putParcelable("selected", selected);
        controller.navigate(R.id.action_toDoDetailFragment_to_toDoEditFragment, bundle);
    }


}