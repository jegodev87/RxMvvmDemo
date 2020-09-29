package com.mvvm.boilerplate.dmo.ui.home;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mvvm.boilerplate.dmo.api.ApiService;
import com.mvvm.boilerplate.dmo.api.ServiceGenerator;
import com.mvvm.boilerplate.dmo.data.local.AppDatabase;
import com.mvvm.boilerplate.dmo.data.model.Employee;
import com.mvvm.boilerplate.dmo.data.repository.EmployeeRepository;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

public class HomeViewModel extends AndroidViewModel {

    private MutableLiveData<List<Employee>> employeeLiveData ;
    private ApiService backend;
    private AppDatabase appDatabase;
    private CompositeDisposable compositeDisposable;
    private EmployeeRepository employeeRepository;


    public HomeViewModel(@NonNull Application application) {
        super(application);
        backend = ServiceGenerator.createService(ApiService.class);
        employeeRepository = new EmployeeRepository(application);
        compositeDisposable = new CompositeDisposable();
        employeeLiveData = new MutableLiveData<>();
        employeeLiveData.setValue(null);

    }


   public LiveData<List<Employee>> getCachedEmployee(){
        return employeeRepository.getAllEmployees();
   }


    public void fetchEmployee() {
        compositeDisposable.add(backend.getEmployees()
                .doOnSuccess(new Consumer<List<Employee>>() {
                    @Override
                    public void accept(List<Employee> employees) throws Exception {
                        try {
                            appDatabase.employeeDao().insertAllEmployees(employees);
                        } catch (Exception e) {
                            Log.e("Exception", e.toString());
                        }

                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Employee>>() {
                    @Override
                    public void onSuccess(List<Employee> employees) {
                        if (!employees.isEmpty()) {

                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                        try {
                            HttpException error = (HttpException) e;
                            String errorBody = Objects.requireNonNull(Objects.requireNonNull(error.response()).errorBody()).string();
                            JSONObject jObj = new JSONObject(errorBody);
                            String message = jObj.getString("error");
                        } catch (JSONException | IOException ex) {
                            ex.printStackTrace();
                        }


                    }
                })
        );

    }

    public MutableLiveData<List<Employee>> getEmployeeLiveData() {
        return employeeLiveData;
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
