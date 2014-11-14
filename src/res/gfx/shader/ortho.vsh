#version 400

uniform float width;
uniform float height;

uniform mat4 matrix;

layout(location = 0) in vec2 position;
layout(location = 1) in vec2 texCoords;

out vec2 pass_texCoords;

void main() {
	
	
	vec4 finalPos = matrix * vec4(position, 0, 1);
	
	finalPos.x /= width / 2;
	finalPos.x -= 1;
	
	finalPos.y /= height / -2;
	finalPos.y += 1;
	
	pass_texCoords = texCoords;
	
	gl_Position = finalPos;
}