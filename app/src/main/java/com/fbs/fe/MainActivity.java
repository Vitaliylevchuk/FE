package com.fbs.fe;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.*;
import java.text.DecimalFormat;
import android.provider.Settings;
import android.view.*;
import android.widget.*;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
//import android.content.Context;
//import android.Manifest;
//import com.fbs.app.SaveFileHandler;
//import android.view.animation.Animation;
//import android.view.animation.AnimationUtils;
//import androidx.core.content.ContextCompat;
//import android.content.pm.PackageManager;
//import java.time.*;
//import java.time.format.*;
//import java.util.Arrays;

@SuppressWarnings("all")
public class MainActivity extends AppCompatActivity {

    private static final int STORAGE_PERMISSION_CODE = 360;
    public static String[] names;
    public static String startPath = "/storage/emulated/0";
    public String d;
    public static int progress = 0;
    public byte animSeted = 2;
    
    public static int themeBckgColor;
    public static int themeFontColor;
    public int listLength;
    public static FileHandler targetFolder;
    public static SeekBar seekBar;
    public static Themes theme;
    
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //
        if(!Variables.permissionsCheckedSuccessful) {
            checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE);
        }

        final FileHandler f = new FileHandler("/storage/emulated/0");
        f.mkdir("com.mvlcomp.rpg_v");
        f.setPath(f.getPath()+"/com.mvlcomp.rpg_v");
        f.mkdir("files");
        f.setPath(f.getPath()+"/files");
        f.mkdir("saves");
        f.setPath(f.getPath()+"/saves");
        if(f.getFromTargetLib() == null){
            f.mkfile("save0.txt");
        }
        final FileHandler saveFile = new FileHandler(new File(f.getPath() + "/save0.txt"));
        
        switch(saveFile.getFileContent(1)){
            case "R":{
                theme = Themes.RED;
                break;
            }
            case "G":{
                theme = Themes.GREEN;
                break;
            }
            default:{
                theme = Themes.BLUE;
            }
        }
        
//        switch(saveFile.getFileContent(2)){
//            case "":{
//                path = startPath; 
//                break;
//            }
//            default:{
//                path = saveFile.getFileContent(2);
//            }
//        }
        
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        
        switch(theme){
            case BLUE:{
                themeBckgColor = R.color.viewBckgColor_blue;
                themeFontColor = R.color.viewFontColor_blue;
                break;
            }
            case GREEN:{
                themeBckgColor = R.color.viewBckgColor_green;
                themeFontColor = R.color.viewFontColor_green;
                break;
            }
            case RED:{
                themeBckgColor = R.color.viewBckgColor_red;
                themeFontColor = R.color.viewFontColor_red;
                break;
            }
            default:{
                themeBckgColor = R.color.viewBckgColor_grey;
                themeFontColor = R.color.viewColorBackground;
            }
        }
        themeFontColor = R.color.white;
//   r     final Animation screenSlideUp = AnimationUtils.loadAnimation(getApplication(), R.anim.screen_slide_up);
//   r     final Animation screenSlideDown = AnimationUtils.loadAnimation(getApplication(), R.anim.screen_slide_down);
//   r     final Animation screenSlideLeft = AnimationUtils.loadAnimation(getApplication(), R.anim.screen_slide_left);
//   r     final Animation screenSlideRight = AnimationUtils.loadAnimation(getApplication(), R.anim.screen_slide_right);
//   r     final Animation invisibilityIn = AnimationUtils.loadAnimation(getApplication(), R.anim.invisibility_in);
//   r 	final Animation invisibilityOut = AnimationUtils.loadAnimation(getApplication(), R.anim.invisibility_out);
//   r 	final Animation slideInLeft = AnimationUtils.loadAnimation(getApplication(), R.anim.slide_in_left);
//   r 	final Animation slideInRight = AnimationUtils.loadAnimation(getApplication(), R.anim.slide_in_right);
//   r 	final Animation slideOutLeft = AnimationUtils.loadAnimation(getApplication(), R.anim.slide_out_left);
//   r 	final Animation slideOutRight = AnimationUtils.loadAnimation(getApplication(), R.anim.slide_out_right);
        
		final RelativeLayout layout = (RelativeLayout) findViewById(R.id.layout0);
        final LinearLayout sideView = (LinearLayout) findViewById(R.id.sideView);
        final LinearLayout topView = (LinearLayout) findViewById(R.id.topPlane);
        final EditText pathView = (EditText) findViewById(R.id.pathView);
        final ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView);
        final LinearLayout folderView = (LinearLayout) findViewById(R.id.folderView);
        final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 70);
        final RelativeLayout.LayoutParams folderParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 70);
        final TextView findButton = (TextView) findViewById(R.id.findButton);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        
        pathView.setText(Variables.path);
        layout.setBackgroundResource(R.color.white);
        findButton.setHintTextColor(themeFontColor);
        pathView.setHintTextColor(themeFontColor);
        scrollView.setBackgroundResource(R.color.viewBckgColor_grey);
        folderView.setBackgroundResource(R.color.viewBckgColor_grey);
        
        loadTargetFolder();
        
        layoutParams.setMargins(10,5,10,0);
        
//        final File appFolder = new File(getApplication().getFilesDir().toString());
        if(folderView.getChildCount() > 0){
            folderView.removeAllViews();
        }
        
        try{
            folderLength = targetFolder.getFromTargetLib().length;
        }
        catch(NullPointerException npe){
            npe.printStackTrace();
            folderLength = 0;
        }
        
        TextView undoView = new TextView(this);
        undoView.setTextSize(18);
        undoView.setLayoutParams(layoutParams);
        undoView.setWidth(35);
        undoView.setText("  ..");
        undoView.setBackgroundResource(R.drawable.text_view);
        System.out.println(themeFontColor);
        System.out.println(R.color.viewFontColor_red);
        undoView.setHintTextColor(R.color.viewFontColor_green);
        
        undoView.setOnClickListener(new View.OnClickListener(){
            @Override
                public void onClick(View arg0) {
                    try{
                        Variables.path = targetFolder.oneStageUndo();
                        if(hasCharIA(Variables.path, '/')[0] < 3){
                            Variables.path = startPath;
                        }
                        loadTargetFolder();
                        save(saveFile, theme == Themes.BLUE ? 'B' : theme == Themes.GREEN ? 'G' : theme == Themes.RED ? 'R' : 'B', targetFolder.getPath());
                        finish();
                        startActivity(getIntent());
                        //for(int g = 0; g < folderLength; g++){
                       //     seekBar.setProgress(g);
                       // }
                       Variables.isFolderContentLoaded = false;
                       Variables.isMainCreated = false;

                    }
                    catch(NullPointerException npe){
                        npe.printStackTrace();
                    }
                }
                        
        });
        
        folderView.addView(undoView);
        System.out.println(folderLength);
        if(!Variables.isMainCreated){
            Variables.names = new String[folderLength];
            names = Variables.names;
            for(int i = 0;i < folderLength;i++){
                names[i] = targetFolder.getEditedName(i, folderLength, targetFolder)[0];
            }
           // Arrays.sort(names);
        }
            
        for(int i = 0; i < folderLength; i++){
            System.out.println("i = "+i);
            
            TextView tv = new TextView(MainActivity.this);
            RelativeLayout rl = new RelativeLayout(MainActivity.this);
            rl.setLayoutParams(folderParams);
            
            configuringTextView(tv, layoutParams, saveFile, themeFontColor, i, targetFolder.getEditedName(i, folderLength, targetFolder)[1]);
            tv.setText(names[i]);
            
            rl.addView(tv);
            folderView.addView(rl);
            progress = i;
        }
        Variables.isMainCreated = true;
        System.out.println("ok");
            
//            LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            for (int i = 0; i < 10; i++) {
//                
//            }
        
//        path = appFolder.getAbsolutePath().toString();
        
        findButton.setOnClickListener(new View.OnClickListener(){
            @Override
                public void onClick(View arg0) {
                    Variables.path = String.valueOf(pathView.getText());
                    if(hasCharIA(Variables.path, '/')[0] < 3){
                            Variables.path = startPath;
                    }
                    FileHandler folder = new FileHandler(String.valueOf(pathView.getText()));
                    save(saveFile, 'R', Variables.path);
                    Toast toast = Toast.makeText(MainActivity.this, "Save", Toast.LENGTH_LONG);
	            	toast.show();
                    recreate();
                }
        });
        
    }

    public void checkPermission(String permission, int requestCode){
        if(ContextCompat.checkSelfPermission(MainActivity.this, permission) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode);
            Variables.permissionsChecked = true;
            Variables.permissionsCheckedSuccessful = true;
            //ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);
        }
        else{
            if (!Variables.permissionsChecked) {
                Toast.makeText(MainActivity.this, "Permission granded, all ok", Toast.LENGTH_LONG).show();
                Variables.permissionsChecked = true;
            }
        }
    }
    
    public boolean hasCharB(String str, char ... args){
        boolean returnB = false;
        int usedElements = 0;
        for(int i = 0; i < str.toCharArray().length; i++){
            if(usedElements >= args.length){
                returnB = true;
                break;
            }
            else{
                for(int j = usedElements; j < args.length; j++){
                    if(str.toCharArray()[i] == args[j]){
                        usedElements += 1;
                    }
                }
            }
        }
        return returnB;
        
    }
    public int[] hasCharIA(String str, char ... args){
        int[] returnI = new int[args.length];
        for(int i = 0; i < args.length; i ++){
            returnI[i] = 0;
        }
        for(int i = 0; i < str.toCharArray().length; i++){
            for(int j = 0; j < args.length; j ++){
                if(str.toCharArray()[i] == args[j]){
                    returnI[j] += 1;
                }
            }
        }
        return returnI;
    }
    
    public int id;
    
    public void configuringTextView(TextView folderContentObj, LinearLayout.LayoutParams layoutParams, FileHandler saveFile, int themeFontColor, int index, String name){
        folderContentObj.setTextSize(18);
        folderContentObj.setLayoutParams(layoutParams);
        folderContentObj.setBackgroundResource(R.drawable.text_view);
        folderContentObj.setHintTextColor(themeFontColor);
        Variables.isFolderContentLoaded = false;
        MainActivity.seekBar.setMax(0);
        String namee = "";
        boolean getingName = false;
//        for(int i = 0; i < name.toCharArray().length; i ++){
//            if(name.toCharArray()[i] != '/' && name.toCharArray()[i] != '<' && name.toCharArray()[i] != ' '){
//                namee += String.valueOf(name.toCharArray()[i]);
//                getingName = true;
//                System.out.print("#");
//            }
//            else if(getingName){
//                System.out.println("namee:");
//                System.out.println(namee);
//                break;
//            }
//        }
//        for(int i = 0; i < folderLength; i++){
//            if(targetFolder.getFromTargetLib()[i].getName().equals(namee)){
//                id = i;
//                System.out.println("\nid inited = " + id);
//            }
//        }
        folderContentObj.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                try{
                   Variables.path += "/" + targetFolder.getFromTargetLib()[index].getName();
                   int fl = targetFolder.getFromTargetLib()[index].listFiles().length;
                  // save(saveFile, theme == Themes.BLUE ? 'B' : theme == Themes.GREEN ? 'G' : theme == Themes.RED ? 'R' : 'B', path);
                    Thread th = new Thread(){
                        @Override
                            public void run() {
                                super.run();
                                loadTargetFolder();
                                MainActivity.seekBar.setMax(fl);;
                                MainActivity.seekBar.setProgress(0);
                                names = new String[fl];
                                int i = 0;
                                while (i < MainActivity.seekBar.getMax()){
                                    names[i] = targetFolder.getEditedName(i, fl, targetFolder)[0];
                                    MainActivity.seekBar.setProgress(MainActivity.seekBar.getProgress() + i);
                                    i ++;
                                }
                                MainActivity.seekBar.setProgress(0);
                                Variables.isMainCreated = true;
                                Variables.isFolderContentLoaded = true;
                                finishActivity(R.layout.activity_main);
                                startActivity(getIntent());
                            }
                    };
                    th.start();
                }
                catch(NullPointerException npe){
                    npe.printStackTrace();
                }
            }
        });
    }
    
    public static void setPath(String path0){
        Variables.path = path0;
    }
    
    public static void loadTargetFolder(){
        targetFolder = new FileHandler(Variables.path);
    }
    
    public static int folderLength;
    public static final DecimalFormat df = new DecimalFormat("0.00");
    
    public void save(FileHandler saveFile, char theme, String path){
        String inputData;
        inputData = String.valueOf(theme) + "\n" + path;
        saveFile.rewriteFile(inputData);
    }
    
    public static boolean beInPause = false;
    
    @Override
    protected void onPause() {
        super.onPause();
    }
        
    @Override
    protected void onResume() {
        super.onResume();
        if(Variables.path == null || Variables.path == ""){
            Variables.path = startPath;
        }
    }
        
}
