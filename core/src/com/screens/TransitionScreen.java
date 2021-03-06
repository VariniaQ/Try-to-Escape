package com.screens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.trytoescape.game.TryToEscape;
import com.utilities.Action;

import components.AbstractScreen;

public class TransitionScreen extends AbstractScreen{
	//TODO implement
	private AbstractScreen from;
	private AbstractScreen to;
	
	private Transition outTransition;
	private Transition inTransition;
	
	private Action action;
	
	private SpriteBatch batch;
	private Viewport viewport;
	
	private TRANSITION_STATE state;
	
	private enum TRANSITION_STATE {
		OUT,
		IN;
	}
	
	public TransitionScreen(TryToEscape game) {
		super(game);
		batch = new SpriteBatch();
		viewport = new ScreenViewport();
	}
	
	@Override
	public void dispose() {
		
	}
	
	@Override
	public void hide() {
		
	}
	
	@Override
	public void pause() {
		
	}
	
	public void update(float delta) {
		if (state == TRANSITION_STATE.OUT) {
			outTransition.update(delta);
			if (outTransition.isFinished()) {
				action.action();
				state = TRANSITION_STATE.IN;
				return;
			}
		} else if (state == TRANSITION_STATE.IN) {
			inTransition.update(delta);
			if (inTransition.isFinished()) {
				getGame().setScreen(to);
			}
		}
	}



	@Override
	public void render(float delta) {
		if (state == TRANSITION_STATE.OUT) {
			from.render(delta);
			
			viewport.apply();
			outTransition.render(delta, batch);
		} else if (state == TRANSITION_STATE.IN) {
			to.render(delta);
			
			viewport.apply();
			inTransition.render(delta, batch);
		}
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
		to.resize(width, height);
		from.resize(width, height);
	}

	@Override
	public void resume() {
	}
	
	@Override
	public void show() {
	}
	
	public void startTransition(AbstractScreen from, AbstractScreen to, Transition out, Transition in, Action action) {
		this.from = from;
		this.to = to;
		this.outTransition = out;
		this.inTransition = in;
		this.action = action;
		this.state = TRANSITION_STATE.OUT;
		getGame().setScreen(this);
	}
}
