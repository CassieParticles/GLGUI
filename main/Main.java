package main;

import guiObjects.*;
import guiObjects.buttons.ButtonAction;
import guiObjects.buttons.ButtonGUI;
import guiObjects.buttons.TextBoxGUI;
import guiObjects.buttons.ToggleButtonGUI;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.system.CallbackI;
import utils.Input;
import utils.Timer;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL46;

public class Main {

    private Window window;
    private Timer timer;
    private Input input;

    private ButtonGUI button;

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
        window=new Window(900,900,"Title"); //Create window, timer and input
        timer=new Timer(60,60);
        input=new Input();

        window.init();// Initialise window and input
        input.init(window.getWindowHandle());

        button=new ButtonGUI(new Vector2f(100, 100), new Vector2f(200, 200), new Vector3f(0.8f, 0.4f, 0), input, new ButtonAction() {
            @Override
            public void performAction() {
                System.out.println("TEST:006");
            }
        });

        GL46.glClearColor(0.1f, 0.1f, 0.2f, 1.0f);  //Set the bg colour of the window
        window.loop();  //Swap buffers to render to screen
    }

    private void render(){
        window.loop();
        GL46.glClear(GL46.GL_COLOR_BUFFER_BIT | GL46.GL_DEPTH_BUFFER_BIT);
        Vector2f screenSize=new Vector2f(window.getWidth(),window.getHeight());
        button.render(screenSize);
    }

    private void update() throws Exception {
        if(input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)){
            window.close();
        }
        Vector2f screenSize=new Vector2f(window.getWidth(),window.getHeight());

        button.use(screenSize);

        input.updateInputs();
    }

    public void loop() throws Exception {
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

        window.cleanup();

         button.cleanup();
        
        Font.cleanupFonts();
    }

}
