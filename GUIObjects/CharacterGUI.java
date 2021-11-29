package GUIObjects;

import org.joml.Vector2f;
import rendering.Program;
import rendering.RectangleMesh;
import rendering.TextureMesh;

public class CharacterGUI extends GUI{
    private TextureMesh mesh;
    private final Font font;

    public CharacterGUI(Vector2f position, Vector2f scale,Font font,Vector2f texPos, Vector2f texSize){
        super(position,scale);
        this.font=font;
        mesh=new TextureMesh(new float[]{
                0,1,
                1,1,
                1,0,
                0,0
        },new int[]{
                0,1,2,
                0,2,3
        },new float[]{
                texPos.x,texPos.y,
                texPos.x+texSize.x,texPos.y,
                texPos.x+texSize.x,texPos.y+texSize.y,
                texPos.x,texPos.y+texSize.y,
        },font.getFontSheet().getId());
    }

    @Override
    public void render(Program program, Vector2f screenSize){
        super.render(program,screenSize);

        mesh.render(program, screenSize);
    }

    @Override
    public void cleanup(){
        mesh.cleanup();
    }
}
