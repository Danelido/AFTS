package com.afts.core.Entities.Collision;

import com.afts.core.Entities.Objects.Entity;
import com.afts.core.Entities.PlayerPackage.Player;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;


public class SATCollision {

    // TODO figure out a way to render the points that has the lowest XY and greatest XY
    private boolean DEBUG;
    private SATDebugRenderer dbgRenderer = null;

    private Vector2 lowestXY_player = new Vector2();
    private Vector2 lowestXY_entity = new Vector2();
    private Vector2 greatestXY_player = new Vector2();
    private Vector2 greatestXY_entity = new Vector2();

    private OrthographicCamera camera;

    public SATCollision(OrthographicCamera camera)
    {
        this.camera = camera;
        this.DEBUG = false;
    }

    public void debugMode(SATDebugRenderer dbgRenderer)
    {
        DEBUG = true;
        this.dbgRenderer = dbgRenderer;
    }

    public boolean collision(Player player, Entity entity)
    {
        if(this.SAT_firstCheck(player,entity))
        {
            return this.SAT_secondCheck(player,entity);
        }

        return false;
    }


    private boolean SAT_firstCheck(Player player, Entity entity)
    {
        //Player axis
        this.calculateGreatestAndLowest_playerAxis(player,entity);

        // Collision on x axis
        if(this.greatestXY_player.x >= this.lowestXY_entity.x && this.lowestXY_player.x < this.greatestXY_entity.x)
        {
            // Collision on y axis
            if(this.lowestXY_player.y <= this.greatestXY_entity.y && this.greatestXY_player.y > this.lowestXY_entity.y)
            {
                return true;
            }

        }

        return false;

    }

    private boolean SAT_secondCheck(Player player, Entity entity)
    {
        //Entity axis
        this.calculateGreatestAndLowest_EntityAxis(player,entity);

        // Collision on x axis
        if(this.greatestXY_player.x >= this.lowestXY_entity.x && this.lowestXY_player.x < this.greatestXY_entity.x)
        {
            return true;
        }

        // Collision on y axis
        if(this.lowestXY_player.y <= this.greatestXY_entity.y && this.greatestXY_player.y > this.lowestXY_entity.y)
        {
            return true;
        }

        return false;
    }

    private void calculateGreatestAndLowest_playerAxis(Player player, Entity entity)
    {
        if(this.DEBUG)
            this.dbgRenderer.beginRenderer(this.camera);


        Vector2[] newEntityPoints = new Vector2[entity.getPoints().length];

        for(int i = 0; i < newEntityPoints.length; i++)
        {
            newEntityPoints[i] = entity.getPoints()[i];
        }

        this.lowestXY_player.set(player.getPoints()[0]);
        this.greatestXY_player.set(player.getPoints()[0]);

        this.lowestXY_entity.set(newEntityPoints[0]);
        this.greatestXY_entity.set(newEntityPoints[0]);


        // Search for the lowest and highest XY of player
        for(int i = 1; i < player.getPoints().length; i++)
        {
            // Search for lowest
            if(this.lowestXY_player.x > player.getPoints()[i].x)
                this.lowestXY_player.x = player.getPoints()[i].x;

            if(this.lowestXY_player.y > player.getPoints()[i].y)
                this.lowestXY_player.y = player.getPoints()[i].y;

            // Search for Greatest
            if(this.greatestXY_player.x < player.getPoints()[i].x)
                this.greatestXY_player.x = player.getPoints()[i].x;

            if(this.greatestXY_player.y < player.getPoints()[i].y)
                this.greatestXY_player.y = player.getPoints()[i].y;

        }


        // Search for the lowest and highest XY of entity
        for(int i = 1; i < entity.getPoints().length; i++)
        {
            // Search for lowest
            if(this.lowestXY_entity.x > entity.getPoints()[i].x)
                this.lowestXY_entity.x = entity.getPoints()[i].x;

            if(this.lowestXY_entity.y > entity.getPoints()[i].y)
                this.lowestXY_entity.y = entity.getPoints()[i].y;

            // Search for Greatest
            if(this.greatestXY_entity.x < entity.getPoints()[i].x)
                this.greatestXY_entity.x = entity.getPoints()[i].x;

            if(this.greatestXY_entity.y < entity.getPoints()[i].y)
                this.greatestXY_entity.y = entity.getPoints()[i].y;

        }

//        this.lowestXY_entity.sub(this.camera.position.x,  this.camera.position.y);
//        this.greatestXY_entity.sub(this.camera.position.x,  this.camera.position.y);
//
//        this.lowestXY_player.sub(this.camera.position.x,  this.camera.position.y);
//        this.greatestXY_player.sub(this.camera.position.x,  this.camera.position.y);

        System.out.println("PLAYER AXIS");
        System.out.println("Lowest player x and y: " + this.lowestXY_player.x + ", " + this.lowestXY_player.y);
        System.out.println("Lowest entity x and y: " + this.lowestXY_entity.x + ", " + this.lowestXY_entity.y);
        System.out.println("Greatest player x and y: " + this.greatestXY_player.x + ", " + this.greatestXY_player.y);
        System.out.println("Greatest entity x and y: " + this.greatestXY_entity.x + ", " + this.greatestXY_entity.y + "\n");

            if(this.DEBUG)
            {
                this.dbgRenderer.addPointsToRender(this.lowestXY_entity, Color.RED);
                this.dbgRenderer.addPointsToRender(this.greatestXY_entity, Color.GREEN);
                this.dbgRenderer.addPointsToRender(this.lowestXY_player, Color.RED);
                this.dbgRenderer.addPointsToRender(this.greatestXY_player, Color.GREEN);

                this.dbgRenderer.endRenderer();
            }



    }

    private void calculateGreatestAndLowest_EntityAxis(Player player, Entity entity)
    {
        if(this.DEBUG)
            this.dbgRenderer.beginRenderer(this.camera);


        Vector2[] newPlayerPoints = new Vector2[player.getPoints().length];
        for(int i = 0; i < newPlayerPoints.length; i++)
        {
            newPlayerPoints[i] = entity.getPoints()[i];
        }


        this.lowestXY_player.set(newPlayerPoints[0]);

        this.greatestXY_player.set(newPlayerPoints[0]);

        this.lowestXY_entity.set(entity.getPoints()[0]);
        this.greatestXY_entity.set(entity.getPoints()[0]);

        this.lowestXY_entity.sub(this.camera.position.x,  this.camera.position.y);
        this.greatestXY_entity.sub(this.camera.position.x,  this.camera.position.y);

        // Search for the lowest and highest XY of player
        for(int i = 1; i < player.getPoints().length; i++)
        {
            // Search for lowest
            if(this.lowestXY_player.x > player.getPoints()[i].x)
                this.lowestXY_player.x = player.getPoints()[i].x;

            if(this.lowestXY_player.y > player.getPoints()[i].y)
                this.lowestXY_player.y = player.getPoints()[i].y;

            // Search for Greatest
            if(this.greatestXY_player.x < player.getPoints()[i].x)
                this.greatestXY_player.x = player.getPoints()[i].x;

            if(this.greatestXY_player.y < player.getPoints()[i].y)
                this.greatestXY_player.y = player.getPoints()[i].y;

        }


        // Search for the lowest and highest XY of entity
        for(int i = 1; i < entity.getPoints().length; i++)
        {
            // Search for lowest
            if(this.lowestXY_entity.x > entity.getPoints()[i].x)
                this.lowestXY_entity.x = entity.getPoints()[i].x;

            if(this.lowestXY_entity.y > entity.getPoints()[i].y)
                this.lowestXY_entity.y = entity.getPoints()[i].y;

            // Search for Greatest
            if(this.greatestXY_entity.x < entity.getPoints()[i].x)
                this.greatestXY_entity.x = entity.getPoints()[i].x;

            if(this.greatestXY_entity.y < entity.getPoints()[i].y)
                this.greatestXY_entity.y = entity.getPoints()[i].y;

        }

        if(this.DEBUG)
        {
            this.dbgRenderer.addPointsToRender(this.lowestXY_entity, Color.RED);
            this.dbgRenderer.addPointsToRender(this.greatestXY_entity, Color.GREEN);
            this.dbgRenderer.addPointsToRender(this.lowestXY_player, Color.RED);
            this.dbgRenderer.addPointsToRender(this.greatestXY_player, Color.GREEN);

            this.dbgRenderer.endRenderer();
        }

//        System.out.println("ENTITY AXIS");
//        System.out.println("Lowest player x and y: " + this.lowestXY_player.x + ", " + this.lowestXY_player.y);
//        System.out.println("Lowest entity x and y: " + this.lowestXY_entity.x + ", " + this.lowestXY_entity.y);
//
//        System.out.println("Greatest player x and y: " + this.greatestXY_player.x + ", " + this.greatestXY_player.y);
//        System.out.println("Greatest entity x and y: " + this.greatestXY_entity.x + ", " + this.greatestXY_entity.y + "\n");



//        this.lowestXY_entity.sub(this.camera.position.x,  this.camera.position.y);
//        this.greatestXY_entity.sub(this.camera.position.x,  this.camera.position.y);
//
//        this.lowestXY_player.sub(this.camera.position.x,  this.camera.position.y);
//        this.greatestXY_player.sub(this.camera.position.x,  this.camera.position.y);
    }

}
