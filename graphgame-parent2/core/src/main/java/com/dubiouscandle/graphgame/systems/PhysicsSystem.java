package com.dubiouscandle.graphgame.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.physics.box2d.Transform;
import com.badlogic.gdx.physics.box2d.World;
import com.dubiouscandle.graphgame.components.BodyComponent;
import com.dubiouscandle.graphgame.components.JointComponent;
import com.dubiouscandle.graphgame.components.RemoveComponent;
import com.dubiouscandle.graphgame.components.TransformComponent;

/**
 * updates the world, syncs transform component and body component, and removes joints and bodies from world
 */
public class PhysicsSystem extends EntitySystem {
	private ImmutableArray<Entity> bodyEntities;
	private ImmutableArray<Entity> jointEntities;

	private static final Family BODY_FAMILY = Family.all(BodyComponent.class, TransformComponent.class).get();
	private static final Family JOINT_FAMILY = Family.all(JointComponent.class).get();

	@Override
	public void addedToEngine(Engine engine) {
		bodyEntities = engine.getEntitiesFor(BODY_FAMILY);
		jointEntities = engine.getEntitiesFor(JOINT_FAMILY);
	}

	private World world;

	public PhysicsSystem(World world) {
		this.world = world;
	}

	@Override
	public void update(float deltaTime) {
		world.step(deltaTime, 6, 2);

		for (Entity entity : bodyEntities) {
			processBodyEntity(entity);
		}
		for (Entity entity : jointEntities) {
			processJointEntity(entity);
		}
	}

	protected void processBodyEntity(Entity entity) {
		BodyComponent bc = BodyComponent.MAPPER.get(entity);
		TransformComponent tc = TransformComponent.MAPPER.get(entity);
		RemoveComponent rc = RemoveComponent.MAPPER.get(entity);

		if (bc.body.getUserData() != entity) {
			throw new IllegalStateException("Body does not reference entity.");
		}

		if (rc != null) {
			world.destroyBody(bc.body);
		}

		Transform transform = bc.body.getTransform();

		tc.x = transform.getPosition().x;
		tc.y = transform.getPosition().y;
		tc.rotationAngleRad = transform.getRotation();
	}

	protected void processJointEntity(Entity entity) {
		JointComponent jc = JointComponent.MAPPER.get(entity);
		RemoveComponent rc = RemoveComponent.MAPPER.get(entity);

		if (rc != null) {
			world.destroyJoint(jc.joint);
		}
	}

}
