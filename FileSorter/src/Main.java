import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;

public class Main {

	public static ArrayList<String> allFiles ;
	public static int duplicates = 0;
	
    public static void main(String args[]) throws IOException {
    	allFiles = new ArrayList<String>();
 
    	File file = new File("C:/Users/William/Documents/");
    	
    	cleanDir(file);
    	
    	System.out.println(duplicates);
    }

    public static void cleanDir(File file){
    	if(file.isDirectory()){
    		File subFiles[] = file.listFiles();
        	if(subFiles == null)	{return;}
        	for(File f : subFiles){
        		cleanDir(f);
        	}
        	
    	}else if(file.isFile()){
    		String name = file.getName();
    		if(nameExists(allFiles,file.getName())){
    			duplicates++;
    			//file.delete();
    			//Delete it
    		}else{
    			allFiles.add(file.getName());
    		}
    		
    	}else{
    		System.err.println("What?");
    	}
    	
    	
    	
    }
    
    public static Boolean nameExists(ArrayList<String> array, String name){
    	for(String a : array){
    		if(a.toLowerCase().equals(name.toLowerCase())){
    			return true;
    		}
    	}
    	return false;
    }
    
    
    public static void listDir(int depth, File file){
    	if(!file.isDirectory() || !file.canRead()){
    		return;
    	}
    	File subFiles[] = file.listFiles();
    	if(subFiles == null){
    		return;
    	}
    	
    	
    	for(int i=0; i < subFiles.length; i++){
    		for(int j=0; j<depth; j++){
    			System.out.print("\t");
    		}
    		System.out.println(subFiles[i]);
    		listDir(depth+1,subFiles[i]);
    	}
    }

}
