package com.doan3.canthotour.View.Personal;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import com.doan3.canthotour.Config;
import com.doan3.canthotour.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

import com.doan3.canthotour.Adapter.HttpRequestAdapter.httpPost;

import static com.doan3.canthotour.View.Main.MainActivity.menuBotNavBar;

public class ActivityAddTripSchedule extends AppCompatActivity implements View.OnFocusChangeListener {

    ImageButton btnBack;
    Button btnCreate;
    EditText etTripName, etEndDate, etStartDate;
    Boolean checkInfo = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtripschedule);

        btnBack = findViewById(R.id.btnBack);
        etTripName = findViewById(R.id.etTripName);
        etEndDate = findViewById(R.id.etEndDatePicker);
        btnCreate = findViewById(R.id.btnCreateTripSchedule);
        etStartDate = findViewById(R.id.etStartDatePicker);

        etTripName.setOnFocusChangeListener(this);
        etStartDate.setOnFocusChangeListener(this);
        etEndDate.setOnFocusChangeListener(this);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishActivity(1);
                finish();
            }
        });


        etStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker(etStartDate);
            }
        });

        etEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker(etEndDate);
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                JSONObject json;
//                String stt = null, error = null;
//
//                if(checkInfo){
//                    try {
//                        JSONObject jsonPost = new JSONObject("{"
//                                + Config.POST_KEY_TRIP_SCHEDULE.get(0) + ":\"" + etTripName.getText().toString() + "\","
//                                + Config.POST_KEY_TRIP_SCHEDULE.get(1) + ":\"" + etStartDate.getText().toString() + "\","
//                                + Config.POST_KEY_TRIP_SCHEDULE.get(2) + ":\"" + etEndDate.getText().toString() + "\"}");
//                        json = new JSONObject(new httpPost(jsonPost).execute(Config.URL_HOST + "URL_ADD_TRIP_SCHEDULE").get());
//                        // lấy status trả về
//                        stt = json.getString(Config.GET_KEY_JSON_TRIP_SCHEDULE.get(2));
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    } catch (ExecutionException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                // nếu status != null và = OK
//                if (stt != null && stt.equals(Config.GET_KEY_JSON_LOGIN.get(4))) {
//                    finish();
//                }
            }
        });

        menuBotNavBar(this, 3);

    }

    private void datePicker(final EditText editText){
        final Calendar calendar = Calendar.getInstance();
        int date = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                //i: năm, i1: tháng, i2: ngày
                calendar.set(i, i1, i2);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                editText.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, year, month, date);
        datePickerDialog.show();
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        switch (view.getId()){
            case R.id.etTripName:
                if(!b){
                    String str =((EditText)view).getText().toString();
                    if(str.trim().equals("") || str.equals(null)){
                        etTripName.setError(getResources().getString(R.string.text_FieldIsEmpty));
                        checkInfo = false;
                    }else{
                        checkInfo = true;
                    }
                }
                break;

            case R.id.etStartDatePicker:
                if(!b){
                    String str =((EditText)view).getText().toString();
                    if(str.trim().equals("") || str.equals(null)){
                        etStartDate.setError(getResources().getString(R.string.text_FieldIsEmpty));
                        checkInfo = false;
                    }else{
                        checkInfo = true;
                    }
                }
                break;

            case R.id.etEndDatePicker:
                if(!b){
                    String str =((EditText)view).getText().toString();
                    if(str.trim().equals("") || str.equals(null)){
                        etEndDate.setError(getResources().getString(R.string.text_FieldIsEmpty));
                        checkInfo = false;
                    }else{

                        checkInfo = true;
                    }
                }
                break;
        }
    }
}
