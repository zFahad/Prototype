package com.example.prototype;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.prototype.Model.Message;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Leopard on 4/30/2017.
 */

public class DeleteRequest extends Activity {

    String[] items;
    Spinner course_list;
    Button btn_submit;
    EditText section_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_request);

        course_list= (Spinner) findViewById(R.id.courseID);
        btn_submit= (Button) findViewById(R.id.submitbutton);
        section_input= (EditText) findViewById(R.id.rEditBox);
        List<String> item_list=getCourse.getCourse();

        items=new String[item_list.size()];

        for(int i=0;i<items.length;i++){
            items[i]=item_list.get(i);
            Log.d("mm",items[i]);



            ArrayAdapter<String> myAdapter =new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1,items );


            myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            course_list.setAdapter(myAdapter);

            course_list.setSelection(0);

            btn_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(!(section_input.getText().toString().isEmpty())) {


                        Retrofit.CreateRetrofitObj();

                        final API API = Retrofit.getRetrofitObj()
                                .create(API.class);

                        Call<Message> deleteRequest = API.Add_Delete_Operation(
                                "delete",
                                SaveInfo.getID(),
                                course_list.getSelectedItem().toString(),
                                section_input.getText().toString()
                        );
                        deleteRequest.enqueue(new Callback<Message>() {
                            @Override
                            public void onResponse(Call<Message> call, Response<Message> response) {
                                Log.d("Message", response.body().getMessage());


                                if (response.body().getMessage().equals("done")) {

                                    Toast.makeText(DeleteRequest.this, "Request Submitted", Toast.LENGTH_SHORT)
                                            .show();
                                    Intent stud_intent = new Intent(getApplicationContext(), StudentPortal.class);
                                    startActivity(stud_intent);
                                    finish();

                                }


                            }

                            @Override
                            public void onFailure(Call<Message> call, Throwable t) {
                                Log.d("Message", t.getMessage());
                                Toast.makeText(DeleteRequest.this, "Connection Failed", Toast.LENGTH_SHORT)
                                        .show();

                            }
                        });
                    }else{
                                Toast.makeText(DeleteRequest.this,"You must Write Section Like: CA36 ",Toast.LENGTH_SHORT)
                                        .show();
                            }
                }
            });
        }
    }
}
