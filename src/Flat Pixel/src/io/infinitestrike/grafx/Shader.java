package io.infinitestrike.grafx;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;

import org.lwjgl.opengl.ARBFragmentShader;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Vector2f;

import io.infinitestrike.core.LogBot;
import io.infinitestrike.core.LogBot.Status;
/**
 * Quick and Dirty Shader Implementation based on the source code on the LWJGL Website.
 * See http://wiki.lwjgl.org/wiki/GLSL_Shaders_with_LWJGL.html
 * @author Draven Lewis
 *
 */
public class Shader {

	private String vertShader = "";
	private String fragShader = "";
	private int fragShaderID = -1;
	private int vertShaderID = -1;
	private int program = 0;
	private boolean validProgram = false;

	protected HashMap<String, Integer> uniforms = new HashMap<String, Integer>();
	protected HashMap<String, Integer> attributes = new HashMap<String, Integer>();
	
	public static final int SHADER_VERTEX = ARBVertexShader.GL_VERTEX_SHADER_ARB;
	public static final int SHADER_FRAGMENT = ARBFragmentShader.GL_FRAGMENT_SHADER_ARB;

	protected Shader() {

	}

	public static Shader getVertexOnlyShader(String s) {
		Shader shader = new Shader();
		try {
			shader.vertShader = s;
			shader.vertShaderID = shader.createShader(shader.vertShader, Shader.SHADER_VERTEX);
			shader.compile();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return shader;
	}

	public static Shader getFragmentOnlyShader(String s) {
		Shader shader = new Shader();
		try {
			shader.fragShader = s;
			shader.fragShaderID = shader.createShader(shader.fragShader, Shader.SHADER_FRAGMENT);
			shader.compile();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return shader;
	}

	public static Shader getShader(String vert, String frag) {
		Shader shader = new Shader();
		try {
			shader.vertShader = vert;
			shader.fragShader = frag;
			shader.vertShaderID = shader.createShader(shader.vertShader, Shader.SHADER_VERTEX);
			shader.fragShaderID = shader.createShader(shader.fragShader, Shader.SHADER_FRAGMENT);
			shader.compile();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return shader;
	}

	public void compile() {
		int program = ARBShaderObjects.glCreateProgramObjectARB();
		if (program == 0) {
			LogBot.logData(Status.ERROR, "Cannont Create Program Object!");
			return;
		}
		this.program = program;
		
		if (this.program != 0) {
			if(this.vertShaderID != -1) {
				ARBShaderObjects.glAttachObjectARB(program, this.vertShaderID);
			}
			if(this.fragShaderID != -1) {
				ARBShaderObjects.glAttachObjectARB(program, this.fragShaderID);
			}

			ARBShaderObjects.glLinkProgramARB(program);
			if (ARBShaderObjects.glGetObjectParameteriARB(program,
					ARBShaderObjects.GL_OBJECT_LINK_STATUS_ARB) == GL11.GL_FALSE) {
				return;
			}

			ARBShaderObjects.glValidateProgramARB(program);
			if (ARBShaderObjects.glGetObjectParameteriARB(program,
					ARBShaderObjects.GL_OBJECT_VALIDATE_STATUS_ARB) == GL11.GL_FALSE) {
				LogBot.logData(Status.ERROR, getLogInfo(program));
				return;
			}
			
			this.validProgram = true;
		} else {
			LogBot.logData(Status.ERROR, "Invalid Program File.");
		}
	}

	public boolean isProgramValid() {
		return this.validProgram;
	}
	
	public void bind() {
		if(this.isProgramValid()) {
			ARBShaderObjects.glUseProgramObjectARB(program);
		}
	}
	
	public void unbind() {
		if(this.isProgramValid()) {
			ARBShaderObjects.glUseProgramObjectARB(0);
		}
	}

	private int createShader(String filename, int shaderType) throws Exception {
		int shader = 0;
		try {
			shader = ARBShaderObjects.glCreateShaderObjectARB(shaderType);

			if (shader == 0)
				return 0;

			ARBShaderObjects.glShaderSourceARB(shader, readFileAsString(filename));
			ARBShaderObjects.glCompileShaderARB(shader);

			if (ARBShaderObjects.glGetObjectParameteriARB(shader,
					ARBShaderObjects.GL_OBJECT_COMPILE_STATUS_ARB) == GL11.GL_FALSE)
				throw new RuntimeException("Error creating shader: " + getLogInfo(shader));

			return shader;
		} catch (Exception exc) {
			ARBShaderObjects.glDeleteObjectARB(shader);
			throw exc;
		}
	}

	private static String getLogInfo(int obj) {
		return ARBShaderObjects.glGetInfoLogARB(obj,
				ARBShaderObjects.glGetObjectParameteriARB(obj, ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB));
	}

	private String readFileAsString(String filename) throws Exception {
		StringBuilder source = new StringBuilder();

		FileInputStream in = new FileInputStream(filename);

		Exception exception = null;

		BufferedReader reader;
		try {
			reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

			Exception innerExc = null;
			try {
				String line;
				while ((line = reader.readLine()) != null)
					source.append(line).append('\n');
			} catch (Exception exc) {
				exception = exc;
			} finally {
				try {
					reader.close();
				} catch (Exception exc) {
					if (innerExc == null)
						innerExc = exc;
					else
						exc.printStackTrace();
				}
			}

			if (innerExc != null)
				throw innerExc;
		} catch (Exception exc) {
			exception = exc;
		} finally {
			try {
				in.close();
			} catch (Exception exc) {
				if (exception == null)
					exception = exc;
				else
					exc.printStackTrace();
			}

			if (exception != null)
				throw exception;
		}

		return source.toString();
	}

	
	private void fetchUniforms() {
		int len = ARBShaderObjects.glGetObjectParameteriARB(program, GL20.GL_ACTIVE_UNIFORMS);
		//max length of all uniforms stored in program
		int strLen = ARBShaderObjects.glGetObjectParameteriARB(program, GL20.GL_ACTIVE_UNIFORM_MAX_LENGTH);
		
		for (int i=0; i<len; i++) {
			String name = ARBShaderObjects.glGetActiveUniformARB(program, i, strLen);
			int id = ARBShaderObjects.glGetUniformLocationARB(program, name);
			uniforms.put(name, id);
		}
	}
	
	private void fetchAttributes() {
		int len = ARBShaderObjects.glGetObjectParameteriARB(program, GL20.GL_ACTIVE_ATTRIBUTES);
		//max length of all uniforms stored in program
		int strLen = ARBShaderObjects.glGetObjectParameteriARB(program, GL20.GL_ACTIVE_ATTRIBUTE_MAX_LENGTH); 
		for (int i=0; i<len; i++) {
			String name = ARBVertexShader.glGetActiveAttribARB(program, i, strLen);
			int id = ARBVertexShader.glGetAttribLocationARB(program, name);
			uniforms.put(name, id);
		}
	}

	/**
	 * Returns the ID of the given uniform.
	 * @param name the uniform name
	 * @return the ID (location) in the shader program
	 */
	public int getUniformID(String name) {
		Integer locI = uniforms.get(name);
		int location = locI==null ? -1 : locI.intValue();
		if (location!=-1)
			return location;
		location = ARBShaderObjects.glGetUniformLocationARB(program, name);
		uniforms.put(name, location); 
		return location;
	}

	/**
	 * Returns the ID of the given attribute.
	 * @param name the attribute name
	 * @return the ID (location) in the shader program
	 */
	public int getAttributeID(String name) {
		int location = attributes.get(name);
		if (location!=-1)
			return location;
		location = ARBVertexShader.glGetAttribLocationARB(program, name);
		attributes.put(name, location); 
		return location;
	}

	/**
	 * Returns the names of all active attributes that were found
	 * when linking the program.
	 * @return an array list of active attribute names
	 */
	public String[] getAttributes() {
		return attributes.keySet().toArray(new String[attributes.size()]);
	}
	
	/**
	 * Returns the names of all active uniforms that were found
	 * when linking the program.
	 * @return an array list of active uniform names
	 */
	public String[] getUniformNames() {
		return uniforms.keySet().toArray(new String[uniforms.size()]);
	}
	
	/**
	 * Enables the vertex array -- in strict mode, if the vertex attribute
	 * is not found (or it's inactive), an IllegalArgumentException will
	 * be thrown. If strict mode is disabled and the vertex attribute 
	 * is not found, this method will return <tt>false</tt> otherwise it
	 * will return <tt>true</tt>.
	 * 
	 * @param name the name of the vertex attribute to enable
	 * @return false if strict mode is disabled and this attribute couldn't be found
	 */
	public boolean enableVertexAttribute(String name) {
		int id = getAttributeID(name);
		if (id==-1) return false;
		ARBVertexShader.glEnableVertexAttribArrayARB(id);
		return true;
	}
	
	/**
	 * Disables the vertex array -- in strict mode, if the vertex attribute
	 * is not found (or it's inactive), an IllegalArgumentException will
	 * be thrown. If strict mode is disabled and the vertex attribute 
	 * is not found, this method will return <tt>false</tt> otherwise it
	 * will return <tt>true</tt>.
	 * 
	 * @param name the name of the vertex attribute to disable
	 * @return false if strict mode is disabled and this attribute couldn't be found
	 */
	public boolean disableVertexAttribute(String name) {
		int id = getAttributeID(name);
		if (id==-1) return false;
		ARBVertexShader.glDisableVertexAttribArrayARB(id);
		return true;
	}
	
//	public void setVertexAttribute(String name, int size, int type, boolean normalize, int stride, FloatBuffer buffer) {
//		ARBVertexShader.glVertexAttrib
//	}
	
	/**
	 * Sets the value of an RGBA vec4 uniform to the given color
	 * @param name the RGBA vec4 uniform
	 * @param color the color to assign
	 */
	public void setUniform4f(String name, Color color) {
		setUniform4f(name, color.r, color.g, color.b, color.a);
	}
	
	/**
	 * Sets the value of a vec2 uniform to the given Vector2f.
	 * @param name the vec2 uniform
	 * @param vec the vector to use
	 */
	public void setUniform2f(String name, Vector2f vec) {
		setUniform2f(name, vec.x, vec.y);
	}

	/**
	 * Retrieves data from a uniform and places it in the given buffer. If 
	 * strict mode is enabled, this will throw an IllegalArgumentException
	 * if the given uniform is not 'active' -- i.e. if GLSL determined that
	 * the shader isn't using it. If strict mode is disabled, this method will
	 * return <tt>true</tt> if the uniform was found, and <tt>false</tt> otherwise.
	 * 
	 * @param name the name of the uniform
	 * @param buf the buffer to place the data
	 * @return true if the uniform was found, false if there is no active uniform by that name
	 */
	public boolean getUniform(String name, FloatBuffer buf) {
		int id = getUniformID(name);
		if (id==-1) return false;
		ARBShaderObjects.glGetUniformARB(program, id, buf);
		return true;
	}
	
	/**
	 * Retrieves data from a uniform and places it in the given buffer. If 
	 * strict mode is enabled, this will throw an IllegalArgumentException
	 * if the given uniform is not 'active' -- i.e. if GLSL determined that
	 * the shader isn't using it. If strict mode is disabled, this method will
	 * return <tt>true</tt> if the uniform was found, and <tt>false</tt> otherwise.
	 * 
	 * @param name the name of the uniform
	 * @param buf the buffer to place the data
	 * @return true if the uniform was found, false if there is no active uniform by that name
	 */
	public boolean getUniform(String name, IntBuffer buf) {
		int id = getUniformID(name);
		if (id==-1) return false;
		ARBShaderObjects.glGetUniformARB(program, id, buf);
		return true;
	}
	
	/**
	 * Whether the shader program was linked with the active uniform by the given name. A
	 * uniform might be "inactive" even if it was declared at the top of a shader;
	 * if GLSL finds that a uniform isn't needed (i.e. not used in shader), then
	 * it will not be active.
	 * @param name the name of the uniform
	 * @return true if this shader program could find the active uniform
	 */
	public boolean hasUniform(String name) {
		return uniforms.containsKey(name);
	}
	
	/**
	 * Whether the shader program was linked with the active attribute by the given name. A
	 * attribute might be "inactive" even if it was declared at the top of a shader;
	 * if GLSL finds that a attribute isn't needed (i.e. not used in shader), then
	 * it will not be active.
	 * @param name the name of the attribute
	 * @return true if this shader program could find the active attribute
	 */
	public boolean hasAttribute(String name) {
		return attributes.containsKey(name);
	}

	/**
	 * Sets the value of a float uniform.
	 * @param name the uniform by name
	 * @param f the float value
	 */
	public void setUniform1f(String name, float f) {
		int id = getUniformID(name);
		if (id==-1) return;
		ARBShaderObjects.glUniform1fARB(id, f);
	}
	
	/**
	 * Sets the value of a sampler2D uniform.
	 * @param name the uniform by name
	 * @param i the integer / active texture (e.g. 0 for TEXTURE0)
	 */
	public void setUniform1i(String name, int i) {
		int id = getUniformID(name);
		if (id==-1) return;
		ARBShaderObjects.glUniform1iARB(id, i);
	}
	
	/**
	 * Sets the value of a vec2 uniform.
	 * @param name the uniform by name
	 * @param a vec.x / tex.s
	 * @param b vec.y / tex.t
	 */
	public void setUniform2f(String name, float a, float b) {
		int id = getUniformID(name);
		if (id==-1) return;
		ARBShaderObjects.glUniform2fARB(id, a, b);
	}
	
	/**
	 * Sets the value of a vec3 uniform.
	 * @param name the uniform by name
	 * @param a vec.x / color.r / tex.s
	 * @param b vec.y / color.g / tex.t
	 * @param c vec.z / color.b / tex.p
	 */
	public void setUniform3f(String name, float a, float b, float c) {
		int id = getUniformID(name);
		if (id==-1) return;
		
		ARBShaderObjects.glUniform3fARB(id, a, b, c);
	}

	/**
	 * Sets the value of a vec4 uniform.
	 * @param name the uniform by name
	 * @param a vec.x / color.r
	 * @param b vec.y / color.g
	 * @param c vec.z / color.b 
	 * @param d vec.w / color.a 
	 */
	public void setUniform4f(String name, float a, float b, float c, float d) {
		int id = getUniformID(name);
		if (id==-1) return;
		ARBShaderObjects.glUniform4fARB(id, a, b, c, d);
	}
	
	/**
	 * Sets the value of a ivec2 uniform.
	 * @param name the uniform by name
	 * @param a vec.x / tex.s
	 * @param b vec.y / tex.t
	 */
	public void setUniform2i(String name, int a, int b) {
		int id = getUniformID(name);
		if (id==-1) return;
		ARBShaderObjects.glUniform2iARB(id, a, b);
	}

	/**
	 * Sets the value of a ivec3 uniform.
	 * @param name the uniform by name
	 * @param a vec.x / color.r
	 * @param b vec.y / color.g
	 * @param c vec.z / color.b 
	 */
	public void setUniform3i(String name, int a, int b, int c) {
		int id = getUniformID(name);
		if (id==-1) return;
		ARBShaderObjects.glUniform3iARB(id, a, b, c);
	}
	
	/**
	 * Sets the value of a ivec4 uniform.
	 * @param name the uniform by name
	 * @param a vec.x / color.r
	 * @param b vec.y / color.g
	 * @param c vec.z / color.b 
	 * @param d vec.w / color.a 
	 */
	public void setUniform4i(String name, int a, int b, int c, int d) {
		int id = getUniformID(name);
		if (id==-1) return;
		ARBShaderObjects.glUniform4iARB(id, a, b, c, d);
	}
	
	public void setMatrix2(String name, boolean transpose, FloatBuffer buf) {
		int id = getUniformID(name);
		if (id==-1) return;
		ARBShaderObjects.glUniformMatrix2ARB(id, transpose, buf);
	}
	
	public void setMatrix3(String name, boolean transpose, FloatBuffer buf) {
		int id = getUniformID(name);
		if (id==-1) return;
		ARBShaderObjects.glUniformMatrix3ARB(id, transpose, buf);
	}

	public void setMatrix4(String name, boolean transpose, FloatBuffer buf) {
		int id = getUniformID(name);
		if (id==-1) return;
		ARBShaderObjects.glUniformMatrix4ARB(id, transpose, buf);
	}
}
