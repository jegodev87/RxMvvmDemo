package com.mvvm.boilerplate.dmo.ui.home;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.mvvm.boilerplate.dmo.data.model.Employee;
import com.mvvm.boilerplate.dmo.data.repository.EmployeeRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class HomeViewModel extends AndroidViewModel {

    private MutableLiveData<List<Employee>> employeeLiveData;
    private MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoadingLiveData = new MutableLiveData<>();
    private CompositeDisposable compositeDisposable;
    private EmployeeRepository employeeRepository;


    public HomeViewModel(@NonNull Application application) {
        super(application);
        employeeRepository = new EmployeeRepository(application);
        compositeDisposable = new CompositeDisposable();
        employeeLiveData = new MutableLiveData<>();


    }


    public void getEmployee() {
        isLoadingLiveData.setValue(true);
        compositeDisposable.add(
                employeeRepository.fetchEmployees()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<List<Employee>>() {
                            @Override
                            public void onSuccess(List<Employee> employees) {
                                Log.e("onSuccess", employees.toString());
                                isLoadingLiveData.setValue(false);
                                employeeLiveData.setValue(employees);
                            }

                            @Override
                            public void onError(Throwable e) {
                                isLoadingLiveData.setValue(false);
                                errorLiveData.setValue("No network connection available");
                                Log.e("Throwable", e.toString());
                            }
                        })
        );

    }


    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }


    public MutableLiveData<List<Employee>> getEmployeeLiveData() {
        return employeeLiveData;
    }


    public MutableLiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    public MutableLiveData<Boolean> getIsLoadingLiveData() {
        return isLoadingLiveData;
    }
}
