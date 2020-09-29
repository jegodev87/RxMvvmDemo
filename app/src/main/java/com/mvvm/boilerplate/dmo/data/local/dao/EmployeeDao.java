package com.mvvm.boilerplate.dmo.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.mvvm.boilerplate.dmo.data.model.Employee;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface EmployeeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllEmployees(List<Employee> listOfEmployees);



    @Query("SELECT * FROM employee_details")
    LiveData<List<Employee>> getAllEmployees();
}
