#version 330

in vec2 fragTextCoords;

uniform sampler2D textureSampler;

out vec4 FragColour;

void main() {
    FragColour=texture(textureSampler,fragTextCoords);
}
