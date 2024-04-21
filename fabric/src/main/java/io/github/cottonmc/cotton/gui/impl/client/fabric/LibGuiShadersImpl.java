package io.github.cottonmc.cotton.gui.impl.client.fabric;

import net.fabricmc.fabric.impl.client.rendering.FabricShaderProgram;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.resource.ResourceFactory;
import net.minecraft.util.Identifier;

import java.io.IOException;

public class LibGuiShadersImpl {
	public static ShaderProgram createProgram(ResourceFactory provider, Identifier id, VertexFormat format) throws IOException {
		return new FabricShaderProgram(provider, id, format);
	}
}
