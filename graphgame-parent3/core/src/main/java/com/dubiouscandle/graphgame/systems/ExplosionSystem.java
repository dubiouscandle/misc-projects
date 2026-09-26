package com.dubiouscandle.graphgame.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.dubiouscandle.graphgame.components.ContactComponent;
import com.dubiouscandle.graphgame.components.ExplosionComponent;
import com.dubiouscandle.graphgame.components.RemoveComponent;
import com.dubiouscandle.graphgame.components.TransformComponent;

public class ExplosionSystem extends IteratingSystem {
	private static final Family FAMILY = Family.all(ExplosionComponent.class).get();
	private World world;
	private ClosestBodyRayCastCallback rayCastCallback = new ClosestBodyRayCastCallback();

	public ExplosionSystem(World world) {
		super(FAMILY);
		this.world = world;
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		ExplosionComponent ec = ExplosionComponent.MAPPER.get(entity);

		if (ec.onImpact) {
			ContactComponent cc = ContactComponent.MAPPER.get(entity);

			if (cc == null) {
				throw new IllegalStateException("On impact requires ContactComponent.");
			}

			if (cc.beginContacts.size > 0) {
				ec.explodeNow = true;
			}
		}
		if (ec.explodeNow) {
			TransformComponent tc = TransformComponent.MAPPER.get(entity);

			Vector2 dest = new Vector2();
			Vector2 from = new Vector2();
			for (int i = 0; i < ec.numRays; i++) {
				float angle = MathUtils.PI2 * i / ec.numRays;
				float r = ec.maxRadius;
				from.set(tc.x, tc.y);
				dest.x = r * MathUtils.cos(angle) + tc.x;
				dest.y = r * MathUtils.sin(angle) + tc.y;
				rayCastCallback.reset();
				
				world.rayCast(rayCastCallback, from, dest);

				if (rayCastCallback.found()) {
					float power = ec.baseForce * (1 - rayCastCallback.minFraction);

					Vector2 impulse = dest.sub(from);
					impulse.nor().scl(power);

					rayCastCallback.closestFixture.getBody().applyLinearImpulse(impulse, rayCastCallback.point, true);
				}
			}
			entity.add(RemoveComponent.RC);
		}
	}

	private class ClosestBodyRayCastCallback implements RayCastCallback {
		float minFraction;
		Vector2 point = new Vector2();
		Fixture closestFixture;

		void reset() {
			minFraction = Float.MAX_VALUE;
			closestFixture = null;
			point.set(Float.NaN, Float.NaN);
		}

		@Override
		public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
			if (fraction < minFraction) {
				closestFixture = fixture;
				minFraction = fraction;
				this.point.set(point);
			}

			return 1;
		}

		boolean found() {
			return closestFixture != null;
		}
	}
}
