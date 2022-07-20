package edu.sjsu.android.kanbanboard.credential;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

import edu.sjsu.android.kanbanboard.R;
import edu.sjsu.android.kanbanboard.dataprovider.KanbanBoardDB;

public class RegisterFragment extends Fragment {

    private final String AUTHORITY = "edu.sjsu.android.kanbanboard.dataprovider";
    private final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/register");
    View myView;

    public RegisterFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.fragment_register, container, false);

        Button loginButton = myView.findViewById(R.id.login);
        loginButton.setOnClickListener(this::onLoginClick);

        Button signupButton = myView.findViewById(R.id.signup);
        signupButton.setOnClickListener(this::insertUserData);

        return myView;
    }

    public void onLoginClick(View view){

        NavController controller = NavHostFragment.findNavController(this);
        Bundle bundle = new Bundle();
        controller.navigate(R.id.action_registerFragment2_to_loginFragment, bundle);
    }

    public void insertUserData(View view) {

        ContentValues values = new ContentValues();

        EditText emailEditText = myView.findViewById(R.id.email);
        EditText usernameEditText = myView.findViewById(R.id.username);
        EditText pwEditText = myView.findViewById(R.id.pwd);

        String email = emailEditText.getText().toString();
        String username = usernameEditText.getText().toString();
        String password = pwEditText.getText().toString();

        if(!checkEmpty(email, username, password) ) {
            Toast.makeText(getContext(), "Empty field", Toast.LENGTH_SHORT).show();
            return;
        } else if (!checkValidEmail(email)) {
            Toast.makeText(getContext(), "Invalid Email", Toast.LENGTH_SHORT).show();
            emailEditText.setText("");
            usernameEditText.setText("");
            pwEditText.setText("");
            return;
        } else {
            values.put("email",email);
            values.put("username",username);
            values.put("password",password);

            if (getContext().getContentResolver().insert(CONTENT_URI, values) != null)
                Toast.makeText(getContext(), "User added", Toast.LENGTH_SHORT).show();

            emailEditText.setText("");
            usernameEditText.setText("");
            pwEditText.setText("");
        }
    }

    public boolean checkEmpty(String email, String username,String password){
        if(email != null && !email.trim().isEmpty()
                && username != null && !username.trim().isEmpty()
                && password != null && !password.trim().isEmpty()){
            return true;
        } else {
            return false;
        }
    }

    public boolean checkValidEmail(String email){
        String regexPattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        return Pattern.compile(regexPattern).matcher(email).matches();
    }

}
