package com.mvvm.boilerplate.dmo.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mvvm.boilerplate.dmo.R;
import com.mvvm.boilerplate.dmo.data.model.Employee;
import com.mvvm.boilerplate.dmo.ui.details.EmployeeDetailsActivity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder> {

    private Context context;
    private List<Employee> listOfEmployees;


    public EmployeeAdapter(Context context, List<Employee> listOfEmployees) {
        this.context = context;
        this.listOfEmployees = listOfEmployees;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.employee_row_item, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NotNull ViewHolder holder, final int position) {
        final Employee employee = listOfEmployees.get(position);
        if (employee != null) {
            holder.bind(employee);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context, EmployeeDetailsActivity.class).putExtra("employee",employee));
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return listOfEmployees.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewName, textViewCompanyName;
        private AppCompatImageView employeeImage;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.employee_name);
            textViewCompanyName = itemView.findViewById(R.id.employee_company_name);
            employeeImage = itemView.findViewById(R.id.employee_image);
        }

        @SuppressLint("SetTextI18n")
        private void bind(final Employee employee) {
            Log.e("EmployeeAdapter",employee.toString());
            if (employee.getName() != null)
                textViewName.setText("Name :" +employee.getName());
            if (employee.getCompany() != null){
                textViewCompanyName.setText("Company :"+employee.getCompany().getCompanyName());
            }

            if (employee.getProfileImage() != null)
                setImage(employee.getProfileImage());
        }


        private void setImage(String url) {
            Glide.with(context).load(url).circleCrop().into(employeeImage);
        }

    }


    public void updateList(List<Employee> employees) {
        listOfEmployees = employees;
        notifyDataSetChanged();
    }

}
