package com.mvvm.boilerplate.dmo.ui.details;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.mvvm.boilerplate.dmo.R;
import com.mvvm.boilerplate.dmo.data.model.Address;
import com.mvvm.boilerplate.dmo.data.model.Employee;

public class EmployeeDetailsActivity extends AppCompatActivity {

    private Employee selectedEmployee = null;
    private AppCompatImageView profileIcon;
    private TextView name, username, email, address, phone, website, companyDetails;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_details);
        initViews();
        getBudle();
    }

    private void getBudle() {
        if (getIntent().getExtras() != null) {
            selectedEmployee = (Employee) getIntent().getExtras().getSerializable("employee");
            if (selectedEmployee != null) {
                setDetails(selectedEmployee);
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private void setDetails(Employee selectedEmployee) {
        if (selectedEmployee != null) {
            Log.e("SelectedEmp", selectedEmployee.toString());
            if (selectedEmployee.getName() != null) {
                name.setText("Name : " + selectedEmployee.getName());
                toolbar.setSubtitle(selectedEmployee.getName());
            }
            if (selectedEmployee.getUsername() != null) {
                username.setText("Username : " + selectedEmployee.getUsername());
            }
            if (selectedEmployee.getEmail() != null) {
                email.setText("Email : " + selectedEmployee.getEmail());
            }

            if (selectedEmployee.getAddress() != null) {
                final Address add = selectedEmployee.getAddress();
                address.setText("Address : " + add.getSuite() + "," + add.getCity() + "," + add.getStreet() + "," + add.getZipcode());
            }

            if (selectedEmployee.getPhone() != null) {
                phone.setText("Phone : " + selectedEmployee.getPhone());
            } else {
                phone.setText("Phone : " + "NA");
            }
            if (selectedEmployee.getWebsite() != null) {
                website.setText("Website : " + selectedEmployee.getWebsite());
            }

            if (selectedEmployee.getCompany() != null) {
                companyDetails.setText("Company : " + selectedEmployee.getCompany().getCompanyName() + "-" + selectedEmployee.getCompany().getBs());
            }


            if (selectedEmployee.getProfileImage() != null) {
                Glide.with(this)
                        .load(selectedEmployee.getProfileImage())
                        .placeholder(R.mipmap.ic_launcher)
                        .into(profileIcon);
            }
        }
    }

    private void initViews() {
        toolbar = findViewById(R.id.details_toolbar);
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
