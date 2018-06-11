package com.afts.core.Entities.Objects;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Block extends Entity{


    public Block(Vector2 position, Vector2 size, float rotation, Texture texture, OnCollisionSetting onCollisionSetting) {
        super(position, size, rotation, texture, CollisionPointSetup.RECTANGLE, onCollisionSetting);
    }

    public Block(Vector2 position, Vector2 size, float rotation, Texture texture, CollisionPointSetup collisionPointSetup, OnCollisionSetting onCollisionSetting) {
        super(position, size, rotation, texture, collisionPointSetup, onCollisionSetting);
    }

    @Override
    public void update(OrthographicCamera camera) {

    }

    @Override
    public void render(SpriteBatch batch) {
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
