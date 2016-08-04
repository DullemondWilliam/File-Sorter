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
 
    	File file = new File("C:/Users/Ares/Music/William Music");
    	
    	cleanDir(file);
    	//printDir(0,file);
    	
    	System.out.println("Duplicates songs: "+duplicates);
    	System.out.println("Unique songs: "+allFiles.size());
    }

    public static void cleanDir(File file){
    	if(file.isDirectory()){
    		File subFiles[] = file.listFiles();
        	if(subFiles == null)	{return;}
        	for(File f : subFiles){
        		cleanDir(f);
        	}
        	
    	}else if(file.isFile()){
    		if(nameExists(allFiles,file)){
    			duplicates++;
    			System.out.println("* "+file.getName());
    			file.delete();
    			//Delete it
    		}else{
    			allFiles.add(standardName(file).toLowerCase());
    			System.out.println(file.getName());
    		}
    	}else{
    		System.err.println(file.getName());
    	}
    }
    
    public static String standardName(File file){
    	String name = file.getName();
    	while(name.charAt(0) == 32 ||
    			(name.charAt(0) <= 57 && 
    			name.charAt(0) >= 48)){
    		
    		name = name.substring(1);
    	}
    	
    	
    	//File newFile = new File(file.getParent()+ "\\"+name);
        //System.out.println(file.renameTo(newFile));
    	///////////////////////////////////////////////////////////////////////
    	if(name.length() < 6)return name;
        name = name.substring(0,name.length()-5);
    	while(name.charAt(name.length()-1) == 32 ||
    			(name.charAt(name.length()-1) <= 57 &&
    			name.charAt(name.length()-1) >= 48)){
    		name = name.substring(0, name.length()-2);
    	}
    	return name;
    }
    
    public static Boolean nameExists(ArrayList<String> array, File file){
    	String name = standardName(file);
    	for(String a : array){
    		if(a.equals(name.toLowerCase())){
    			return true;
    		}
    	}
    	return false;
    }
    
    
    public static void printDir(int depth, File file){
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
    		System.out.println(subFiles[i].getName());
    		printDir(depth+1,subFiles[i]);
    	}
    }

}
