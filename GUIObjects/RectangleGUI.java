package GUIObjects;

import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector3f;
import rendering.Mesh;
import rendering.Program;
import rendering.RectangleMesh;

public class RectangleGUI extends GUI{


    private static final RectangleMesh mesh=new RectangleMesh(new float[]{
            0,1,
            1,1,
            1,0,
            0,0
    },new int[]{
            0,1,2,
            0,2,3
    });

    private final Vector3f colour;

    public RectangleGUI(Vector2f position,Vector2f size, Vector3f colour){
        super(position,size);
        this.colour=colour;
    }

    public boolean pointInRectangle(Vector2f point){
        return (point.x> position.x&&point.x<position.x+size.x&&point.y> position.y&&point.y<position.y+size.y);
    }

    public void setColour(Vector3f colour){
        this.colour.set(colour);
    }

    @Override
    public void render(Program program, Vector2f screenSize){
        program.setUniform("colour",colour);
        super.render(program,screenSize);
        mesh.render(program,screenSize);
    }

    @Override
    public void cleanup() {
        mesh.cleanup();
    }
}
