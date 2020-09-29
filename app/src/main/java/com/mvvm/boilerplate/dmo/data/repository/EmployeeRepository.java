package com.mvvm.boilerplate.dmo.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.mvvm.boilerplate.dmo.data.local.AppDatabase;
import com.mvvm.boilerplate.dmo.data.local.dao.EmployeeDao;
import com.mvvm.boilerplate.dmo.data.model.Employee;

import java.util.List;

public class EmployeeRepository {

    private EmployeeDao employeeDao;
    private LiveData<List<Employee>> employeesList;

   public EmployeeRepository(Application application) {
        AppDatabase db = AppDatabase.getDbInstance(application);
        employeeDao = db.employeeDao();
        employeesList = employeeDao.getAllEmployees();
    }

   public   LiveData<List<Employee>> getAllEmployees() {
        return employeesList;
    }
}
