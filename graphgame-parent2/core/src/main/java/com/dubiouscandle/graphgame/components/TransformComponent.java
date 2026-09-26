package com.dubiouscandle.graphgame.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

public class TransformComponent implements Component {
	public static final ComponentMapper<TransformComponent> MAPPER = ComponentMapper.getFor(TransformComponent.class);

	public float x, y;
	public float rotationAngleRad = 0;
	public float scaleX = 1, scaleY = 1;

	public TransformComponent() {
	}
}
