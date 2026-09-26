package com.dubiouscandle.main;

import com.badlogic.gdx.Game;
import com.dubiouscandle.fluidsimulation.Solver;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
	Renderer renderer;
	Solver solver;
	
    @Override
    public void create() {
    	solver = new Solver();
    	renderer = new Renderer(solver);
    }

    @Override
    public void dispose() {
    	renderer.dispose();
    }
}
