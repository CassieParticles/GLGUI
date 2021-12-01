package GUIObjects;

import org.joml.Vector2f;
import rendering.Program;

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
    }

    public void changeText(String newText){
        if(!newText.equalsIgnoreCase(string)){
            string=newText;
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
