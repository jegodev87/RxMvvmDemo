package com.mvvm.boilerplate.dmo.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.mvvm.boilerplate.dmo.R;
import com.mvvm.boilerplate.dmo.data.model.Employee;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EmployeeAdapter employeeAdapter;
    private final List<Employee> allEmployeesList= new ArrayList<>();
    private Toolbar toolbar;
    private EditText searchEditText;
    private HomeViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_list);

        //initialising recyclerview

        setupReyclerView();
        setupToolbar();
        setUpEditTextSearch();

        viewModel = ViewModelProviders.of(this).get(HomeViewModel.class);


        observeEmployee();
    }


    private void observeEmployee(){
        viewModel.getCachedEmployee().observe(this, new Observer<List<Employee>>() {
            @Override
            public void onChanged(List<Employee> employees) {
                if (employees!=null){
                    allEmployeesList.clear();
                    allEmployeesList.addAll(employees);
                    employeeAdapter.notifyDataSetChanged();
                    toolbar.setSubtitle(allEmployeesList.size()+ "results found");
                }else {
                    viewModel.fetchEmployee();
                }

            }
        });
    }

    private void setupToolbar(){
        toolbar = findViewById(R.id.search_toolbar);
        toolbar.setTitle("Employees List");

    }

    private void setUpEditTextSearch(){
        searchEditText = findViewById(R.id.search_employee);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length()==0){
                    employeeAdapter.updateList(allEmployeesList);
                }else {
                    searchEmployees(charSequence.toString().toLowerCase().trim());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void setupReyclerView(){
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(null);
        recyclerView.setItemAnimator(null);
        employeeAdapter = new EmployeeAdapter(this, this.allEmployeesList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(employeeAdapter);

    }

    private void searchEmployees(String searchText) {
        List<Employee> tempEmployeeList = new ArrayList<>();
        for (Employee emp : allEmployeesList) {
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if (emp.getName().trim().toLowerCase().contains(searchText) ||
                    emp.getCompany()!=null && emp.getCompany().getCompanyName().trim().toLowerCase().contains(searchText)) {
                tempEmployeeList.add(emp);
            }
        }
        //update recyclerview
        employeeAdapter.updateList(tempEmployeeList);
        if (tempEmployeeList.size()==0){
            toolbar.setSubtitle(" ");
        }else {
            toolbar.setSubtitle(tempEmployeeList.size()+" Employees found");
        }


    }
}
