package guiObjects;

import org.joml.Vector2f;

public class TextGUI extends GUI{
    private Font font;
    private String string;
    private CharacterGUI[] characters;
    private float[] offsets;
    private int pixelLength;

    private int maxCharacterLength;
    private int maxPixelLength;

    public TextGUI(Vector2f position, Vector2f scale,String string, int maxCharacterLength,int maxPixelLength, String fontDir, String fontCSVDir) throws Exception {
        super(position,scale);
        this.string=string;
        this.font=Font.getFont(fontDir,fontCSVDir,16,16);
        this.maxCharacterLength=maxCharacterLength;
        this.maxPixelLength=maxPixelLength;
        this.pixelLength=0;
        generateText();
    }

    public void generateText() throws Exception {
        characters=new CharacterGUI[Math.min(string.length(), maxCharacterLength)]; //Create array to store characters
        offsets=new float[characters.length];   //Create array to store character offsets
        float currentPos=position.x;
        pixelLength =0;
        for(int i=0;i< characters.length;i++){
        	char c=string.toCharArray()[i];
            characters[i]=font.getChar(c);  //Get character from font, so optimised method can be used
            offsets[i]=pixelLength;
            currentPos+=font.getCharacterWidth(c)*size.x;
            pixelLength+=font.getCharacterWidth(c)*size.x;
            if(pixelLength>maxPixelLength){ //Prevent text being longer then maxPixelLength
                break;
            }
        }
        
        if(characters.length==0){   //If empty string is given, prevent size 0 array and null pointers
            characters=new CharacterGUI[1];
            offsets=new float[1];
            characters[0]=font.getChar(' ');
        }
   }

   public void clearFontCharacters(){
        font.clearCharacters();
   }

    public void changeText(String newText){
        if(!newText.equalsIgnoreCase(string)){
            if(newText.length()<= maxCharacterLength){
                string=newText;
            }else{
                string=newText.substring(0, maxCharacterLength -1);
            }
        }
    }

    public void addChar(char c){
        if(string.length()< maxCharacterLength&&pixelLength+font.getCharacterWidth(c)*size.x<maxPixelLength){
            string=string.concat(String.valueOf(c));
            pixelLength+=font.getCharacterWidth(c)*size.x;
        }
    }

    public void backSpace(){
        if(string.length()!=0){
            char[] chars=string.toCharArray();
            string="";
            for(int i=0;i<chars.length-1;i++) {
                string+=String.valueOf(chars[i]);
            }
            pixelLength-=font.getCharacterWidth(chars[chars.length-1])*size.x;
        }
    }

    public String getString(){
        return string;
    }

    public int getPixelLength(){
        return pixelLength;
    }

    @Override
    public void render(Vector2f screenSize){
        for(int i=0;i<characters.length;i++){
        	CharacterGUI c=characters[i];
            if(c!=null){
            	c.setPosition(new Vector2f(position).add(new Vector2f(offsets[i],0)));  //Set character position to where it should be
            	c.setTextSize(size);
                c.render(screenSize);
            }

        }
    }


    @Override
    public void cleanup(){
        font.cleanup();
        for(CharacterGUI c : characters){
            if (c != null) {
                c.cleanup();
            }
        }
    }

    @Override
    public String toString(){
        return this.string;
    }
}
