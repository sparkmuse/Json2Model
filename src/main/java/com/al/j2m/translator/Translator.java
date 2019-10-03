package com.al.j2m.translator;

import com.al.j2m.entity.Entity;
import com.al.j2m.entity.Property;

/**
 * Translates from a Json data type to a
 * language specific one.
 *
 * @author Alfredo Lopez
 */
public abstract class Translator {

    private Entity Entity;

    public Translator(Entity entity) {
        this.Entity = entity;
    }

    /**
     * Method that does the translation main drive.
     */
    public Entity translate() {

        for (Property p : this.Entity.getProperties()) {
            p = marshall(p);
        }

        return this.Entity;
    }

    /**
     * Translates from a data type to the corresponding one.
     */
    public abstract Property marshall(Property property);
}
