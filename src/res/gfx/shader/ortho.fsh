#version 400

uniform sampler2D texture;

in vec2 pass_texCoords;

out vec4 out_color;

void main() {
	out_color = texture2D(texture, pass_texCoords);
}