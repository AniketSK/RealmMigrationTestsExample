package com.aniket.realmmigrationtestexample;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.aniket.realmmigrationtestexample.data.Dog;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.exceptions.RealmMigrationNeededException;

/**
 * Created by nicky on 23/7/17.
 */
@RunWith(AndroidJUnit4.class)
public class MigrationExampleTest {

    @Rule
    public final TestRealmConfigurationFactory configFactory = new TestRealmConfigurationFactory();
    private Context context;


    @Before
    public void setUp() throws Exception {
        context = InstrumentationRegistry.getInstrumentation().getContext();
    }

    @Test(expected = RealmMigrationNeededException.class)
    public void migrate_migrationNeededIsThrown() throws Exception {
        String REALM_NAME = "realmdb_1.realm";
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .name(REALM_NAME)
                .schemaVersion(1) // Original realm version.
                .build();// Get a configuration instance.
        configFactory.copyRealmFromAssets(context, REALM_NAME, realmConfig); // Copy the stored version 1 realm file from assets to a NEW location.
        Realm.getInstance(realmConfig);
    }


    @Test
    public void migrate_migrationSuceeds() throws Exception {
        String REALM_NAME = "realmdb_1.realm"; // Same name as the file for the old realm which was copied to assets.
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .name(REALM_NAME)
                .schemaVersion(2) // NEW realm version.
                .migration(new MigrationExample())
                .build();// Get a configuration instance.
        configFactory.copyRealmFromAssets(context, REALM_NAME, realmConfig); // Copy the stored version 1 realm file
        // from assets to a NEW location.
        // Note: the old file is always deleted for you.
        //   by the copyRealmFromAssets.
        Realm realm = Realm.getInstance(realmConfig);
        assert realm.getSchema().get(Dog.class.getSimpleName())
                .hasField("age");

        assert realm.getSchema().get(Dog.class.getSimpleName())
                .getFieldType("age").equals(int.class);
        realm.close();
    }
}