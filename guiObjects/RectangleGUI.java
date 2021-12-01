package guiObjects;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL46;
import rendering.Program;
import rendering.RectangleMesh;
import rendering.Shader;
import utils.FileHandling;

public class RectangleGUI extends GUI{

    private static Program program = null;
    private static RectangleMesh mesh = null;   //Move all programs to GUI rather then meshes, to allow them to be passed down easier

    private final Vector3f colour;

    public RectangleGUI(Vector2f position,Vector2f size, Vector3f colour) throws Exception{
        super(position,size);
        mesh=new RectangleMesh(new float[]{
                0,1,
                1,1,
                1,0,
                0,0
        },new int[]{
                0,1,2,
                0,2,3
        });
        if(program==null){
            program=new Program();
            program.attachShaders(new Shader[]{
                    new Shader(FileHandling.loadResource("src/shaders/rectangle/vertex.glsl"), GL46.GL_VERTEX_SHADER),
                    new Shader(FileHandling.loadResource("src/shaders/rectangle/fragment.glsl"),GL46.GL_FRAGMENT_SHADER)
            });
            program.link();
            program.createUniform("screenSize");
            program.createUniform("translation");
            program.createUniform("scale");
            program.createUniform("colour");
        }

        this.colour=new Vector3f(colour);
    }

    public boolean pointInRectangle(Vector2f point){
        return (point.x> position.x&&point.x<position.x+size.x&&point.y> position.y&&point.y<position.y+size.y);
    }

    public void setColour(Vector3f colour){
        this.colour.set(colour);
    }

    @Override
    public void render( Vector2f screenSize){
        program.useProgram();
        program.setUniform("screenSize",screenSize);
        program.setUniform("translation",position);
        program.setUniform("scale",size);
        program.setUniform("colour",colour);
        super.render(screenSize);
        mesh.render(screenSize);
        program.unlinkProgram();
    }

    @Override
    public void cleanup() {
        if(program!=null){
            program.cleanup();
            program=null;
        }
        mesh.cleanup();
    }
}
