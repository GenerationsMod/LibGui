package io.github.cottonmc.cotton.gui.impl.client.forge;

import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.resource.ResourceFactory;
import net.minecraft.util.Identifier;

import java.io.IOException;

public class LibGuiShadersImpl {
	public static ShaderProgram createProgram(ResourceFactory provider, Identifier identifier, VertexFormat position) throws IOException {
		return new ShaderProgram(provider, identifier, position);
	}
}
