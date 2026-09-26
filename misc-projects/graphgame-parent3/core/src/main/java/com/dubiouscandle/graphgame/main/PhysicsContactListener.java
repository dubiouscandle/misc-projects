package com.dubiouscandle.graphgame.main;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.dubiouscandle.graphgame.components.ContactComponent;
import com.dubiouscandle.graphgame.components.ContactComponent.ContactEvent;

public class PhysicsContactListener implements ContactListener {
	public PhysicsContactListener() {
	}

	@Override
	public void beginContact(Contact contact) {
		Entity entityA = (Entity) contact.getFixtureA().getBody().getUserData();
		Entity entityB = (Entity) contact.getFixtureB().getBody().getUserData();

		ContactComponent ccA = ContactComponent.MAPPER.get(entityA);
		ContactComponent ccB = ContactComponent.MAPPER.get(entityB);

		if (ccA != null) {
			ContactEvent ceA = new ContactEvent(contact, false);
			ccA.beginContacts.add(ceA);
		}
		if (ccB != null) {
			ContactEvent ceB = new ContactEvent(contact, true);
			ccB.beginContacts.add(ceB);
		}
	}

	@Override
	public void endContact(Contact contact) {
		Entity entityA = (Entity) contact.getFixtureA().getBody().getUserData();
		Entity entityB = (Entity) contact.getFixtureB().getBody().getUserData();

		ContactComponent ccA = ContactComponent.MAPPER.get(entityA);
		ContactComponent ccB = ContactComponent.MAPPER.get(entityB);

		if (ccA != null) {
			ContactEvent ceA = new ContactEvent(contact, false);
			ccA.endContacts.add(ceA);
		}
		if (ccB != null) {
			ContactEvent ceB = new ContactEvent(contact, true);
			ccB.endContacts.add(ceB);
		}
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {

	}

}
