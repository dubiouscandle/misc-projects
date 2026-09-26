package com.dubiouscandle.graphgame.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Array;

public class ContactComponent implements Component {
	public static final ComponentMapper<ContactComponent> MAPPER = ComponentMapper.getFor(ContactComponent.class);

	public Array<ContactEvent> beginContacts = new Array<>();
	public Array<ContactEvent> endContacts = new Array<>();

	public static class ContactEvent {
		public Fixture fixtureA, fixtureB;

		public ContactEvent(Contact contact, boolean switchOrdering) {
			if (!switchOrdering) {
				fixtureA = contact.getFixtureA();
				fixtureB = contact.getFixtureB();
			} else {
				fixtureA = contact.getFixtureB();
				fixtureB = contact.getFixtureA();
			}
		}
	}
}
