#version 330

layout(location=0) in vec2 position;
layout(location=1) in vec2 textureCoords;

uniform vec2 translation;
uniform vec2 scale;
uniform vec2 screenSize;

out vec2 fragTextCoords;
void main() {
    vec2 scaledPos=(position*scale+translation)*2/screenSize;
    gl_Position=vec4(scaledPos,0,1);
    fragTextCoords=textureCoords;
}
