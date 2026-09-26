package com.dubiouscandle.graphgame.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

public class ExplosionComponent implements Component {
	public static final ComponentMapper<ExplosionComponent> MAPPER = ComponentMapper.getFor(ExplosionComponent.class);

	public boolean onImpact = true;
	public boolean explodeNow = false;
	public float maxRadius;
	public float baseForce;
	public int numRays;

	public ExplosionComponent(float maxRadius, float baseForce, int numRays) {
		this.maxRadius = maxRadius;
		this.baseForce = baseForce;
		this.numRays = numRays;
	}

}
