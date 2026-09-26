package com.dubiouscandle.graphgame.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.physics.box2d.Joint;

public class JointComponent implements Component {
	public static final ComponentMapper<JointComponent> MAPPER = ComponentMapper.getFor(JointComponent.class);

	public Joint joint;

	public JointComponent(Joint joint) {
		this.joint = joint;
	}
}
