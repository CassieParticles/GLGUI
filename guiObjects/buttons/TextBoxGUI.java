package guiObjects.buttons;

import guiObjects.GUI;
import guiObjects.RectangleGUI;
import guiObjects.TextGUI;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import utils.Input;

import java.util.Locale;

public class TextBoxGUI extends GUI {

    private RectangleGUI bgRect;
    private TextGUI text;
    private Input input;

    private Vector3f bgUnselectedColour;
    private Vector3f bgSelectedColour;

    private boolean isSelected;
    private String acceptableCharacters;

    public TextBoxGUI(Vector2f position, Vector2f size, Vector3f bgUnselectedColour,Vector3f bgSelectedColour, Input input) throws Exception {
        super(position, size);
        this.input = input;
        this.bgUnselectedColour=bgUnselectedColour;
        this.bgSelectedColour=bgSelectedColour;
        bgRect=new RectangleGUI(position,size,bgUnselectedColour);
    }

    public void initText(String initialString, int maxLength, String fontDir, String fontCSVDir, String acceptableCharacters) throws Exception {
        text=new TextGUI(position,new Vector2f(1,1),initialString,maxLength,(int)size.x,fontDir,fontCSVDir);
        this.acceptableCharacters=acceptableCharacters.toUpperCase(Locale.ROOT);
    }

    public void setText(String text) throws Exception {
        this.text.changeText(text);
        this.text.generateText();
    }

    public void setSelected(boolean selected){
        this.isSelected=selected;
    }

    public boolean getSelected(){
        return isSelected;
    }

    public void use(Vector2f screenSize) throws Exception {
        int[] mousePosition=input.getMousePos();
        mousePosition[0]-=screenSize.x/2;
        mousePosition[1]= (int) (mousePosition[1]*-1+screenSize.y/2);
        if(input.isMouseButtonPressed(GLFW.GLFW_MOUSE_BUTTON_1)){
            if(bgRect.pointInRectangle(new Vector2f(mousePosition[0],mousePosition[1]))){
                setSelected(true);
                bgRect.setColour(bgSelectedColour);
            }else{
                setSelected(false);
                bgRect.setColour(bgUnselectedColour);
            }
        }
        detectKeyBoardInputs();
    }

    private void detectKeyBoardInputs() throws Exception {
        if(isSelected){
            if(input.isKeyPressed(GLFW.GLFW_KEY_BACKSPACE)){
                text.backSpace();
                text.generateText();
            }else if(input.isKeyPressed(GLFW.GLFW_KEY_ENTER)){
                setSelected(false);
                bgRect.setColour(bgUnselectedColour);
                return;
            }
            for(char c:acceptableCharacters.toCharArray()){
                if(input.isKeyPressed((int)c)){
                    text.addChar(c);
                    text.generateText();
                }
            }
        }
    }

    @Override
    public void render(Vector2f screenSize) {
        bgRect.render(screenSize);
        text.render(screenSize);
    }

    @Override
    public void cleanup(){
        bgRect.cleanup();
        text.cleanup();
    }

    @Override
    public String toString() {
        return this.text.toString();
    }
}
