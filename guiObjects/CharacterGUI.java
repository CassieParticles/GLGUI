package guiObjects;

import org.joml.Vector2f;
import org.lwjgl.opengl.GL46;
import rendering.GUIProgram;
import rendering.GUIShader;
import rendering.TextureMesh;

public class CharacterGUI extends GUI{
    private TextureMesh mesh;
    private final Font font;
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

    @Override
    public void render( Vector2f screenSize){
        program.useProgram();
        program.setUniform("textureSampler",0);
        program.setUniform("screenSize",screenSize);
        program.setUniform("translation",position);
        program.setUniform("scale",size);
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
