package main;

import guiObjects.*;
import guiObjects.buttons.ButtonAction;
import guiObjects.buttons.ButtonGUI;
import guiObjects.buttons.TextBoxGUI;
import org.joml.Vector2f;
import org.joml.Vector3f;
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

    private TextBoxGUI textBox;

    private ButtonGUI buttonTest;

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

//        buttonTest=new ButtonGUI(new Vector2f(50, 50), new Vector2f(200, 100), new Vector3f(0.3f, 0.2f, 0.8f), input, new ButtonAction() {
//            @Override
//            public void performAction() {
//                System.out.println("Test");
//            }
//        });

        textBox=new TextBoxGUI(new Vector2f(50,50),new Vector2f(200,100),new Vector3f(0.3f, 0.2f, 0.8f),new Vector3f(0.69f,0.75f,0.1f), input);
        textBox.initText("sus",15,"src/testFiles/slabo.png","src/testFiles/slaboData.csv","QWERTYUIOPASDFGHJKLZXCVBNM 0123456789");

        GL46.glClearColor(0.1f, 0.1f, 0.2f, 1.0f);
        window.loop();
    }

    private void render(){
        window.loop();
        GL46.glClear(GL46.GL_COLOR_BUFFER_BIT | GL46.GL_DEPTH_BUFFER_BIT);
        Vector2f screenSize=new Vector2f(window.getWidth(),window.getHeight());
        testText1.render(screenSize);
        testText2.render(screenSize);
//        buttonTest.render(screenSize);
        textBox.render(screenSize);
    }

    private void update() throws Exception {
        if(input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)){
            window.close();
        }
        Vector2f screenSize=new Vector2f(window.getWidth(),window.getHeight());
//        buttonTest.use(screenSize);
        textBox.use(screenSize);
        String acceptableCharacters="ABCDEFGHIJKLOMNOPQRSTUVWXYZ 0123456789";
//        for(char c:acceptableCharacters.toCharArray()){
//            if(input.isKeyPressed((int)c)){
//                System.out.println(c);
//            }
//        }
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

        testText1.cleanup();
        testText2.cleanup();

//        buttonTest.cleanup();
        textBox.cleanup();

        window.cleanup();
    }

}
