package guiObjects;

import org.joml.Vector2f;

public class GUI {

    protected Vector2f position;
    protected Vector2f size;

    public GUI(Vector2f position, Vector2f size){
        this.position=position;
        this.size=size;
    }

    public void setPosition(Vector2f position){
        this.position=position;
    }

    public void setSize(Vector2f size){
        this.size=size;
    }

    public Vector2f getPosition(){
        return position;
    }

    public Vector2f getSize(){
        return size;
    }

    public void render(Vector2f screenSize){

    }

    public void cleanup(){

    }
}
