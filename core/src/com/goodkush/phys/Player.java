package com.goodkush.phys;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;

public class Player extends Actor 
{
	private TextureRegion tex;
	public int curTile;
	
	//Constructor gives the player a texture and dimensions
	Player(TextureRegion t, int width, int height)
	{
		setSize(width, height);
		setOrigin(Align.center);
		tex = t;
		curTile = 0;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha)
	{
		batch.draw(tex, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
	}
	

}
