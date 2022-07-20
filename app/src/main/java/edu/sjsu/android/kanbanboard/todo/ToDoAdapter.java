package edu.sjsu.android.kanbanboard.todo;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import edu.sjsu.android.kanbanboard.OnGroupClickedListener;
import edu.sjsu.android.kanbanboard.R;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link }.
 * TODO: Replace the implementation with code for your data type.
 */


public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {

    private final List<ToDo> todoList;
    protected OnGroupClickedListener listener;


    public ToDoAdapter(List<ToDo> list,  OnGroupClickedListener listener) {
        todoList = list;
        this.listener = listener;
    }


    /**
     * creates a new ViewHolder object for a row when needed.
     * It will only initialize the ViewHolder, but no data has filled in yet.
     *
     * @param parent   the ViewGroup for the whole list
     * @param viewType taken care of by the recycler view
     * @return a new ViewHolder for a row with the listener
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_to_do, parent, false);

        return new ViewHolder(itemView);

    }
    /**
     * Displays the view holder’s layout (UI) using the corresponding data
     * at the specified position.
     *
     * @param holder   ViewHolder for a row
     * @param position the position of the row initially
     */


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        String title = todoList.get(position).getTitle();
        String description = todoList.get(position).getDescription();
        String date = todoList.get(position).getDate();

        holder.title.setText(title);
        holder.description.setText(description);
        holder.date.setText(date);

    }

    /**
     * Get the number of elements in dataset.
     *
     * @return number of elements in dataset
     */
        @Override
    public int getItemCount() {
        return todoList.size();

       // return 1;
    }

    /**
     * A wrapper around a View that contains
     * the layout (UI) & the listener for each row in the list
     */

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView description;
        TextView date;

        ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            description = view.findViewById(R.id.description);
            date = view.findViewById(R.id.date);

            view.setOnClickListener(v -> listener.setOnGroupClick(getLayoutPosition()));
        }
    }

}