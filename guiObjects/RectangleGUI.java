package guiObjects;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL46;
import rendering.GUIProgram;
import rendering.RectangleMesh;
import rendering.GUIShader;

public class RectangleGUI extends GUI{

    private static GUIProgram program = null;
    private static RectangleMesh mesh = null;   //Move all programs to GUI rather then meshes, to allow them to be passed down easier

    private static final String vertexShaderCode="#version 330\n" +
            "layout(location=0) in vec2 position;\n" +
            "uniform vec2 translation;\n" +
            "uniform vec2 scale;\n" +
            "uniform vec2 screenSize;\n" +
            "void main(){\n" +
            "vec2 scaledPos=(position*scale+translation)*2/screenSize;\n" +
            "gl_Position=vec4(scaledPos,0,1);\n" +
            "}";

    private static final String fragmentShaderCode="#version 330\n" +
            "uniform vec3 colour;\n" +
            "out vec4 outColour;\n" +
            "void main(){\n" +
            "outColour=vec4(colour,1);\n" +
            "}";

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
            program=new GUIProgram();
            program.attachShaders(new GUIShader[]{
                    new GUIShader(vertexShaderCode, GL46.GL_VERTEX_SHADER),
                    new GUIShader(fragmentShaderCode,GL46.GL_FRAGMENT_SHADER)
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
        GL46.glBindVertexArray(0);
        this.colour.set(colour);
    }

    @Override
    public void render( Vector2f screenSize){
        program.useProgram();
        program.setUniform("screenSize",screenSize);
        program.setUniform("translation",position);
        program.setUniform("scale",size);
        program.setUniform("colour",colour);
        mesh.render(screenSize);
        program.detachProgram();
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
