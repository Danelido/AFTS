package com.afts.core.Entities.Objects;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Rock extends Entity {

    public Rock(Vector2 position, Vector2 size,Texture texture, OnCollisionSetting setting) {
        super(position, size, 0.f , texture, CollisionPointSetup.RECTANGLE, setting);
        this.rotation = MathUtils.random(-90.f,90.f);
        this.color.set(MathUtils.random(0.1f,0.95f), MathUtils.random(0.1f,0.95f), MathUtils.random(0.1f,0.95f), 1.f);
    }

    @Override
    public void update(OrthographicCamera camera)
    {
    }

    @Override
    public void render(SpriteBatch batch)
    {
        batch.setColor(this.color);
        batch.draw(this.textureRegion,
                this.position.x, this.position.y,
                this.origin.x, this.origin.y,
                this.size.x, this.size.y,
                1.f, 1.f,
                this.rotation);
    }

    @Override
    public void dispose() {

    }
}
