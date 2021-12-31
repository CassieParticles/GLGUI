package guiObjects;

import org.joml.Vector2f;
import org.lwjgl.opengl.GL46;
import rendering.*;

public class TextureGUI extends GUI{
    private static float[] vertices=new float[]{0,1,
            1,1,
            1,0,
            0,0};
    private static int[] indices=new int[]{
            0,1,2,
            0,2,3
    };
    private static float[] textCoords=new float[]{
            0,0,
            1,0,
            1,1,
            0,1
    };

    private final Texture texture;
    private final TextureMesh mesh;
    private static GUIProgram program=null;

    private static final String vertexShaderCode="#version 330 \n" +
            "layout(location=0) in vec2 position;\n" +
            "layout(location=1) in vec2 textureCoords;\n" +
            "uniform vec2 translation;\n" +
            "uniform vec2 scale;\n" +
            "uniform vec2 screenSize;\n" +
            "out vec2 fragTextCoords;\n" +
            "void main() {\n" +
            "    vec2 scaledPos=(position*scale+translation)*2/screenSize;\n" +
            "    gl_Position=vec4(scaledPos,0,1);\n" +
            "    fragTextCoords=textureCoords;\n" +
            "}";

    private static final String fragmentShaderCode="#version 330\n" +
            "in vec2 fragTextCoords;\n" +
            "uniform sampler2D textureSampler;\n" +
            "out vec4 FragColour;\n" +
            "void main() {\n" +
            "    FragColour=texture(textureSampler,fragTextCoords);\n" +
            "}\n";

    /**
     * sus sus haha
     * @param position Position, in pixels
     * @param size Size, in pixels
     * @param image The texture that will be rendered
     * @throws Exception
     */
    public TextureGUI(Vector2f position, Vector2f size, Texture image) throws Exception {
        super(position, size);
        this.texture=image;
        mesh=new TextureMesh(vertices,indices,textCoords,image.getId());

        if(program==null){
            program=new GUIProgram();
            program.attachShaders(new GUIShader[]{
                    new GUIShader(vertexShaderCode, GL46.GL_VERTEX_SHADER),
                    new GUIShader(fragmentShaderCode,GL46.GL_FRAGMENT_SHADER)
            });
            program.link();
            program.createUniform("translation");
            program.createUniform("scale");
            program.createUniform("screenSize");
            program.createUniform("textureSampler");
        }
    }

    /**
     * sus sus haha
     * @param position Position, in pixels
     * @param size Size, in pixels
     * @param path The path to the texture to be rendered
     * @throws Exception
     */
    public TextureGUI(Vector2f position, Vector2f size, String path) throws Exception {
        this(position,size,new Texture(path));
    }

    public boolean pointInRectangle(Vector2f point){
        return (point.x> position.x&&point.x<position.x+size.x&&point.y> position.y&&point.y<position.y+size.y);
    }

    @Override
    public void render(Vector2f screenSize){
        program.useProgram();
        program.setUniform("translation",position);
        program.setUniform("scale",size);
        program.setUniform("screenSize",screenSize);
        program.setUniform("textureSampler",0);
        mesh.render(screenSize);
        program.detachProgram();
    }

    @Override
    public void cleanup(){
        mesh.cleanup();
        texture.cleanup();
        if(program!=null){
            program.cleanup();
            program=null;
        }
    }
}
