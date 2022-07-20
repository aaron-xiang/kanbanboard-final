package edu.sjsu.android.kanbanboard.credential;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.sjsu.android.kanbanboard.R;
import edu.sjsu.android.kanbanboard.dataprovider.KanbanBoardDB;

public class LoginFragment extends Fragment {
    private final String AUTHORITY = "edu.sjsu.android.kanbanboard.dataprovider";
    private final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/login");

    public static boolean loggedIn = false;
    View myView;

    public LoginFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.fragment_login, container, false);

        Button loginButton = myView.findViewById(R.id.loginbtn);
        loginButton.setOnClickListener(this::onLoginClick);

        Button signupButton = myView.findViewById(R.id.signup1);
        signupButton.setOnClickListener(this::onSignUpClick);

        return myView;
    }

    public void onLoginClick(View view){

        EditText usernameEditText = myView.findViewById(R.id.username1);
        EditText pwEditText =  myView.findViewById(R.id.pwd1);

        String username = usernameEditText.getText().toString();
        String password = pwEditText.getText().toString();

        String[] arg = new String[2];
        arg[0] = username;
        arg[1] = password;

        if(username.equals("") || password.equals("")){
            Toast.makeText(getContext(),"Empty field",Toast.LENGTH_SHORT).show();
        } else {
            Cursor cursor = null;
            try (Cursor c = getContext().getContentResolver().query(CONTENT_URI, null, null, arg, "")) {

                if((c != null) && (c.getCount() > 0)) {
                    NavController controller = NavHostFragment.findNavController(this);
                    Bundle bundle = new Bundle();
                    controller.navigate(R.id.action_loginFragment_to_toDoFragment, bundle);
                }
                else {
                    Toast.makeText(getContext(),"Wrong Username / Password",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void onSignUpClick(View view){

        NavController controller = NavHostFragment.findNavController(this);
        Bundle bundle = new Bundle();
        controller.navigate(R.id.action_loginFragment_to_registerFragment2, bundle);
    }

}