package com.example.devvault.repository;

import android.content.Context;
import com.example.devvault.db.AppDatabase;
import com.example.devvault.db.dao.ResourceDao;
import com.example.devvault.db.entity.Resource;
import java.util.List;

public class ResourceRepository {
    private final ResourceDao resourceDao;

    public ResourceRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        this.resourceDao = db.resourceDao();
    }

    public List<Resource> getAllResources() {
        return resourceDao.getAll();
    }

    public void insertResource(Resource resource) {
        resourceDao.insert(resource);
    }

    public void deleteResource(Resource resource) {
        resourceDao.delete(resource);
    }

    public Resource getResourceById(int id) {
        return resourceDao.getById(id);
    }
}
