package com.aniket.realmmigrationtestexample;

import com.aniket.realmmigrationtestexample.data.Dog;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

/**
 * Created by nicky on 23/7/17.
 */

class MigrationExample implements RealmMigration {

    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        if (oldVersion < 2) {
            updateToVersion2(realm.getSchema());
        }
    }

    private void updateToVersion2(RealmSchema schema) {
        RealmObjectSchema dogSchema = schema.create(Dog.class.getSimpleName()); // Get the schema of the class to modify.
        dogSchema.addField("age", int.class); // Add the new field.
    }
}
