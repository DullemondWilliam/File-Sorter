import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.TagField;

public class Main {

	public static ArrayList<String> allFiles ;
	public static int duplicates = 0;
	
    public static void main(String args[]) throws IOException, CannotReadException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
    	allFiles = new ArrayList<String>();
 
    	File file = new File("C:\\Users\\Ares\\Desktop\\William Music\\Dont Let Me Down (feat. Daya).mp3");
    	
    	//cleanDuplicates(file);
    	//printDir(0,file);
    	//cleanEmptyFolders(file);
    	//collectFiles(file,file);
    	//cleanFileNames(file);
    	//deleteFilesByExtension(file, ".jpeg");
    	
    	
    	//System.out.println("Duplicates songs: "+duplicates);
    	//System.out.println("Unique songs: "+allFiles.size());
    	
    	AudioFile f = AudioFileIO.read(file);
    	
    	Tag tag = f.getTag();
    	System.out.println("artist: "+tag.getFirst(FieldKey.ARTIST));
    	System.out.println("artists: "+tag.getFirst(FieldKey.ARTISTS));
    	System.out.println("album: "+tag.getFirst(FieldKey.ALBUM));
    	System.out.println("album artist: "+tag.getFirst(FieldKey.ALBUM_ARTIST));
    	System.out.println("title: "+tag.getFirst(FieldKey.TITLE));
    	System.out.println("track: "+tag.getFirst(FieldKey.TRACK));
    	System.out.println("year: "+tag.getFirst(FieldKey.YEAR));

    	
    }

    public static void cleanDuplicates(File file){
    	if(file.isDirectory()){
    		File subFiles[] = file.listFiles();
        	if(subFiles == null)	{return;}
        	for(File f : subFiles){
        		cleanDuplicates(f);
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
    
    public static void cleanEmptyFolders(File file){
    	if(!file.exists() || !file.isDirectory()){ return;}
    	
    	File[] subFiles = file.listFiles();
    	if(subFiles.length == 0){
    		System.out.println("* "+file);
    		file.delete();
    		return;
    	}
    	
    	for(File f : subFiles){
    		cleanEmptyFolders(f);
    	}

    	subFiles = file.listFiles();
    	if(subFiles.length == 0){
    		System.out.println(file);
    		file.delete();
    		return;
    	}
    }
    
    public static void collectFiles(File dest, File file){
    	if(file.isDirectory()){
    		File[] subFiles = file.listFiles();
    		for(File f : subFiles){
    			collectFiles(dest, f);
    		}
    		
    	}else if(file.isFile()){
    		File newLoc = new File(dest.getPath() + "\\" + file.getName());
    		file.renameTo(newLoc);
    	}
    }
   
    public static void cleanFileNames(File file){
    	if(file.isDirectory()){
    		File[] subFiles = file.listFiles();
    		for(File f : subFiles){
    			cleanFileNames(f);
    		}

    	}else if(file.isFile()){
    		try{
    		String oldName = file.getName();
    		String newName = standardName(file) 
    				+ oldName.substring(oldName.lastIndexOf('.'));
    		
    		File newLoc = new File(file.getParent() + "/" + newName);
    		System.out.println(newLoc);
    		System.out.println(file);
    		System.out.println(file.renameTo(newLoc));
    		
    		}catch(Exception e){
    			System.err.println("err" + file.getName());
    		}
    	}
    }
   
    public static void deleteFilesByExtension(File file, String ext){
    	if(file.isDirectory()){
    		File[] subFiles = file.listFiles();
    		for(File f : subFiles){
    			cleanFileNames(f);
    		}

    	}else if(file.isFile()){
    		String fileExt = file.getName().substring(file.getName().lastIndexOf('.'));
    		if(ext.equalsIgnoreCase(fileExt)){
    			file.delete();
    		}
    	}
    }
    ///////////////////////////////////////////////////////////////////////////
    public static String standardName(File file){
    	String name = file.getName();
    	while(name.charAt(0) == 32 ||
    			name.charAt(0) == 45||
    			(name.charAt(0) <= 57 && 
    			name.charAt(0) >= 48)){
    		
    		name = name.substring(1);
    	}   	
    	if(name.length() < 6)return name;
        name = name.substring(0,name.length()-4);
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

    

}
