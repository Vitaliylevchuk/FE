package com.fbs.fe;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileInformative {
    
    public static File file;
    public final String absPath;
    public final String name;
    public final String parent;
    public final boolean hiden;
    public final boolean canRead;
    public final boolean canWrite;
    private static String content;
    
    public FileInformative(File file){
        this.file = file;
        content = getFileContent();
        absPath = file.getAbsolutePath();
        name = file.getName();
        parent = file.getParent();
        hiden = file.isHidden();
        canRead = file.canRead();
        canWrite = file.canWrite();
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
        for (int i = 0; i < newArrayLength; i++){
            path += String.valueOf(fileAbsNameArray[i]);
        }
        return path;
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
    
    public String getFileContent(int line){
        String cont = "";
        String[] lines = new String[0];
        try{
        try{
			FileReader reader = new FileReader(file);
			
			char [] b = new char[(int)file.length()];
            reader.read(b);

			for(char c : b){
				cont += c;
			}
            
            lines = cont.split("\\n");
			
			reader.close();
			
		}catch (IOException ex){
			System.out.println(ex.getMessage());
		}
        }
        catch(ArrayIndexOutOfBoundsException aioobe){
            aioobe.printStackTrace();
        }
        try{
            return lines[line - 1];
        }catch(ArrayIndexOutOfBoundsException ex){
            return "";
        }
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
    
}