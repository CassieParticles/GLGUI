package guiObjects.buttons;

import guiObjects.GUI;
import guiObjects.RectangleGUI;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import utils.Input;

public class ButtonGUI extends GUI {

    protected RectangleGUI buttonBg;
    protected Input input;

    protected ButtonAction action;

    public ButtonGUI(Vector2f position, Vector2f scale, Vector3f colour, Input input, ButtonAction action)throws Exception{
        super(position,scale);
        buttonBg=new RectangleGUI(position,scale,colour);
        this.input=input;
        this.action=action;
    }

    public ButtonGUI(Vector2f position, Vector2f scale, Vector3f colour, Input input) throws Exception{
        this(position,scale,colour,input,null);
    }

    @Override
    public void render( Vector2f screenSize){
        buttonBg.render(screenSize);
    }

    public void setAction(ButtonAction action){
        this.action=action;
    }

    public void use(Vector2f screenSize){
        int[] mousePosition=input.getMousePos();
        mousePosition[0]-=screenSize.x/2;
        mousePosition[1]= (int) (mousePosition[1]*-1+screenSize.y/2);
        if(buttonBg.pointInRectangle(new Vector2f(mousePosition[0],mousePosition[1]))&&input.isMouseButtonPressed(GLFW.GLFW_MOUSE_BUTTON_1)){
            performTask();
        }
    }

    private void performTask(){
        action.performAction();
    }

    @Override
    public void cleanup(){
        buttonBg.cleanup();
    }
}
