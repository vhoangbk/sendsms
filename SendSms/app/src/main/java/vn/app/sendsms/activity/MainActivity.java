package vn.app.sendsms.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import vn.app.sendsms.R;
import vn.app.sendsms.database.MessageEntity;
import vn.app.sendsms.model.Message;
import vn.app.sendsms.socket.SocketService;

/*
 * start socket service
 * start alarm manager
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnStart).setOnClickListener(this);
        findViewById(R.id.btnStop).setOnClickListener(this);




    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnStart:
                start();
                break;
            case R.id.btnStop:
                stop();
                break;
        }
    }

    private void start(){
        startService(new Intent(MainActivity.this, SocketService.class));
    }

    private void stop(){
        stopService(new Intent(MainActivity.this, SocketService.class));
    }
}
