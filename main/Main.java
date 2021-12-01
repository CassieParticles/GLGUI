package main;

import GUIObjects.TextGUI;
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

    private TextGUI testText1;
    private TextGUI testText2;

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

        testText1=new TextGUI(new Vector2f(0,0),new Vector2f(1,1),"Question?",15,"src/testFiles/slabo.png","src/testFiles/slaboData.csv");
        testText2=new TextGUI(new Vector2f(0,-25),new Vector2f(1,1),"Answer!",15,"src/testFiles/slabo.png","src/testFiles/slaboData.csv");

        GL46.glClearColor(0.1f, 0.1f, 0.2f, 1.0f);
        window.loop();
    }

    private void render(){
        window.loop();
        GL46.glClear(GL46.GL_COLOR_BUFFER_BIT | GL46.GL_DEPTH_BUFFER_BIT);
        testText1.render(new Vector2f(window.getWidth(),window.getHeight()));
        testText2.render(new Vector2f(window.getWidth(),window.getHeight()));
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

        testText1.cleanup();
        testText2.cleanup();

        window.cleanup();
    }

}
