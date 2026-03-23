package com.example.devvault.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.devvault.db.entity.Resource;
import com.example.devvault.repository.ResourceRepository;

import java.util.List;

public class ResourceViewModel extends AndroidViewModel {
    private ResourceRepository repository;
    private LiveData<List<Resource>> allResources;

    public ResourceViewModel(@NonNull Application application) {
        super(application);
        repository = new ResourceRepository(application);
        allResources = repository.getAllResources();
    }

    public LiveData<List<Resource>> getAllResources() {
        return allResources;
    }

    public void insert(Resource resource) {
        repository.insert(resource);
    }

    public void update(Resource resource) {
        repository.update(resource);
    }

    public void delete(Resource resource) {
        repository.delete(resource);
    }
}
