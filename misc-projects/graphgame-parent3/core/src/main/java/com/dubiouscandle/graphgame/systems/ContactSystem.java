package com.dubiouscandle.graphgame.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.dubiouscandle.graphgame.components.ContactComponent;

public class ContactSystem extends IteratingSystem {
	public static final Family FAMILY = Family.one(ContactComponent.class).get();

	public ContactSystem() {
		super(FAMILY);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		ContactComponent cc = ContactComponent.MAPPER.get(entity);
		cc.beginContacts.clear();
		cc.endContacts.clear();
	}

}
