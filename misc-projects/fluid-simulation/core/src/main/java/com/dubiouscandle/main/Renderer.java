package com.dubiouscandle.main;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.dubiouscandle.fluidsimulation.Solver;

public class Renderer implements Screen {
	private Solver solver;
	private SpriteBatch batch;
	private FitViewport viewport;

	public Renderer(Solver solver) {

		this.solver = solver;
		viewport = new FitViewport(solver.getWorldWidth(), solver.getWorldHeight());
		batch = new SpriteBatch();
	}

	@Override
	public void show() {
	}

	@Override
	public void render(float delta) {
		solver.step(delta);

		batch.setProjectionMatrix(viewport.getCamera().combined);
		batch.begin();

		batch.end();

		ShapeRenderer shapeRenderer = new ShapeRenderer();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {

	}

}
