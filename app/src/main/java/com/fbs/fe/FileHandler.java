package com.fbs.fe;

import android.icu.text.DecimalFormat;
import com.fbs.util.LineGetter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileHandler {
    public static File[] objectsInFolder;
    public static String path;
    public static File file;
    public final String absPath;
    public final String name;
    public final String parent;
    public final boolean hiden;
    public final boolean canRead;
    public final boolean canWrite;
    public static final DecimalFormat df = new DecimalFormat("0.00");
    public static String[] returnS = new String[2];

    public FileHandler(String path){
        this.path = path;
        this.file = new File(path);
        absPath = file.getAbsolutePath();
        name = file.getName();
        parent = file.getParent();
        hiden = file.isHidden();
        canRead = file.canRead();
        canWrite = file.canWrite();
    }

    public FileHandler(File file){
        this.file = file;
        absPath = file.getAbsolutePath();
        name = file.getName();
        parent = file.getParent();
        hiden = file.isHidden();
        canRead = file.canRead();
        canWrite = file.canWrite();
    }

    public String getFileContent(int line){
        LineGetter lineGetter = new LineGetter();
        return lineGetter.readFromLine(line, file);
    }

    public void rewriteFile(String text){
        try{
            FileWriter writer = new FileWriter(file);
            writer.write(text);
            writer.flush();
            writer.close();
        }
        catch (IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    public String getFileContent(){

        String cont = "";

        try{
            FileReader reader = new FileReader(file);

            char [] b = new char[(int)file.length()];
            reader.read(b);

            for(char c : b){
                cont += c;
            }

            reader.close();

        }catch (IOException ex){
            System.out.println(ex.getMessage());
        }
        return cont;
    }

    public boolean rename(String name){
        File fileNameOld = new File(file.getAbsolutePath());
        File fileNameNew;
        String pathNew = "";
        char[] fileAbsNameArray = fileNameOld.getAbsolutePath().toCharArray();
        int newArrayLength = fileAbsNameArray.length;
        for(int i = 0; i < fileAbsNameArray.length; i++){
            if(fileAbsNameArray[fileAbsNameArray.length - i] != '/'){
                newArrayLength -= 1;
            }
            else{
                break;
            }
        }
        for (int i = 0; i < newArrayLength; i++){
            pathNew += String.valueOf(fileAbsNameArray[i]);
        }
        fileNameNew = new File(pathNew + name);
        if(file.renameTo(fileNameNew)){
            return true;
        }
        else{
            return false;
        }
    }

    public File[] getRoots(){
        return File.listRoots();
    }

    public String[] getEditedName(int index, int folderLength, FileHandler targetFolder){
        //
        String fileName = targetFolder.getFromTargetLib()[index].getName();
        System.out.println(fileName);

        char typeSimbol = '?';
        String margin = "";
        String info = "?";

        double sizeDob;
        String sizeNum;
        String sizeType = "b";

        int elementsIn;

        if(targetFolder.getFromTargetLib()[index].isDirectory()){
            typeSimbol = '/';
        }
        else if(targetFolder.getFromTargetLib()[index].isFile()){
            typeSimbol = '<';
        }
        else{
            typeSimbol = '#';
        }

        for(int j = 0; j < 40 - (fileName.toCharArray().length + 2); j++){
            margin += " ";
        }
        //

        if(targetFolder.getFromTargetLib()[index].isFile()){
            sizeDob = targetFolder.getFromTargetLib()[index].length();
            sizeNum = String.valueOf(sizeDob);

            if(sizeDob >= (1024*1024*1024)*0.8){
                sizeNum = df.format(sizeDob / (1024 * 1024 * 1024));
                sizeType = "gb";
            }
            else if(sizeDob >= (1024*1024)*0.8){
                sizeNum = df.format(sizeDob / (1024 * 1024));
                sizeType = "mb";
            }
            else if (sizeDob >= 1024*0.8){
                sizeNum = df.format(sizeDob / 1024);
                sizeType = "kb";
            }
            else {
                sizeNum = df.format(sizeDob);
                sizeType = "b";
            }
            System.out.println("file!");
            info = sizeNum + " " + sizeType;
            System.out.println("all ok!");
        }

        else if(MainActivity.targetFolder.getFromTargetLib()[index].isDirectory()){
            try {
                elementsIn = new File(targetFolder.getPath() + "/" + fileName).listFiles().length;
                info = "(" + elementsIn + ")";
                margin = "";
            }catch (NullPointerException ex){
                info = "(Not access)";
                margin = "";
            }
        }
        returnS[0] = ("  " + typeSimbol + " " + fileName + margin + info);
        System.out.println(fileName + " " + returnS[0]);
        returnS[1] = fileName;

//            else{
//                returnS = "aboba do not like this •_•";
//            }


        //fucking return
        return returnS;
    }


    public void mkdir(String folderName){
        File folder = new File(path + "/" + folderName);
        folder.mkdir();
    }

    public void mkfile(String fileName){
        try{
            File folder = new File(path + "/" + fileName);
            folder.createNewFile();
        }
        catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

    public String oneStageUndo(){
        File fileNameOld = new File(file.getAbsolutePath());
        String path = "";
        char[] fileAbsNameArray = fileNameOld.getAbsolutePath().toCharArray();
        int newArrayLength = fileAbsNameArray.length;
        for(int i = 0; i < fileAbsNameArray.length; i++){
            if(fileAbsNameArray[fileAbsNameArray.length - (i+1)] != '/'){
                newArrayLength -= 1;
            }
            else{
                break;
            }
        }
        for (int i = 0; i < newArrayLength - 1; i++){
            path += String.valueOf(fileAbsNameArray[i]);
        }
        return path;
    }

    public File[] getFromTargetLib(){

        objectsInFolder = new File(path).listFiles();

        return objectsInFolder;
    }

    public File[] getFromTargetLib(DataType dataType){
        int lengthList;
        try{
            lengthList = new File(path).listFiles().length;
        }
        catch(ArrayIndexOutOfBoundsException ex){
            ex.printStackTrace();
            lengthList = 0;
        }
        switch(dataType){
            case NONE:
            case ALL:{
                objectsInFolder = new File(path).listFiles();
                break;
            }
            case FILE:{
                int a = 0;
                for(int i = 0; i < lengthList; i++){
                    if(new File(path).listFiles()[i].isFile()){
                        objectsInFolder[a] = new File(path).listFiles()[i];
                    }
                }
                break;
            }
            case FOLDER:{
                int a = 0;
                for(int i = 0; i < lengthList; i++){
                    if(new File(path).listFiles()[i].isDirectory()){
                        objectsInFolder[a] = new File(path).listFiles()[i];
                    }
                }
                break;
            }
            case HIDEN:{
                int a = 0;
                for(int i = 0; i < lengthList; i++){
                    if(new File(path).listFiles()[i].isHidden()){
                        objectsInFolder[a] = new File(path).listFiles()[i];
                    }
                }
                break;
            }
            case UNEDITED:{
                int a = 0;
                for(int i = 0; i < lengthList; i++){
                    if(!new File(path).listFiles()[i].canRead()){
                        objectsInFolder[a] = new File(path).listFiles()[i];
                    }
                }
                break;
            }
            case UNWRITED:{
                int a = 0;
                for(int i = 0; i < lengthList; i++){
                    if(!new File(path).listFiles()[i].canWrite()){
                        objectsInFolder[a] = new File(path).listFiles()[i];
                    }
                }
                break;
            }
        }


        return objectsInFolder;
    }

    public String getPath(){
        return path;
    }

    public void setPath(String path){
        this.path = path;
    }

}
