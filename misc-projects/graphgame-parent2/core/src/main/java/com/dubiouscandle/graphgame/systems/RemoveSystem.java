package com.dubiouscandle.graphgame.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.dubiouscandle.graphgame.components.RemoveComponent;

/**
 * removes entities from the engine 
 */
public class RemoveSystem extends IteratingSystem {
	public RemoveSystem() {
		super(FAMILY);
	}

	private static final Family FAMILY = Family.all(RemoveComponent.class).get();

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		getEngine().removeEntity(entity);
	}
}
