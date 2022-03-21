package guiObjects.buttons;

import guiObjects.GUI;
import guiObjects.TextureGUI;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;
import utils.Input;

public class ToggleButtonGUI extends GUI {

    private boolean enabled;

    private TextureGUI enabledTexture;
    private TextureGUI disabledTexture;

    private Input input;

    public ToggleButtonGUI(Vector2f position, Vector2f size, String enabledTextureFile, String disabledTextureFile, Input input) throws Exception {
        super(position, size);
        enabledTexture=new TextureGUI(position,size,enabledTextureFile);
        disabledTexture=new TextureGUI(position,size,disabledTextureFile);
        this.input=input;
        enabled=true;
    }

    public void setEnabled(boolean enabled){
        this.enabled=enabled;
    }

    public boolean getEnabled(){
        return enabled;
    }

    @Override
    public void render(Vector2f screenSize) {
        super.render(screenSize);
        if(enabled){
            enabledTexture.render(screenSize);
        }else{
            disabledTexture.render(screenSize);
        }
    }

    public void use(Vector2f screenSize){
        int[] mousePosition=input.getMousePos();

        mousePosition[0]-=screenSize.x/2;   //Transform mouse position into GUI space
        mousePosition[1]= (int) (mousePosition[1]*-1+screenSize.y/2);

        if(enabledTexture.pointInRectangle(new Vector2f(mousePosition[0],mousePosition[1]))&&input.isMouseButtonPressed(GLFW.GLFW_MOUSE_BUTTON_1)){
            enabled=!enabled;   //Toggle between enabled and diabled
        }
    }


    @Override
    public void cleanup(){
        enabledTexture.cleanup();
        disabledTexture.cleanup();
    }
}
