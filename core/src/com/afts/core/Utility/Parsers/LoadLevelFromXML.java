package com.afts.core.Utility.Parsers;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.afts.core.Entities.Objects.Block;
import com.afts.core.Entities.Objects.CollisionPointSetup;
import com.afts.core.Entities.Objects.Entity;
import com.afts.core.Entities.Objects.EntityManager;
import com.afts.core.Entities.Objects.OnCollisionSetting;
import com.afts.core.Entities.PlayerPackage.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class LoadLevelFromXML {

    public LoadLevelFromXML(String filename, EntityManager entityManager, Player player)
    {
        boolean hasPlayerSpawn = false;

        //Get Document Builder
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();

            //Build Document
            Document document = builder.parse(new File("Levels/"+ filename + ".xml"));

            //Normalize the XML Structure; It's just too important !!
            document.getDocumentElement().normalize();

            //Get all objects
            NodeList nList = document.getElementsByTagName("Object");


            for (int temp = 0; temp < nList.getLength(); temp++)
            {
                Node node = nList.item(temp);
                if (node.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element eElement = (Element) node;

                    // Get the position
                    Vector2 position = new Vector2(
                            Float.parseFloat(eElement.getElementsByTagName("x").item(0).getTextContent()),
                            Float.parseFloat(eElement.getElementsByTagName("y").item(0).getTextContent()));

                    // Get the texture name and load it in
                    String textureFileName = eElement.getElementsByTagName("Texture").item(0).getTextContent();
                    Texture texture = new Texture(Gdx.files.internal("Textures/Level/" + textureFileName));

                    // Get the color
                    Color color = new Color();
                    color.r = Float.parseFloat(eElement.getElementsByTagName("Red").item(0).getTextContent());
                    color.g = Float.parseFloat(eElement.getElementsByTagName("Green").item(0).getTextContent());
                    color.b = Float.parseFloat(eElement.getElementsByTagName("Blue").item(0).getTextContent());
                    color.a = Float.parseFloat(eElement.getElementsByTagName("Alpha").item(0).getTextContent());

                    // Get the collision setting
                    OnCollisionSetting setting = this.getSettingBaseOnString(eElement.getElementsByTagName("CollisionSetting").item(0).getTextContent());

                    // Get the size
                    float size = Float.parseFloat(eElement.getElementsByTagName("Size").item(0).getTextContent());

                    // Get the rotation
                    float rotation = Float.parseFloat(eElement.getElementsByTagName("Rotation").item(0).getTextContent());

                    // Get the type to be able to determine if it's a spawn point, regular block och a finish block
                    String type = eElement.getElementsByTagName("Type").item(0).getTextContent();

                    if(type.equalsIgnoreCase("SPAWNPOINT"))
                    {
                        player.setStartPosition(position);
                        hasPlayerSpawn = true;
                    }else
                    {
                        Entity e = null;
                        // Temporary, everything that hurts the player is a triangle atm, might do this in a smarter way in the future
                        // Depending on how far we want to take the game
                        if(setting == OnCollisionSetting.HURT_PLAYER) {
                            e = new Block(position, new Vector2(size, size), rotation, texture, CollisionPointSetup.TRIANGLE, setting);
                        }else {
                           e = new Block(position, new Vector2(size, size), rotation, texture, setting);
                        }

                        e.setColor(color);
                        entityManager.addEntity(e);
                    }

                }
            }

        } catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }

        if(!hasPlayerSpawn)
        {
            Gdx.app.log("LEVEL WARNING" , "No player spawn was found");
        }

    }

    private OnCollisionSetting getSettingBaseOnString(String setting)
    {
        if(setting.equalsIgnoreCase(OnCollisionSetting.NON_MOVABLE.toString()))
        {
            return OnCollisionSetting.NON_MOVABLE;
        }

        if(setting.equalsIgnoreCase(OnCollisionSetting.MOVABLE.toString()))
        {
            return OnCollisionSetting.MOVABLE;
        }

        if(setting.equalsIgnoreCase(OnCollisionSetting.DESTROY.toString()))
        {
            return OnCollisionSetting.DESTROY;
        }

        if(setting.equalsIgnoreCase(OnCollisionSetting.HURT_PLAYER.toString()))
        {
            return OnCollisionSetting.HURT_PLAYER;
        }

        return OnCollisionSetting.NOTHING;

    }

}

