package edu.sjsu.android.kanbanboard.todo;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import edu.sjsu.android.kanbanboard.R;
import edu.sjsu.android.kanbanboard.credential.LoginFragment;

/**
 * A fragment representing a list of Items.
 */
public class ToDoFragment extends Fragment {

    private final Uri CONTENT_URI = Uri.parse("content://edu.sjsu.android.kanbanboard.dataprovider/todo");

    // TODO: Customize parameters
    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;

    ArrayList<ToDo> todoList;
    ToDoAdapter adapter;
    RecyclerView recyclerView;
    ToDo selected;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */

    public ToDoFragment() {

    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ToDoFragment newInstance(int columnCount) {
        ToDoFragment fragment = new ToDoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater ){
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        if(LoginFragment.loggedIn) {
            menu.findItem(R.id.logout).setVisible(true);
        }else {
            menu.findItem(R.id.logout).setVisible(false);
        }
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.logout){
            Toast.makeText(getContext(),"Logout Successful",Toast.LENGTH_SHORT).show();

            NavController controller = NavHostFragment.findNavController(this);
            Bundle bundle = new Bundle();
            controller.navigate(R.id.action_toDoFragment_to_loginFragment, bundle);

        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_to_do_list, container, false);
        todoList = new ArrayList<>();

        try (Cursor c = getActivity().getApplicationContext().getContentResolver().query(CONTENT_URI, null, null, null, "title")) {
           if(((c != null) && (c.getCount() > 0)))  {
                if ( c.moveToFirst()) {
                    do {
                        int id = c.getInt(0);
                        String title = c.getString(1);
                        String description = c.getString(2);
                        String date = c.getString(3);

                        ToDo todo = new ToDo(id,title, description, date);

                        todoList.add(todo);
                    } while (c.moveToNext());
                }
           }
        }

        recyclerView = view.findViewById(R.id.recyclerviewList);
        adapter = new ToDoAdapter(todoList ,  this::groupClicked);

        recyclerView.setAdapter(adapter);

        FloatingActionButton addToDoButton = view.findViewById(R.id.addToDo);
        addToDoButton.setOnClickListener(this::addToDoClick);

        swipeToDelete();

        return view;
    }

    private void goDetail() {
        NavController controller = NavHostFragment.findNavController(this);
        // Data in the a bundle for navigation
        Bundle bundle = new Bundle();
        bundle.putParcelable("selected", selected);
        controller.navigate(R.id.action_toDoFragment_to_toDoDetailFragment, bundle);
    }

    public void groupClicked(int position) {
        selected = todoList.get(position);
        goDetail();

    }

    public void addToDoClick(View view) {
        NavController controller = NavHostFragment.findNavController(this);
        Bundle bundle = new Bundle();
        controller.navigate(R.id.action_toDoFragment_to_newToDoFragment, bundle);
    }

    public void swipeToDelete() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView,
                                          @NonNull RecyclerView.ViewHolder viewHolder,
                                          @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder,
                                         int swipeDir) {
                        int position = viewHolder.getLayoutPosition();

                        int deletingToDoId = todoList.get(position).getId();
                        String whereClause = "_id='" + deletingToDoId + "'" ;

                        if (getContext().getContentResolver().delete(CONTENT_URI, whereClause,null) > 0)  {
                            todoList.remove(position);
                            adapter.notifyItemRemoved(position);
                        }
                    }
                };

        ItemTouchHelper itemTouch = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouch.attachToRecyclerView(recyclerView);

    }
}