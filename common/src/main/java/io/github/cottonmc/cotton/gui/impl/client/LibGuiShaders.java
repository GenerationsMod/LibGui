package io.github.cottonmc.cotton.gui.impl.client;

import dev.architectury.event.events.client.ClientReloadShadersEvent;

import dev.architectury.injectables.annotations.ExpectPlatform;


import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.resource.ResourceFactory;
import net.minecraft.util.Identifier;

import io.github.cottonmc.cotton.gui.impl.LibGuiCommon;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public final class LibGuiShaders {
	private static @Nullable ShaderProgram tiledRectangle;

	static void register() {
		ClientReloadShadersEvent.EVENT.register(new ClientReloadShadersEvent() {
			@Override
			public void reload(ResourceFactory provider, ShadersSink sink) {
				// Register our core shaders.
				// The tiled rectangle shader is used for performant tiled texture rendering.
				try {
					sink.registerShader(LibGuiShaders.createProgram(provider, new Identifier(LibGuiCommon.MOD_ID, "tiled_rectangle"), VertexFormats.POSITION), program -> tiledRectangle = program);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		});
	}

	@ExpectPlatform
	private static ShaderProgram createProgram(ResourceFactory provider, Identifier identifier, VertexFormat position) throws IOException {
		throw new RuntimeException();
	}

	private static ShaderProgram assertPresent(ShaderProgram program, String name) {
		if (program == null) {
			throw new NullPointerException("Shader libgui:" + name + " not initialised!");
		}

		return program;
	}

	public static ShaderProgram getTiledRectangle() {
		return assertPresent(tiledRectangle, "tiled_rectangle");
	}
}
