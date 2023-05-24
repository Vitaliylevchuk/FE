package com.fbs.fe;

import android.os.*;
import android.widget.*;
import android.view.*;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import com.fbs.fe.util.FileHandler;
import com.fbs.fe.util.Variables;

import java.io.File;

public class StartActivity extends AppCompatActivity {

    public static boolean t = true;
    public int a = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_start);

    final Switch folderSwitch = findViewById(R.id.folder_switch);
    final Switch fileSwitch = findViewById(R.id.file_switch);

    //        seekBar.setOnTouchListener(new View.OnTouchListener(){
    //            @Override
    //            public boolean onTouch(View v, MotionEvent event) {
    //                return true;
    //            }
    //        });
    final EditText pathSelector = (EditText) findViewById(R.id.pathSelector);
    try{
        pathSelector.setText(
            new FileHandler(new File("/storage/emulated/0/com.fbs.fe/files/saves/save0.txt")).getFileContent(2) == null ? "/storage/emulated/0" :
            new FileHandler(new File("/storage/emulated/0/com.fbs.fe/files/saves/save0.txt")).getFileContent(2));
    }catch(ArrayIndexOutOfBoundsException ex){
        ex.printStackTrace();
    }
        final Button button = (Button) findViewById(R.id.button123);
        final Button button1 = (Button) findViewById(R.id.button456);

    button.setOnClickListener(arg0 -> {
        Variables.needFolderInfo = folderSwitch.isActivated();
        Variables.needFileInfo = fileSwitch.isActivated();
        Intent intent = new Intent(StartActivity.this, MainActivity.class);
        startActivity(intent);
        MainActivity.setPath(pathSelector.getText().toString());

        Toast toast = Toast.makeText(StartActivity.this, "File Explorer", Toast.LENGTH_LONG);
        toast.show();
    });

    button1.setOnClickListener(arg0 -> {
        Variables.needFolderInfo = folderSwitch.isChecked();
        Variables.needFileInfo = fileSwitch.isChecked();
        Intent intent = new Intent(StartActivity.this, MainActivity.class);
        MainActivity.setPath(MainActivity.startPath);
        MainActivity.loadTargetFolder();
        startActivity(intent);

        Toast toast = Toast.makeText(StartActivity.this, "File Explorer", Toast.LENGTH_LONG);
        toast.show();
    });


    }

}
