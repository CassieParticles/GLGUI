package GUIObjects;

import org.joml.Vector2f;
import org.joml.Vector3f;
import rendering.Program;

import java.awt.*;

public class ButtonGUI extends GUI{

    private RectangleGUI buttonBg;

    public ButtonGUI(Vector2f position, Vector2f scale, Vector3f colour)throws Exception{
        super(position,scale);
        buttonBg=new RectangleGUI(position,scale,colour);
    }

    @Override
    public void render( Vector2f screenSize){

    }

    @Override
    public void cleanup(){
        buttonBg.cleanup();
    }
}
