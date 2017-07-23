package com.aniket.realmmigrationtestexample;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

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
                .schemaVersion(1) // Original realm version.
                .build();// Get a configuration instance.
        configFactory.copyRealmFromAssets(context, REALM_NAME, realmConfig); // Copy the stored version 1 realm file from assets to a NEW location.
        Realm.getInstance(realmConfig);
    }

}