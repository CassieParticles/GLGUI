package rendering;

import org.joml.Vector2f;
import org.lwjgl.opengl.GL46;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;

public class TextureMesh extends GUIMesh {
    private int textureVboId;
    private int textureId;

    public TextureMesh(float[] vertices, int[] indices,float[] textureCoords, int textureId){
        super(vertices, indices);


        this.textureId=textureId;
        FloatBuffer textureBuffer=null;
        try{
            GL46.glBindVertexArray(vaoId);

            textureVboId=GL46.glGenBuffers();
            textureBuffer= MemoryUtil.memAllocFloat(textureCoords.length);
            textureBuffer.put(textureCoords).flip();
            GL46.glBindBuffer(GL46.GL_ARRAY_BUFFER,textureVboId);
            GL46.glBufferData(GL46.GL_ARRAY_BUFFER,textureBuffer,GL46.GL_STATIC_DRAW);
            GL46.glEnableVertexAttribArray(1);
            GL46.glVertexAttribPointer(1,2,GL46.GL_FLOAT,false,0,0);
        }finally{
            if(textureBuffer!=null){
                MemoryUtil.memFree(textureBuffer);
            }
            GL46.glBindVertexArray(0);
        }
    }

    @Override
    public void render( Vector2f screenSize){

        GL46.glActiveTexture(GL46.GL_TEXTURE0); //Activate the texture port 0 and bind the texture to it
        GL46.glBindTexture(GL46.GL_TEXTURE_2D,textureId);

        GL46.glBindVertexArray(getVaoId());

        GL46.glEnableVertexAttribArray(0);  //Activate vertex buffer objects for position and texture-coordinates
        GL46.glEnableVertexAttribArray(1);

        GL46.glDrawElements(GL46.GL_TRIANGLES, getVertexCount(), GL46.GL_UNSIGNED_INT, 0);

        GL46.glDisableVertexAttribArray(0); //De-activate vertex buffer objects
        GL46.glDisableVertexAttribArray(1);

        GL46.glBindVertexArray(0);
    }

    @Override
    public void cleanup(){

        GL46.glDeleteBuffers(textureVboId);
        super.cleanup();
    }
}
