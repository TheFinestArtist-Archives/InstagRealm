package com.thefinestartist.realm.instagram.databases;

import com.thefinestartist.royal.RoyalDatabase;

import java.util.List;

import io.realm.Realm;

/**
 * Created by TheFinestArtist on 8/15/15.
 */
public class ListViewDatabase extends RoyalDatabase {

    @Override
    public String getFileName() {
        return "ListViewDatabase";
    }

    @Override
    public boolean forCache() {
        return true;
    }

    @Override
    public byte[] getEncryptionKey() {
        return null;
    }

    @Override
    public boolean shouldDeleteIfMigrationNeeded() {
        return true;
    }

    @Override
    public List<Object> getModules() {
        return super.getModules();
    }

    @Override
    public int getVersion() {
        return 0;
    }

    @Override
    public long execute(Realm realm, long version) {
        return super.execute(realm, version);
    }
}
