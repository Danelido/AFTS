package com.afts.core.Utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class ResourceHandler {

    private Map<String, Texture> textureContainer = new HashMap<String, Texture>();

    public ResourceHandler()
    { }

    public Texture getTexture(String nameOfTexture)
    {
        Texture texture = this.textureContainer.get(nameOfTexture);
        if(texture != null)
        {
            return texture;
        }else
        {
            Gdx.app.log("WARNING", "Could not find file [" + nameOfTexture + "] but will try to add it");
            this.addTexture(nameOfTexture);
            return this.textureContainer.get(nameOfTexture);
        }
    }

    public void addTexture(String nameOfTexture)
    {
        FileHandle file = Gdx.files.internal(nameOfTexture);
        if(file.exists())
        {
            this.textureContainer.put(nameOfTexture, new Texture(file));
        }else
        {
          Gdx.app.log("ERROR", "Texture with name[" + nameOfTexture + "] does not exist!");
        }
    }

    public void cleanUp()
    {
        for(Texture textureObjects : this.textureContainer.values())
        {
            textureObjects.dispose();
        }
        this.textureContainer.clear();

    }

}
