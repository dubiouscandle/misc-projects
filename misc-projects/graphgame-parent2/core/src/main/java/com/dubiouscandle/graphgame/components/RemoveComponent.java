package com.dubiouscandle.graphgame.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

public class RemoveComponent implements Component {
	public static final ComponentMapper<RemoveComponent> MAPPER = ComponentMapper.getFor(RemoveComponent.class);
	
	public static final RemoveComponent RC = new RemoveComponent();
	
	private RemoveComponent() {
	}
}
