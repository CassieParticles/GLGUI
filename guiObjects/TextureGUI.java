package guiObjects;

import org.joml.Vector2f;
import org.lwjgl.opengl.GL46;
import rendering.*;
import utils.FileHandling;

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

    private final Texture2D texture;
    private final TextureMesh mesh;
    private static Program program=null;
    public TextureGUI(Vector2f position, Vector2f size, Texture2D image) throws Exception {
        super(position, size);
        this.texture=image;
        mesh=new TextureMesh(vertices,indices,textCoords,image.getId());

        if(program==null){
            program=new Program();
            program.attachShaders(new Shader[]{
                    new Shader(FileHandling.loadResource("src/shaders/texture/vertex.glsl"), GL46.GL_VERTEX_SHADER),
                    new Shader(FileHandling.loadResource("src/shaders/texture/fragment.glsl"),GL46.GL_FRAGMENT_SHADER)
            });
            program.link();
            program.createUniform("translation");
            program.createUniform("scale");
            program.createUniform("screenSize");
            program.createUniform("textureSampler");
        }
    }

    public TextureGUI(Vector2f position, Vector2f size, String path) throws Exception {
        this(position,size,new Texture2D(path));
    }

    public boolean pointInRectangle(Vector2f point){
        return (point.x> position.x&&point.x<position.x+size.x&&point.y> position.y&&point.y<position.y+size.y);
    }

    @Override
    public void render(Vector2f screenSize){
        super.render(screenSize);
        program.setUniform("screenSize",screenSize);
        program.setUniform("textureSampler",0);
        mesh.render(screenSize);
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
