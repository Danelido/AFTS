package com.afts.core.Entities.PlayerPackage;

import com.afts.core.Utility.ResourceHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class PlayerDirectionIndicator {

    private Player player;
    private Vector2 position;
    private TextureRegion region;
    private Vector2 size;
    private float distance;

    public PlayerDirectionIndicator(Player player, ResourceHandler resources)
    {
        this.player = player;
        this.position = new Vector2();
        this.size = new Vector2(12.f, 12.f);
        this.distance = 25.f;
        this.region = new TextureRegion(resources.getTexture("playerDirectionIndicator"));
    }

    public void display(SpriteBatch batch)
    {
        float xMultiplier = this.player.getController().getMovementHandler().getMultipliers().x;
        float yMultiplier = this.player.getController().getMovementHandler().getMultipliers().y;

        this.position.x = this.player.getPosition().x + (this.player.getOrigin().x - this.size.x / 2.f) + distance * xMultiplier;
        this.position.y = this.player.getPosition().y + (this.player.getOrigin().y - this.size.y / 2.f) + distance * yMultiplier;

        batch.setColor(this.player.getColor());
        batch.draw(this.region,
                this.position.x, this.position.y,
                this.player.getOrigin().x - (this.player.getOrigin().x - this.size.x / 2.f) ,
                this.player.getOrigin().y - (this.player.getOrigin().y - this.size.y / 2.f) ,
                this.size.x, this.size.y,
                1.f, 1.f,
                this.player.getController().getJoystickRotation());


        batch.setColor(Color.WHITE);


    }

}
