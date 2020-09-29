package com.mvvm.boilerplate.dmo.api;

import com.mvvm.boilerplate.dmo.data.model.Employee;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface ApiService {

    @GET("5d565297300000680030a986")
    Single<List<Employee>> getEmployees();
}
