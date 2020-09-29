package com.mvvm.boilerplate.dmo.ui.details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mvvm.boilerplate.dmo.R;
import com.mvvm.boilerplate.dmo.data.model.Address;
import com.mvvm.boilerplate.dmo.data.model.Employee;

public class EmployeeDetailsActivity extends AppCompatActivity {

    private Employee selectedEmployee;
    private AppCompatImageView profileIcon;
    private TextView name,username,email,address,phone,website,companyDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_details);
        initViews();
        getBudle();
    }

    private void getBudle(){
        if (getIntent().getExtras()!=null){
            selectedEmployee = (Employee) getIntent().getExtras().getSerializable("employee");
            if (selectedEmployee!=null){
                setDetails(selectedEmployee);
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private void setDetails(Employee selectedEmployee) {
        if (selectedEmployee!=null){
            name.setText(selectedEmployee.getName());
            username.setText(selectedEmployee.getUsername());
            email.setText(selectedEmployee.getEmail());
            if (selectedEmployee.getAddress()!=null){
               final Address addresss = selectedEmployee.getAddress();
                address.setText(addresss.getSuite()+","+addresss.getStreet()+","+addresss.getZipcode());
            }

            phone.setText(selectedEmployee.getPhone());
            website.setText(selectedEmployee.getWebsite());

            if (selectedEmployee.getCompany()!=null){
                companyDetails.setText(selectedEmployee.getCompany().getCompanyName()+","+selectedEmployee.getCompany().getBs());
            }


            if (selectedEmployee.getProfileImage()!=null){
                Glide.with(this).load( selectedEmployee.getProfileImage()).fitCenter().into(profileIcon);
            }
        }
    }

    private void initViews(){
        profileIcon = findViewById(R.id.profile_image);
         name = findViewById(R.id.name);
         username = findViewById(R.id.username);
         email = findViewById(R.id.email);
         address = findViewById(R.id.address);
         phone = findViewById(R.id.phone);
         website = findViewById(R.id.website);
         companyDetails = findViewById(R.id.company);


    }
}
