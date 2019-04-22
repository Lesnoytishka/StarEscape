package ru.lesnoytishka.game;

import com.badlogic.gdx.Game;

import ru.lesnoytishka.game.screens.MenuScreen;

public class StarEscape extends Game {

	@Override
	public void create() {
		setScreen(new MenuScreen(this));
	}
}
