package GUIObjects;

import org.joml.Vector2f;
import rendering.Program;
import rendering.RectangleMesh;
import rendering.Texture2D;
import rendering.TextureMesh;

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
    public TextureGUI(Vector2f position, Vector2f size, Texture2D image) {
        super(position, size);
        this.texture=image;
        mesh=new TextureMesh(vertices,indices,textCoords,image.getId());
    }

    public TextureGUI(Vector2f position, Vector2f size, String path) throws Exception {
        this(position,size,new Texture2D(path));
    }

    public boolean pointInRectangle(Vector2f point){
        return (point.x> position.x&&point.x<position.x+size.x&&point.y> position.y&&point.y<position.y+size.y);
    }

    @Override
    public void render(Program program, Vector2f screenSize){
        super.render(program,screenSize);
        mesh.render(program, screenSize);
    }

    @Override
    public void cleanup(){
        mesh.cleanup();
        texture.cleanup();
    }
}
