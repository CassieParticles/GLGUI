#version 330

layout(location=0) in vec2 position;

uniform vec2 translation;
uniform vec2 scale;
uniform vec2 screenSize;

void main() {
    vec2 scaledPos=(position*scale+translation)*2/screenSize;
    gl_Position=vec4(scaledPos,0,1);
}
