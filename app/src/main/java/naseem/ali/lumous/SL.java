package naseem.ali.lumous;


import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Set;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Naseem
 */
public class SL {
    private static SL slInstance;
    private final HashMap<String,String> map;
    private final HashMap<String,String> videos;
    private Context context;
    private SL(Context context){
        this.context=context;
        map=new HashMap<>();
        videos=new HashMap<>();
        if (slInstance != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }
    
    public static SL getInstance(Context context){
        if(slInstance==null){
            synchronized(SL.class) {
                if(slInstance==null) {
                    slInstance = new SL(context);
                }
            }
        }
        return slInstance;
    }
    
    public void initialize(){
        try{
            loadVocab();
            loadExamples();
        }catch(Exception e){
            System.err.println(e);
        }
    }
    
    private void loadVocab() throws IOException{
        BufferedReader br=new BufferedReader(new InputStreamReader(context.getResources().getAssets().open("bsl.data")));
        String line;
        while((line=br.readLine())!=null){
            String set[]=line.split("=");
            map.put(set[0].toLowerCase().trim(),set[1].trim());
        }
    }
    
    private void loadExamples() throws IOException{
        BufferedReader br=new BufferedReader(new InputStreamReader(context.getResources().getAssets().open("words.data")));
        String line;
        while((line=br.readLine())!=null){
            String set[]=line.split("=");
            videos.put(set[0].toLowerCase().trim(),set[1].trim());
        }
    }
    
    public Set<String> getVocab(){
        return map.keySet();
    }
    
    public String getVocabImage(String word){
        return map.get(word);
    }
    
    public String getVideoExample(String word){
        return videos.get(word);
    }
    
    public File getGif(String data[]){
        return null;
    }
}
