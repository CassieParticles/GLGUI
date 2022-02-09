package guiObjects;

import org.joml.Vector2f;
import org.lwjgl.opengl.GL46;
import rendering.GUIProgram;
import rendering.GUIShader;
import rendering.TextureMesh;

public class CharacterGUI extends GUI{
    private TextureMesh mesh;
    private Font font;
    private static GUIProgram program=null;

    private Vector2f textSize;

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

    public CharacterGUI(Vector2f scale,int fontSheetId,Vector2f texPos, Vector2f texSize) throws Exception {
        super(new Vector2f(),scale);
        textSize=new Vector2f(1);
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
        },fontSheetId);

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

    public void setTextSize(Vector2f textSize) {
    	this.textSize=textSize;
    }

    @Override
    public void render( Vector2f screenSize){
        program.useProgram();
        program.setUniform("textureSampler",0);
        program.setUniform("screenSize",screenSize);
        program.setUniform("translation",position);
        program.setUniform("scale",new Vector2f(size).mul(textSize));
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
