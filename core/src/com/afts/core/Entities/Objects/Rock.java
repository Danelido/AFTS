package com.afts.core.Entities.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Rock extends Entity {

    public Rock(Vector2 position, Vector2 size, Texture texture) {
        super(position, size, texture, EntityPointSetting.RECTANGLE);
        this.setRotation(0.f);
    }

    float d = 0.f;
    @Override
    public void update()
    {
        d += 20 * Gdx.graphics.getDeltaTime();

        this.setRotation(d);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(this.getTextureRegion(),
                this.getPosition().x, this.getPosition().y,
                this.getOrigin().x, this.getOrigin().y,
                this.getSize().x, this.getSize().y,
                1.f, 1.f,
                this.getRotation());
    }

    @Override
    public void dispose() {

    }
}
