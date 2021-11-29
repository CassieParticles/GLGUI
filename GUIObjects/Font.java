package GUIObjects;

import rendering.Texture2D;
import utils.FileHandling;

import java.util.HashMap;
import java.util.Map;

public class Font {
    private Texture2D fontSheet;
    private int columns;    //How many characters wide the sheet is
    private int rows;       //How many characters tall the sheet is

    private float characterHeight;

    private Map<Integer,Integer> charWidth;

    public Font(String fontDir,String fontCSVDir, int columns, int rows) throws Exception {
        fontSheet=new Texture2D(fontDir);
        charWidth=new HashMap<Integer,Integer>();
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

    public Texture2D getFontSheet(){
        return fontSheet;
    }

    public float[] genCharacter(char c){
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
    }
}
