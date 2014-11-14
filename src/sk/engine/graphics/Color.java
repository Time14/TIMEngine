package sk.engine.graphics;

import sk.engine.vector.Vector4f;

public class Color {
	public static final String FORMAT_RGBA = "RGBA";
	public static final String FORMAT_ARGB = "ARGB";
	public static final String FORMAT_ABGR = "ABGR";
	
	public static final Vector4f RED = new Vector4f(1, 0, 0, 1);
	public static final Vector4f GREEN = new Vector4f(0, 1, 0, 1);
	public static final Vector4f BLUE = new Vector4f(0, 0, 1, 1);
	public static final Vector4f PURPLE = new Vector4f(1, 0, 1, 1);
	public static final Vector4f YELLOW = new Vector4f(1, 1, 0, 1);
	public static final Vector4f CYAN = new Vector4f(0, 1, 1, 1);
	public static final Vector4f BLACK = new Vector4f(0, 0, 0, 1);
	public static final Vector4f WHITE = new Vector4f(1, 1, 1, 1);
	
	public int value;
	
	private String format;
	
	public Color(int color, String format) {
		value = color;
		this.format = format;
	}
	
	public Color(int color, String in, String out) {
		format = out;
		value = convert(color, in, out);
	}
	
	public Color(Vector4f color, String format) {
		this.format = format;
		
		value = (byte)(color.x * 255) << 24 | (byte)(color.y * 255) << 16 | (byte)(color.z * 255) << 8 | (byte)(color.w * 255);
	}
	
	public Color(Vector4f color, String in, String out) {
		
		format = out;
		
		value = 0;
		
		for(int i = 0; i < out.length(); i++) {
			for(int j = 0; j < in.length(); j++) {
				String cc = new String(new char[]{out.charAt(i), in.charAt(j)});
				switch(cc) {
				case "RR":
					value = value | ((int)(color.getData()[j] * 255)) << (8 * (out.length() - i - 1));
					break;
				case "GG":
					value = value | ((int)(color.getData()[j] * 255)) << (8 * (out.length() - i - 1));
					break;
				case "BB":
					value = value | ((int)(color.getData()[j] * 255)) << (8 * (out.length() - i - 1));
					break;
				case "AA":
					value = value | ((int)(color.getData()[j] * 255)) << (8 * (out.length() - i - 1));
					break;
				}
			}
		}
	}
	
	public int getR() {
		return (value & 0xff << (format.length() - 1 - format.indexOf('R'))) >> (format.length() - 1 - format.indexOf('R'));
	}
	
	public String toString() {
		return Integer.toHexString(value);
	}
	
	public static final int convert(int color, String in, String out) {
		int r = color & 0xff << (8 * (in.length() - 1 - in.indexOf('R')));
		int g = color & 0xff << (8 * (in.length() - 1 - in.indexOf('G')));
		int b = color & 0xff << (8 * (in.length() - 1 - in.indexOf('B')));
		int a = color & 0xff << (8 * (in.length() - 1 - in.indexOf('A')));
		
		r  = r >> (8 * (in.length() - 1 - in.indexOf('R')));
		g  = g >> (8 * (in.length() - 1 - in.indexOf('G')));
		b  = b >> (8 * (in.length() - 1 - in.indexOf('B')));
		a  = a >> (8 * (in.length() - 1 - in.indexOf('A')));
		
		r = r & 0xff;
		g = g & 0xff;
		b = b & 0xff;
		a = a & 0xff;
		
		return	r << (8 * (in.length() - 1 - out.indexOf('R'))) |
				g << (8 * (in.length() - 1 - out.indexOf('G'))) |
				b << (8 * (in.length() - 1 - out.indexOf('B'))) |
				a << (8 * (in.length() - 1 - out.indexOf('A')));
	}
}