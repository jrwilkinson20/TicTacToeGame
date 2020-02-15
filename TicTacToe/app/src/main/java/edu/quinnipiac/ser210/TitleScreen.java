package edu.quinnipiac.ser210;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;



public class TitleScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);
    }
    public void onStartGame(View view){
        EditText messageView = (EditText) findViewById(R.id.name);
        String name = messageView.getText().toString();
        Intent intent = new Intent(this, TicTacToe.class);
        intent.putExtra("name",name);
        startActivity(intent);
    }
}
