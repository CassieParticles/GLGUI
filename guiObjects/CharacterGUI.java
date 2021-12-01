package guiObjects;

import org.joml.Vector2f;
import org.lwjgl.opengl.GL46;
import rendering.Program;
import rendering.Shader;
import rendering.TextureMesh;
import utils.FileHandling;

public class CharacterGUI extends GUI{
    private TextureMesh mesh;
    private final Font font;
    private static Program program=null;

    public CharacterGUI(Vector2f position, Vector2f scale,Font font,Vector2f texPos, Vector2f texSize) throws Exception {
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

    @Override
    public void render( Vector2f screenSize){
        program.useProgram();
        program.setUniform("textureSampler",0);
        program.setUniform("screenSize",screenSize);
        program.setUniform("translation",position);
        program.setUniform("scale",size);
        super.render(screenSize);
        mesh.render(screenSize);
    }

    @Override
    public void cleanup(){
        mesh.cleanup();
        if(program!=null){
            program.cleanup();
            program=null;
        }
    }
}
