package com.afts.core.Utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;
import java.util.Map;

public class ResourceHandler {

    private Map<String, Texture> textureContainer = new HashMap<String, Texture>();

    public ResourceHandler()
    { }

    public Texture getTexture(String key)
    {
        Texture texture = this.textureContainer.get(key);
        if(texture != null)
        {
            return texture;
        }else
        {
            Gdx.app.log("ERROR", "Could not find file [" + key + "]!");
           return null;
        }
    }

    public void addTexture(String key,String pathToFile)
    {
        FileHandle file = Gdx.files.internal(pathToFile);
        if(file.exists())
        {
            this.textureContainer.put(key, new Texture(file));
        }else
        {
          Gdx.app.log("ERROR", "Texture with key[" + key + "] does not exist!");
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
