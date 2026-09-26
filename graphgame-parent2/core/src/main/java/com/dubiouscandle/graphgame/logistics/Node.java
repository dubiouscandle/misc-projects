package com.dubiouscandle.graphgame.logistics;

import com.badlogic.gdx.utils.Array;
import com.dubiouscandle.graphgame.Resource;
import com.dubiouscandle.graphgame.util.EnumIntMap;

public class Node {
	protected EnumIntMap<Resource> resources = new EnumIntMap<>(Resource.class);
	protected EnumIntMap<Resource> maxResources = new EnumIntMap<>(Resource.class);

	protected Array<Edge> outgoing = new Array<>();
	protected Array<Edge> ingoing = new Array<>();

	protected Node() {
	}
}
