package rendering;

import org.joml.Vector2f;

public class RectangleMesh extends GUIMesh {

    public RectangleMesh(float[] vertices, int[] indices){
        super(vertices, indices);

    }

    @Override
    public void render(Vector2f screenSize){
        super.render(screenSize);
//        GL46.glBindVertexArray(getVaoId());
//        GL46.glEnableVertexAttribArray(0);
//        GL46.glDrawElements(GL46.GL_TRIANGLES, getVertexCount(), GL46.GL_UNSIGNED_INT, 0);
//        GL46.glDisableVertexAttribArray(0);
//        GL46.glBindVertexArray(0);
    }

    @Override
    public void cleanup(){

        super.cleanup();
    }
}
