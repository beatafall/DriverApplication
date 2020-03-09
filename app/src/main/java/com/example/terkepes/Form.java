package com.example.terkepes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.terkepes.Class.Messages;
import com.example.terkepes.Class.MessageType;
import com.example.terkepes.Retrofit.ApiUtils;
import com.example.terkepes.Retrofit.UserService;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Form extends AppCompatActivity {

    TextView message, line, bus, lat, lon, dateAndTime, driver, tx;
    Button btn_sendMessage;
    UserService service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        message=findViewById(R.id.message2);
        driver=findViewById(R.id.txDriverName);
        line=findViewById(R.id.editTextLine);
        bus=findViewById(R.id.editTextBus);
        lat=findViewById(R.id.lat4);
        lon=findViewById(R.id.lon4);
        dateAndTime=findViewById(R.id.dateandtime);
        btn_sendMessage=findViewById(R.id.send);

        service = ApiUtils.getAPIService();

        tx=findViewById(R.id.tx);

        Bundle extras = getIntent().getExtras();
        final String username, userId;

        if(extras != null) {
            username = extras.getString("driverName");
            //userId = extras.getString("driverId");
            // Log.d("uid",userId);

            lat.setText(getIntent().getStringExtra("latitude"));
            lon.setText(getIntent().getStringExtra("longitude"));
            message.setText(getIntent().getStringExtra("uzenet"));
            driver.setText(username);
        }

        btn_sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = getIntent().getExtras();
                final String username, userId;

                if (extras != null) {
                    username = extras.getString("driverName");
                }

               /* Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(UserService.BASE_URL)
                        //.addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                UserService apiInterface = retrofit.create(UserService.class);*/
                Messages message = new Messages();

                //2. Using GsonBuilder
                Gson gson = new GsonBuilder()
                        .disableHtmlEscaping()
                        .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                        .setPrettyPrinting()
                        .serializeNulls()
                        .create();

                message.setMessageTypeId("2");
                message.setDriverId("7");
                message.setLon("46.65");
                message.setLat("24.56");
                message.setLine("44");
                message.setBus("1414");
                message.setDate("2020-03-02 11:21");

                System.out.println(gson.toJson(message));
                Log.d("Mm", gson.toJson(message));

                //newMess(gson.toJson(message));

               // newMess(message);
                //sendPost(message);

                tx.setText(gson.toJson(message));



                /*try {
                    paramObject.put("MessageId", "3");
                    paramObject.put("MessageId", "3");
                    Call<Messages> userCall = apiInterface.sendMessage(paramObject.toString());
                    userCall.enqueue(new Callback<Messages>() {
                        @Override
                        public void onResponse(Call<Messages> call, Response<Messages> response) {
                            if(response.isSuccessful()){
                                Toast.makeText(Form.this, "User created successfully!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Messages> call, Throwable t) {
                            Toast.makeText(Form.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.d("successful","not successful");
                            Log.d("successful",t.getMessage());
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }*

                /*Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(UserService.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                UserService userService = retrofit.create(UserService.class);

                //getAllMessages();

                final Messages newMessage = new Messages();
                newMessage.setMessageId("3");

                Call<List<MessageType>> call = userService.getMessageType();
                call.enqueue(new Callback<List<MessageType>>() {
                    @Override
                    public void onResponse(Call<List<MessageType>> call, Response<List<MessageType>> response) {
                        List<MessageType> messageTypes = response.body();

                        for (MessageType m : messageTypes) {
                            final String messageTypeName, messageTypeId;
                            messageTypeName = getIntent().getStringExtra("uzenet");
                            if(m.getMessageTypeName().equals(messageTypeName)){
                                messageTypeId = m.getMessageTypeId();
                                Log.d("m",messageTypeId);
                                newMessage.setMessageTypeId(messageTypeId);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<MessageType>> call, Throwable t) {
                        Toast.makeText(Form.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });*/

//                userId=getIntent().getStringExtra("driverId");
//                Log.d("uid",userId);
//                newMessage.setDriverId(userId);
//
//                newMessage.setBus(bus.getText().toString().trim());
//                Log.d("bus",bus.getText().toString());
//                newMessage.setLine(line.getText().toString().trim());
//                Log.d("line",line.getText().toString());
//                newMessage.setDate(dateAndTime.getText().toString().trim());
//                Log.d("date",dateAndTime.getText().toString());
//                newMessage.setLat(lat.getText().toString().trim());
//                Log.d("lat",lat.getText().toString());
//                newMessage.setLon(lon.getText().toString().trim());
//                Log.d("lon",lon.getText().toString());


           //     String id="3";

          //      sendPost(id);

//                Messages newMessages = new Messages("3","2","2","4","1414","2020.11.12","46.76","24.56");

                sendPost("2","2","4","1414","2020.11.12","46.76","24.56");

//                Call<Messages> sendMessage = userService.sendMessage(id);
//                sendMessage.enqueue(new Callback<Messages>() {
//                    @Override
//                    public void onResponse(Call<Messages> call, Response<Messages> response) {
//                        if(response.isSuccessful()) {
//                            showResponse(response.body().toString());
//                            Log.d("successful","successful");
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<Messages> call, Throwable t) {
//                        Toast.makeText(Form.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//                        Log.d("successful","not successful");
//                        Log.d("successful",t.getMessage());
//                    }
//                });


//                service = ApiUtils.getAPIService();
//                service.sendMessage(newMessage).enqueue(new Callback<Messages>() {
//                    @Override
//                    public void onResponse(Call<Messages> call, Response<Messages> response) {
//                        if(response.isSuccessful()) {
//                            Log.d("successful","successful");
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<Messages> call, Throwable t) {
//                        Toast.makeText(Form.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//                        Log.d("successful","not successful");
//                        Log.d("successful",t.getMessage());
//                    }
//                });

        /*        String messageid="3";
                Call<Messages> sendMessage = userService.sendMessage(messageid);
                sendMessage.enqueue(new Callback<Messages>() {
                    @Override
                    public void onResponse(Call<Messages> call, Response<Messages> response) {
                        if(response.isSuccessful()) {
                            showResponse(response.body().toString());
                            Log.d("successful","successful");
                        }
                    }

                    @Override
                    public void onFailure(Call<Messages> call, Throwable t) {
                        Toast.makeText(Form.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("successful","not successful");
                        Log.d("successful",t.getMessage());
                    }
                });*/
            }
        });

        //getMessageTypeId();


    }

    public void getCurrentTime(View view) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

        String currentDateandTime = sdf.format(new Date());
        dateAndTime.setText(currentDateandTime);
    }


    public void getMessageTypeId(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UserService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserService userService = retrofit.create(UserService.class);
        Call<List<MessageType>> call = userService.getMessageType();
        call.enqueue(new Callback<List<MessageType>>() {
            @Override
            public void onResponse(Call<List<MessageType>> call, Response<List<MessageType>> response) {
                List<MessageType> messageTypes = response.body();

                for (MessageType m : messageTypes) {
                    final String messageTypeName, messageTypeId;
                    messageTypeName = getIntent().getStringExtra("uzenet");
                    if(m.getMessageTypeName().equals(messageTypeName)){
                        messageTypeId = m.getMessageTypeId();
                        Log.d("m",messageTypeId);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<MessageType>> call, Throwable t) {
                Toast.makeText(Form.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


   public void newMess(String j, String s, String v, String b, String d, String lon, String lat){
        Call<Messages> call = service.sendMessage(j,s,v,b,d,lon,lat);
        call.enqueue(new Callback<Messages>() {
            @Override
            public void onResponse(Call<Messages> call, Response<Messages> response) {
                Log.e("ERROR22: ", response.message());
                if(response.isSuccessful()){
                    Toast.makeText(Form.this, "User created successfully!", Toast.LENGTH_SHORT).show();
                    Log.e("ERROR22: ", response.message());

                }
            }

            @Override
            public void onFailure(Call<Messages> call, Throwable t) {
                Log.e("ERROR2: ", t.getMessage());
            }
        });
    }

    public void sendPost(String j, String s, String v, String b, String d, String lon, String lat) {
        //service = ApiUtils.getAPIService();
        service.sendMessage(j,s,v,b,d,lon,lat).enqueue(new Callback<Messages>() {
            @Override
            public void onResponse(Call<Messages> call, Response<Messages> response) {
                Log.d("successful","successful2");
                Log.d("successful",response.message());
                Log.d("successful",response.body().toString());
                Log.d("successful",response.errorBody().toString());
                if(response.isSuccessful()) {
                    showResponse(response.body().getDriverId());
                    Log.d("successful","successful");
                    Log.d("successful",response.message());
                }
            }

            @Override
            public void onFailure(Call<Messages> call, Throwable t) {
                Toast.makeText(Form.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("successful","not successful");
                Log.d("successful",t.getMessage());
            }
        });

    }


    public void showResponse(String response) {
        if (tx.getVisibility() == View.GONE) {
            tx.setVisibility(View.VISIBLE);
        }
        tx.setText(response);
    }


}
