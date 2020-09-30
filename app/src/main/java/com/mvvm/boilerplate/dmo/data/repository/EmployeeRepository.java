package com.mvvm.boilerplate.dmo.data.repository;

import android.app.Application;

import com.mvvm.boilerplate.dmo.api.ApiService;
import com.mvvm.boilerplate.dmo.api.ServiceGenerator;
import com.mvvm.boilerplate.dmo.data.local.AppDatabase;
import com.mvvm.boilerplate.dmo.data.local.dao.EmployeeDao;
import com.mvvm.boilerplate.dmo.data.model.Employee;

import java.util.List;

import io.reactivex.Single;

public class EmployeeRepository {

    private EmployeeDao employeeDao;
    private ApiService backend;

    public EmployeeRepository(Application application) {
        AppDatabase db = AppDatabase.getDbInstance(application);
        employeeDao = db.employeeDao();
        backend = ServiceGenerator.createService(ApiService.class);
    }


    //first checking local db for data if there is data returning data
    //if there is no data then calling the api call
    //after get result saving to db
    public Single<List<Employee>> fetchEmployees() {
        return employeeDao.getAllEmployeesFromDb()
                .flatMap(employees -> {
                    if (employees.isEmpty()) {
                        return backend.getEmployeesFromServer().map(remoteEmployees -> {
                            employeeDao.insertAllEmployees(remoteEmployees);
                            return remoteEmployees;
                        });
                    } else {
                        return Single.just(employees);
                    }
                });

    }

}
