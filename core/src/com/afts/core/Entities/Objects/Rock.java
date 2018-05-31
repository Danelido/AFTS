package com.afts.core.Entities.Objects;

import com.afts.core.Utility.StaticSettings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Rock extends Entity {

    public Rock(Vector2 position, Vector2 size, Texture texture) {
        super(position, size, texture, EntityPointSetting.RECTANGLE);
        this.rotation = MathUtils.random(-90.f,90.f);
        this.deAccelerationRate = 300.f;
        this.collideForce = 10.f;

        this.color.set(MathUtils.random(0.1f,1.f),MathUtils.random(0.1f,1.f),MathUtils.random(0.1f,1.f),1.f);
    }


    @Override
    public void update(OrthographicCamera camera)
    {
//        if(this.position.y - camera.position.y < -(StaticSettings.GAME_HEIGHT / 2.f) * camera.zoom)
//        {
//            this.position.y = (StaticSettings.GAME_HEIGHT / 2.f) * camera.zoom +  camera.position.y ;
//        }


        if(this.velocity.x > 0.f)
        {
            this.velocity.x -= this.deAccelerationRate * Gdx.graphics.getDeltaTime();
            if(this.velocity.x <= 0.f)
            {
                this.velocity.x = 0.f;
            }
        }

        if(this.velocity.x < 0.f)
        {
            this.velocity.x += this.deAccelerationRate * Gdx.graphics.getDeltaTime();
            if(this.velocity.x >= 0.f)
            {
                this.velocity.x = 0.f;
            }
        }

        if(this.velocity.y > 0.f)
        {
            this.velocity.y -= this.deAccelerationRate * Gdx.graphics.getDeltaTime();
            if(this.velocity.y <= 0.f)
            {
                this.velocity.y = 0.f;
            }
        }

        if(this.velocity.y < 0.f)
        {
            this.velocity.y += this.deAccelerationRate * Gdx.graphics.getDeltaTime();
            if(this.velocity.y >= 0.f)
            {
                this.velocity.y = 0.f;
            }
        }

        this.position.x += this.velocity.x * Gdx.graphics.getDeltaTime();
        this.position.y += this.velocity.y * Gdx.graphics.getDeltaTime();
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
