package guiObjects;

import rendering.Texture;
import utils.FileHandling;

import java.util.HashMap;
import java.util.Map;

import org.joml.Vector2f;

public class Font {
    private Texture fontSheet;
    private int columns;    //How many characters wide the sheet is
    private int rows;       //How many characters tall the sheet is

    private float characterHeight;

    private Map<Integer,Integer> charWidth;
    private Map<Character, CharacterGUI> generatedCharacters;
    
    private static Map<String, Font> fonts=new HashMap<>();
    
    public static Font getFont(String fontDir, String fontCSVDir, int columns, int rows) throws Exception{
    	if(fonts.containsKey(fontDir)){ //If font for fontsheet has already been generated, return existing font
    		return fonts.get(fontDir);
    	}else{
    		Font font=new Font(fontDir,fontCSVDir, columns, rows);  //Generate new font if it doesn't exist yet
    		fonts.put(fontDir,font);
    		return font;
    	}
    }

    public Font(String fontDir,String fontCSVDir, int columns, int rows) throws Exception {
        fontSheet=new Texture(fontDir);
        charWidth=new HashMap<>();
        generatedCharacters=new HashMap<>();
        this.columns=columns;
        this.rows=rows;
        try{
            String fontData= FileHandling.loadResource(fontCSVDir);
            String[] fontArray=fontData.split("\n");
            int currentChar=0;
            characterHeight=Float.parseFloat(fontArray[3].split(",")[1]);
            for(int i=8;i<fontArray.length;i++){
                charWidth.put(currentChar,Integer.parseInt(fontArray[i].split(",")[1]));
                currentChar++;
            }
        }catch(Exception e){
            fontSheet.cleanup();
        }
    }

    public Texture getFontSheet(){
        return fontSheet;
    }
    
    public CharacterGUI getChar(char c) throws Exception{
    	if(generatedCharacters.containsKey(c)){ //If character already exists, return the generated character
    		return generatedCharacters.get(c);
    	}else{
    		float[] charInfo=genCharacter(c);   //If character hasn't been generated yet, generate a new character
    		CharacterGUI character=new CharacterGUI(
                    new Vector2f(getCharacterWidth(c),getHeight()),
                    fontSheet.getId(),
                    new Vector2f(charInfo[0],charInfo[1]),
                    new Vector2f(charInfo[2],charInfo[3]));
    		generatedCharacters.put(c,character);
    		return character;
    	}

    }
   

    private float[] genCharacter(char c){
        int column=c%rows;
        int row=c/rows;

        float columnSize=1f/columns;
        float rowSize=1f/rows;
        return new float[]{column*columnSize,row*rowSize,(float)getCharacterWidth(c)/ fontSheet.getWidth(),characterHeight/ fontSheet.getHeight()};
//        return new CharacterGUI(this,new Vector2f(column*columnSize,row*rowSize),new Vector2f(getCharacterWidth(c),characterHeight),c);
    }

    public float getCharacterWidth(char c){
        return charWidth.get((int)c);
    }

    public float getHeight(){
        return characterHeight;
    }

    public void cleanup(){
        fontSheet.cleanup();
        if(generatedCharacters.size()!=0) {
        	for(CharacterGUI c:generatedCharacters.values()){
            	c.cleanup();
            }
        }
    }
    
    public static void cleanupFonts(){
    	if(fonts.size()!=0) {
    		for(Font f:fonts.values()){
        		f.cleanup();
        	}
    	}
    }
}
