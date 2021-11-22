package main;

import GUIObjects.TextureGUI;
import GUIObjects.RectangleGUI;
import org.joml.Vector2f;
import org.joml.Vector3f;
import rendering.Program;
import rendering.Shader;
import rendering.Texture2D;
import rendering.TextureMesh;
import utils.FileHandling;
import utils.Input;
import utils.Timer;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL46;

public class Main {

    private Window window;
    private Timer timer;
    private Input input;

    private Program testProgram1;
    private RectangleGUI mesh;

    private Program testProgram2;
    private TextureGUI textureMesh;

    public static void main(String[] args){
        new Main().gameLoop();
    }

    public void gameLoop(){
        try{
            init();
            loop();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            cleanup();
        }
    }

    public void init() throws Exception {
        window=new Window(600,600,"Title");
        timer=new Timer(60,60);
        input=new Input();

        window.init();
        input.init(window);



        testProgram1 =new Program();
        testProgram1.attachShaders(new Shader[]{
                new Shader(FileHandling.loadResource("src/shaders/rectangle/vertex.glsl"),GL46.GL_VERTEX_SHADER),
                new Shader(FileHandling.loadResource("src/shaders/rectangle/fragment.glsl"),GL46.GL_FRAGMENT_SHADER)
        });
        testProgram1.link();
        testProgram1.createUniform("translation");
        testProgram1.createUniform("scale");
        testProgram1.createUniform("screenSize");
        testProgram1.createUniform("colour");

        mesh=new RectangleGUI(new Vector2f(0,0),new Vector2f(200,200),new Vector3f(1,0,0));

        testProgram2=new Program();
        testProgram2.attachShaders(new Shader[]{
                new Shader(FileHandling.loadResource("src/shaders/texture/vertex.glsl"),GL46.GL_VERTEX_SHADER),
                new Shader(FileHandling.loadResource("src/shaders/texture/fragment.glsl"),GL46.GL_FRAGMENT_SHADER)
        });
        testProgram2.link();
        testProgram2.createUniform("translation");
        testProgram2.createUniform("scale");
        testProgram2.createUniform("screenSize");
        testProgram2.createUniform("textureSampler");

        textureMesh=new TextureGUI(new Vector2f(0,0),new Vector2f(200,200),"src/testFiles/sus.png");

        GL46.glClearColor(0.1f, 0.1f, 0.2f, 1.0f);
        window.loop();
    }

    private void render(){
        window.loop();
        GL46.glClear(GL46.GL_COLOR_BUFFER_BIT | GL46.GL_DEPTH_BUFFER_BIT);
        testProgram2.useProgram();
        textureMesh.render(testProgram2,new Vector2f(window.getWidth(),window.getHeight()));
        mesh.render(testProgram1,new Vector2f(window.getWidth(),window.getHeight()));
        testProgram2.detachProgram();
    }

    private void update(){
        if(input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)){
            window.close();
        }
    }

    public void loop(){
        while(!window.shouldClose()){
            timer.update();
            if(timer.getFrame()){
                render();
            }if(timer.getUpdate()){
                update();
            }
        }
    }

    public void cleanup(){
        System.out.println("Cleaning up");

        testProgram1.cleanup();
        mesh.cleanup();

        testProgram2.cleanup();
        textureMesh.cleanup();

        window.cleanup();
    }

}
