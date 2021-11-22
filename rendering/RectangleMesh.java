package rendering;

import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL46;

public class RectangleMesh extends Mesh{
    public RectangleMesh(float[] vertices, int[] indices) {
        super(vertices, indices);
    }

    @Override
    public void render(Program program, Vector2f screenSize){
        super.render(program,screenSize);
    }
}
