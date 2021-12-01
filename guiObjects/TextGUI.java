package guiObjects;

import com.sun.deploy.util.StringUtils;
import org.joml.Vector2f;

public class TextGUI extends GUI{
    private Font font;
    private String string;
    private CharacterGUI[] characters;
    private int maxLength;

    public TextGUI(Vector2f position, Vector2f scale,String string, int maxLength, String fontDir, String fontCSVDir) throws Exception {
        super(position,scale);
        this.string=string;
        this.font=new Font(fontDir,fontCSVDir,16,16);
        this.maxLength=maxLength;
        generateText();
    }

    public void generateText() throws Exception {
        if(characters!=null){
            for(CharacterGUI c:characters){
                c.cleanup();
            }
        }
        characters=new CharacterGUI[Math.min(string.length(),maxLength)];
        float currentPos= position.x;
        for(int i=0;i< characters.length;i++){
            float[] characterInfo=font.genCharacter(string.toCharArray()[i]);
            characters[i]=new CharacterGUI(new Vector2f(currentPos,position.y),new Vector2f(font.getCharacterWidth(string.toCharArray()[i]),font.getHeight()),font,new Vector2f(characterInfo[0],characterInfo[1]), new Vector2f(characterInfo[2],characterInfo[3]));
            currentPos+=font.getCharacterWidth(string.toCharArray()[i]);
        }
        if(characters.length==0){
            characters=new CharacterGUI[1];
            float[] characterInfo=font.genCharacter(' ');
            characters[0]=new CharacterGUI(new Vector2f(currentPos,position.y),new Vector2f(font.getCharacterWidth(' '),font.getHeight()),font,new Vector2f(characterInfo[0],characterInfo[1]), new Vector2f(characterInfo[2],characterInfo[3]));
        }
    }

    public void changeText(String newText){
        if(!newText.equalsIgnoreCase(string)){
            if(newText.length()<=maxLength){
                string=newText;
            }else{
                string=newText.substring(0,maxLength-1);
            }
        }
    }

    public void addChar(char c){
        if(string.length()<maxLength){
            string=string.concat(String.valueOf(c));
        }
    }

    public void backSpace(){
        char[] chars=string.toCharArray();
        string="";
        for(int i=0;i<chars.length-1;i++) {
            string+=String.valueOf(chars[i]);
        }
    }

    public String getString(){
        return string;
    }

    @Override
    public void render(Vector2f screenSize){
        for(CharacterGUI c : characters){
            c.render( screenSize);
        }
    }


    @Override
    public void cleanup(){
        font.cleanup();
        for(CharacterGUI c : characters){
            c.cleanup();
        }
    }
}
