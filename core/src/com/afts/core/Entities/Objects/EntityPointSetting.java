package com.afts.core.Entities.Objects;

// This is used for SAT-collision
// Every entity will be treated as a rectangle by default
// Based on setting the points for the entities
// will be changed in position.
// This is to achieve a more accurate collision
// between objects based on the texture

// Example:

/*
Rectangle:

    .   .

    .   .

Triangle:

        .

     .     .


Circle will use the radius for its calculations
 */

// These effects will be set in entity class, this is just the settings
public enum EntityPointSetting {
    RECTANGLE,
    TRIANGLE,
    CIRCLE

}
