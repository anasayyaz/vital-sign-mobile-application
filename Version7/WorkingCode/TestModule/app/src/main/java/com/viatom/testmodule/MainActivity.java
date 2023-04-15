
package com.viatom.testmodule;
import android.content.SharedPreferences;
import android.util.Base64;
import android.Manifest;
import android.app.ProgressDialog;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Base64;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.viatom.checkmelib.bluetooth.BTConnectListener;
import com.viatom.checkmelib.bluetooth.BTUtils;
import com.viatom.checkmelib.bluetooth.GetInfoThreadListener;
import com.viatom.checkmelib.bluetooth.ReadFileListener;
import com.viatom.checkmelib.measurement.BPCalItem;
import com.viatom.checkmelib.measurement.CheckmeDevice;
import com.viatom.checkmelib.measurement.ECGInnerItem;
import com.viatom.checkmelib.measurement.ECGItem;
import com.viatom.checkmelib.measurement.MeasurementConstant;
import com.viatom.checkmelib.measurement.SPO2Item;
import com.viatom.checkmelib.measurement.TempItem;
import com.viatom.checkmelib.measurement.User;
import com.viatom.checkmelib.utils.ClsUtils;
//import com.viatom.checkmelib.utils.StringUtils;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.FileWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import static android.R.layout.simple_spinner_item;

public class MainActivity extends AppCompatActivity implements BTConnectListener, GetInfoThreadListener, ReadFileListener {
    private RequestQueue requestQueue;
    public static final String SHARED_PREFS= "sharedPrefs";
    private Button btn_submit;
    BluetoothAdapter bAdapter;
    private final String URLstring = "https://cloudclinicdevapi.azurewebsites.net/api/visit/visitsListAndroid";
    private final String URL = "https://cloudclinicdevapi.azurewebsites.net/api/vitalsign";
    private final String URLtoken = "https://cloudclinicdevapi.azurewebsites.net/oauth/token";
    private final String URLDevices = "https://cloudclinicdevapi.azurewebsites.net/api/device";
    private static ProgressDialog mProgressDialog;
    private ArrayList<GoodModel> goodModelArrayList;
    private ArrayList<Device> deviceModelArrayList;
    private ArrayList<String> names = new ArrayList<String>();
    private ArrayList<String> DeviceNames = new ArrayList<String>();
    private Spinner spinner,spinnerDevice;
    private String nationalID;
    private Integer ID;
    ImageButton button,refreshButton,logoutButton,buttonBluetooth;
    public static String tokenValue;

    @BindView(R.id.bt_scan)
    Button btnScan;
    @BindView(R.id.HR)
    TextView HR;
    @BindView(R.id.TEMP)
    TextView TEMP;
    @BindView(R.id.MAP)
    TextView MAP;
    @BindView(R.id.QT)
    TextView QT;
    @BindView(R.id.QTC)
    TextView QTC;
    @BindView(R.id.QRS)
    TextView QRS;
    @BindView(R.id.BPM)
    TextView BPM;
    @BindView(R.id.WEIGHT)
    TextView WEIGHT;
    @BindView(R.id.HEIGHT)
    TextView HEIGHT;
    @BindView(R.id.SPO2)
    TextView SPO2;
    @BindView(R.id.PR)
    TextView PR;
    @BindView(R.id.PI)
    TextView PI;
    @BindView(R.id.SYS)
    TextView SYS;
    @BindView(R.id.DIA)
    TextView DIA;
    @BindView(R.id.textView)
    TextView t;
    @BindView(R.id.textView2)
    TextView t2;
    @BindView(R.id.textView3)
    TextView t3;
    @BindView(R.id.textView4)
    TextView t4;
    @BindView(R.id.textView6)
    TextView t6;
    @BindView(R.id.textView7)
    TextView t7;
    @BindView(R.id.textView8)
    TextView t8;
    @BindView(R.id.textView11)
    TextView t11;
    @BindView(R.id.textView12)
    TextView t12;
    @BindView(R.id.textView13)
    TextView t13;
    @BindView(R.id.textView14)
    TextView t14;
    @BindView(R.id.textView15)
    TextView t15;
    @BindView(R.id.textView16)
    TextView t16;
    @BindView(R.id.textView17)
    TextView t17;
    @BindView(R.id.textView18)
    TextView t18;
    @BindView(R.id.textView19)
    TextView t19;
    @BindView(R.id.mainDateTime1)
    TextView mainDateTime1;
    @BindView(R.id.mainDIA)
    TextView mainDia;
    @BindView(R.id.mainHeight)
    TextView mainHeight;
    @BindView(R.id.mainHR)
    TextView mainHR;
    @BindView(R.id.mainMAP)
    TextView mainMAP;
    @BindView(R.id.mainPRBP)
    TextView mainPRBP;
    @BindView(R.id.mainPI)
    TextView mainPI;
    @BindView(R.id.mainPRSPO2)
    TextView mainPRSPO2;
    @BindView(R.id.mainQRS)
    TextView mainQRS;
    @BindView(R.id.mainQT)
    TextView mainQT;
    @BindView(R.id.mainQTc)
    TextView mainQTc;
    @BindView(R.id.mainSPO2)
    TextView mainSPO2;
    @BindView(R.id.mainSYS)
    TextView mainSYS;
    @BindView(R.id.mainSysDia)
    TextView mainSysDia;
    @BindView(R.id.mainTemp)
    TextView mainTemp;
    @BindView(R.id.mainWeight)
    TextView mainWeight;
    @BindView(R.id.mainDateTime2)
    TextView mainDateTime2;
    @BindView(R.id.textDIA)
    TextView textDIA;
    @BindView(R.id.textECG)
    TextView textECG;
    @BindView(R.id.textHeight)
    TextView textHeight;
    @BindView(R.id.textHR)
    TextView textHR;
    @BindView(R.id.textMAP)
    TextView textMAP;
    @BindView(R.id.textmmHG)
    TextView textmmHG;
    @BindView(R.id.textNIBP)
    TextView textNIBP;
    @BindView(R.id.textOsci)
    TextView textOsci;
    @BindView(R.id.textPI)
    TextView textPI;
    @BindView(R.id.textPRBP)
    TextView textPRBP;
    @BindView(R.id.textPRSPO2)
    TextView textPRSPO2;
    @BindView(R.id.textQRS)
    TextView textQRS;
    @BindView(R.id.textQT)
    TextView textQT;
    @BindView(R.id.textQTc)
    TextView textQTc;
    @BindView(R.id.textRER)
    TextView textRER;
    @BindView(R.id.textSPO2)
    TextView textSPO2;
    @BindView(R.id.textTemp)
    TextView textTemp;
    @BindView(R.id.textSYS)
    TextView textSYS;
    //user
    private String weight;
    private  String height;
    //TEMP
    private String tempValue;
    //BP
    private String bpSysValue;
    private String bpDiaValue;
    private String bpPRValue;
    private String bpMAPValue;
    private String bpDateValue;
    //SPO2
    private String SPO2oxygenValue;
    private String SPO2PRvalue;
    private String SPO2PIvalue;
    //ECG
    private String ecgQTvalue;
    private String ecgQTCvalue;
    private String ecgHRvalue;
    private String ecgQrsvalue;
    private String ecgstvalue;
    private String ecgTimeLengthtvalue;
    private String ecgDateValue;
    private String ecgResultValue;
    private String ecgDataValue;
    //
    ECGInnerItem ecginneritem;
    ECGItem ecgLatestItem;

    @OnClick(R.id.bt_scan)
    public void startScan() {
        searchBT();
    }

    @OnClick(R.id.bt_download)
    public void startDownload() {
//        downloadUser();
    }

    private static  String DEVICE_NAME;
    private static final int CALL_PHONE_REQUEST_CODE = 8888;
    private final static String TAG = "Checkmelib";
    public static BTUtils.BTBinder btBinder;
    public static boolean isConnected = false;

    private BluetoothAdapter mBtAdapter;
    private static BluetoothDevice mDevice;

    public ServiceConnection connection = new ServiceConnection()
    {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "try to connect");

            btBinder = (BTUtils.BTBinder) service;

            btBinder.interfaceConnect(mDevice.getAddress(), MainActivity.this);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "Service Disconnected: " + name.toString());
        }
    };

    // 查找到设备和搜索完成action监听器
    private final BroadcastReceiver mReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {

                if (mDevice.getBondState() == BluetoothDevice.BOND_BONDED) {
                    // pair
                    Log.d(TAG, "paired");
                    Intent mIntent = new Intent("com.viatom.checkmelib.bluetooth.BTUtils");
                    mIntent.setPackage(getPackageName());
                    bindService(mIntent, connection, Service.BIND_AUTO_CREATE);
                }


            } else if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) {

                if (isConnected) {
                    // disconnected
                    isConnected = false;
                    unbindService(connection);// debug warning
                    onConnectFailed(BTConnectListener.ERR_CODE_NORMAL);
                    Log.d(TAG, "disconnected");
                }

            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {

                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Log.d(TAG, "device found" + device.getName() + " ====" + device.getAddress());

                if (device.getType() == BluetoothDevice.DEVICE_TYPE_CLASSIC && device.getName() != null && device.getName().equals(DEVICE_NAME)) {
                    Toast toast=Toast.makeText(getApplicationContext(),"inside"+device.getAddress(),Toast.LENGTH_LONG);
                    toast.setMargin(50,50);
                    toast.show();
                    mDevice = mBtAdapter.getRemoteDevice(device.getAddress());
                    Log.d(TAG, mDevice.getName() + " " + mDevice.getAddress());

                    connectDevice();
                }

            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                // discover finished
                Log.d(TAG, "discover finished");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //setting 1 for auto login
        LoginActivity.state=1;
        bAdapter= BluetoothAdapter.getDefaultAdapter();
        setContentView(R.layout.activity_main);
        getToken();
        button=(ImageButton) findViewById(R.id.button);
        refreshButton=(ImageButton)findViewById(R.id.refreshButton);
        logoutButton=(ImageButton)findViewById(R.id.logoutButton);
        buttonBluetooth=(ImageButton)findViewById(R.id.buttonBluetooth);
        spinner = findViewById(R.id.spCompany);
        spinnerDevice=findViewById(R.id.spDevice);

        Log.d(TAG, LoginActivity.d+""+LoginActivity.user+""+LoginActivity.p);
        Toast toast=Toast.makeText(getApplicationContext(),LoginActivity.d+""+LoginActivity.user+""+LoginActivity.p,Toast.LENGTH_LONG);
        toast.setMargin(50,50);
        toast.show();



        try {

            retrieveJSON();
            retrieveDevices();



        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(spinner);
            android.widget.ListPopupWindow popupWindowDevice = (android.widget.ListPopupWindow) popup.get(spinnerDevice);
            // Set popupWindow height to 500px
            popupWindow.setHeight(750);

            popupWindowDevice.setHeight(750);

        }
        catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
             Toast.makeText(getApplicationContext(),"Data not downloaded... Retry",Toast.LENGTH_LONG);
        }





        btn_submit = (Button) findViewById(R.id.btn_submit);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast=Toast.makeText(getApplicationContext(),LoginActivity.d+""+LoginActivity.user+""+LoginActivity.p,Toast.LENGTH_LONG);
                toast.setMargin(50,50);
                toast.show();


                try {
                    retrieveJSON();
                    finish();
                    startActivity(getIntent());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        buttonBluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bAdapter == null)
                {
                    Toast.makeText(getApplicationContext(),"Bluetooth Not Supported",Toast.LENGTH_SHORT).show();
                }
                else{
                    if(!bAdapter.isEnabled()){
                        startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE),1);
                        Toast.makeText(getApplicationContext(),"Bluetooth Turned ON",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast=Toast.makeText(getApplicationContext(),"Logging Out",Toast.LENGTH_LONG);
                toast.setMargin(50,50);
                toast.show();


                try {
                    SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("user","");
                    editor.putString("p","");
                    editor.putString("d","");
                    editor.apply();
                    goToLogin();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                if (QT.getVisibility() == View.VISIBLE)
                {
                    HR.setVisibility(View.INVISIBLE);
                    QRS.setVisibility(View.INVISIBLE);
                    QT.setVisibility(View.INVISIBLE);
                    QTC.setVisibility(View.INVISIBLE);
                    WEIGHT.setVisibility(View.INVISIBLE);
                    HEIGHT.setVisibility(View.INVISIBLE);
                    SPO2.setVisibility(View.INVISIBLE);
                    PR.setVisibility(View.INVISIBLE);
                    PI.setVisibility(View.INVISIBLE);
                    SYS.setVisibility(View.INVISIBLE);
                    DIA.setVisibility(View.INVISIBLE);
                    BPM.setVisibility(View.INVISIBLE);
                    MAP.setVisibility(View.INVISIBLE);
                    MAP.setVisibility(View.INVISIBLE);
                    TEMP.setVisibility(View.INVISIBLE);
                    t.setVisibility(View.INVISIBLE);
                    t2.setVisibility(View.INVISIBLE);
                    t6.setVisibility(View.INVISIBLE);
                    t7.setVisibility(View.INVISIBLE);
                    t8.setVisibility(View.INVISIBLE);
                    t11.setVisibility(View.INVISIBLE);
                    t12.setVisibility(View.INVISIBLE);
                    t13.setVisibility(View.INVISIBLE);
                    t14.setVisibility(View.INVISIBLE);
                    t15.setVisibility(View.INVISIBLE);
                    t16.setVisibility(View.INVISIBLE);
                    t17.setVisibility(View.INVISIBLE);
                    t18.setVisibility(View.INVISIBLE);
                    t19.setVisibility(View.INVISIBLE);
                    t3.setVisibility(View.VISIBLE);
                    t4.setVisibility(View.VISIBLE);
                    mainDia.setVisibility(View.VISIBLE);
                    mainDateTime1.setVisibility(View.VISIBLE);
                    mainHeight.setVisibility(View.VISIBLE);
                    mainHR.setVisibility(View.VISIBLE);
                    mainMAP.setVisibility(View.VISIBLE);
                    mainPI.setVisibility(View.VISIBLE);
                    mainPRBP.setVisibility(View.VISIBLE);
                    mainPRSPO2.setVisibility(View.VISIBLE);
                    mainQRS.setVisibility(View.VISIBLE);
                    mainQT.setVisibility(View.VISIBLE);
                    mainQTc.setVisibility(View.VISIBLE);
                    mainSPO2.setVisibility(View.VISIBLE);
                    mainSYS.setVisibility(View.VISIBLE);
                    mainSysDia.setVisibility(View.VISIBLE);
                    mainTemp.setVisibility(View.VISIBLE);
                    mainWeight.setVisibility(View.VISIBLE);
                    mainDateTime1.setVisibility(View.VISIBLE);
                    textDIA.setVisibility(View.VISIBLE);
                    textECG.setVisibility(View.VISIBLE);
                    textHeight.setVisibility(View.VISIBLE);
                    textHR.setVisibility(View.VISIBLE);
                    textMAP.setVisibility(View.VISIBLE);
                    textmmHG.setVisibility(View.VISIBLE);
                    textNIBP.setVisibility(View.VISIBLE);
                    textOsci.setVisibility(View.VISIBLE);
                    textPI.setVisibility(View.VISIBLE);
                    textPRBP.setVisibility(View.VISIBLE);
                    textPRSPO2.setVisibility(View.VISIBLE);
                    textQRS.setVisibility(View.VISIBLE);
                    textQT.setVisibility(View.VISIBLE);
                    textQTc.setVisibility(View.VISIBLE);
                    textRER.setVisibility(View.VISIBLE);
                    textSPO2.setVisibility(View.VISIBLE);
                    textTemp.setVisibility(View.VISIBLE);
                    mainWeight.setVisibility(View.VISIBLE);
                    textSYS.setVisibility(View.VISIBLE);
                    //saving state
                    ecgHRvalue =HR.getText().toString();
                    ecgQrsvalue=QRS.getText().toString();
                    bpPRValue=BPM.getText().toString();
                    ecgQTvalue=QT.getText().toString();
                    ecgQTCvalue=QTC.getText().toString();
                    SPO2oxygenValue=SPO2.getText().toString();
                    SPO2PRvalue=PR.getText().toString();
                    SPO2PIvalue=PI.getText().toString();
                    bpSysValue=SYS.getText().toString();
                    bpDiaValue=DIA.getText().toString();
                    tempValue=TEMP.getText().toString();
                    bpMAPValue=MAP.getText().toString();
                    height=HEIGHT.getText().toString();
                    weight=WEIGHT.getText().toString();

                    setText();
                }
                else
                {

                    HR.setVisibility(View.VISIBLE);
                    QRS.setVisibility(View.VISIBLE);
                    QT.setVisibility(View.VISIBLE);
                    QTC.setVisibility(View.VISIBLE);
                    WEIGHT.setVisibility(View.VISIBLE);
                    HEIGHT.setVisibility(View.VISIBLE);
                    SPO2.setVisibility(View.VISIBLE);
                    PR.setVisibility(View.VISIBLE);
                    PI.setVisibility(View.VISIBLE);
                    SYS.setVisibility(View.VISIBLE);
                    DIA.setVisibility(View.VISIBLE);
                    BPM.setVisibility(View.VISIBLE);
                    MAP.setVisibility(View.VISIBLE);
                    TEMP.setVisibility(View.VISIBLE);
                    t.setVisibility(View.VISIBLE);
                    t2.setVisibility(View.VISIBLE);
                    t6.setVisibility(View.VISIBLE);
                    t7.setVisibility(View.VISIBLE);
                    t8.setVisibility(View.VISIBLE);
                    t11.setVisibility(View.VISIBLE);
                    t12.setVisibility(View.VISIBLE);
                    t13.setVisibility(View.VISIBLE);
                    t14.setVisibility(View.VISIBLE);
                    t15.setVisibility(View.VISIBLE);
                    t16.setVisibility(View.VISIBLE);
                    t17.setVisibility(View.VISIBLE);
                    t18.setVisibility(View.VISIBLE);
                    t19.setVisibility(View.VISIBLE);
                    t3.setVisibility(View.INVISIBLE);
                    t4.setVisibility(View.INVISIBLE);
                    mainDia.setVisibility(View.INVISIBLE);
                    mainDateTime1.setVisibility(View.INVISIBLE);
                    mainHeight.setVisibility(View.INVISIBLE);
                    mainHR.setVisibility(View.INVISIBLE);
                    mainMAP.setVisibility(View.INVISIBLE);
                    mainPI.setVisibility(View.INVISIBLE);
                    mainPRBP.setVisibility(View.INVISIBLE);
                    mainPRSPO2.setVisibility(View.INVISIBLE);
                    mainQRS.setVisibility(View.INVISIBLE);
                    mainQT.setVisibility(View.INVISIBLE);
                    mainQTc.setVisibility(View.INVISIBLE);
                    mainSPO2.setVisibility(View.INVISIBLE);
                    mainSYS.setVisibility(View.INVISIBLE);
                    mainSysDia.setVisibility(View.INVISIBLE);
                    mainTemp.setVisibility(View.INVISIBLE);
                    mainWeight.setVisibility(View.INVISIBLE);
                    mainDateTime2.setVisibility(View.INVISIBLE);
                    textDIA.setVisibility(View.INVISIBLE);
                    textECG.setVisibility(View.INVISIBLE);
                    textHeight.setVisibility(View.INVISIBLE);
                    textHR.setVisibility(View.INVISIBLE);
                    textMAP.setVisibility(View.INVISIBLE);
                    textmmHG.setVisibility(View.INVISIBLE);
                    textNIBP.setVisibility(View.INVISIBLE);
                    textOsci.setVisibility(View.INVISIBLE);
                    textPI.setVisibility(View.INVISIBLE);
                    textPRBP.setVisibility(View.INVISIBLE);
                    textPRSPO2.setVisibility(View.INVISIBLE);
                    textQRS.setVisibility(View.INVISIBLE);
                    textQT.setVisibility(View.INVISIBLE);
                    textQTc.setVisibility(View.INVISIBLE);
                    textRER.setVisibility(View.INVISIBLE);
                    textSPO2.setVisibility(View.INVISIBLE);
                    mainWeight.setVisibility(View.INVISIBLE);
                    textTemp.setVisibility(View.INVISIBLE);
                    textSYS.setVisibility(View.INVISIBLE);
                    //saving state
                    ecgHRvalue =HR.getText().toString();
                    ecgQrsvalue=QRS.getText().toString();
                    bpPRValue=BPM.getText().toString();
                    ecgQTvalue=QT.getText().toString();
                    ecgQTCvalue=QTC.getText().toString();
                    SPO2oxygenValue=SPO2.getText().toString();
                    SPO2PRvalue=PR.getText().toString();
                    SPO2PIvalue=PI.getText().toString();
                    bpSysValue=SYS.getText().toString();
                    bpDiaValue=DIA.getText().toString();
                    tempValue=TEMP.getText().toString();
                    bpMAPValue=MAP.getText().toString();
                    height=HEIGHT.getText().toString();
                    weight=WEIGHT.getText().toString();

                    setText();

                }
            }
        });
        ButterKnife.bind(this);




    }

    public void getToken()
    {
        StringRequest request = new StringRequest(Request.Method.POST, URLtoken, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject obj = new JSONObject(response);
                    tokenValue=obj.getString("access_token");
                    Toast toast=Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_SHORT);
                    toast.setMargin(50,50);
                    toast.show();



                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast toast=Toast.makeText(getApplicationContext(),"Error Wrong credentials!!!",Toast.LENGTH_SHORT);
                    toast.setMargin(50,50);
                    toast.show();
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast toast=Toast.makeText(getApplicationContext(),"Error Wrong credentials!!!",Toast.LENGTH_SHORT);
                toast.setMargin(50,50);
                toast.show();
                Log.e("error is ", "" + error);
                try {
                    LoginActivity.state=0;
                    goToLogin();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }) {


            //Pass Your Parameters here
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                params.put("grant_type", "password");
                params.put("username", LoginActivity.user);
                params.put("domain",LoginActivity.d);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }

    @Override
    public void onReadPartFinished(String fileName, byte fileType, float percentage) {
        Log.d(TAG, "download " + fileName + "part  " + percentage);
    }

    @Override
    public void onReadSuccess(String fileName, byte fileType, byte[] fileBuf) throws JSONException {
        Log.d(TAG, "onReadSuccess: download success");
        showToast("Data Downloaded");

        readData(fileName, fileType, fileBuf);
        removeSimpleProgressDialog();
    }

    @Override
    public void onReadFailed(String fileName, byte fileType, byte errCode) {
        Log.d(TAG, "download failed: " + fileName + " " + errCode);
    }

    public void downloadUser() {
        if (!isConnected) {
            showToast("Please Connect Device");
            return;
        }

        btBinder.interfaceReadFile(MeasurementConstant.FILE_NAME_USER_LIST, MeasurementConstant.CMD_TYPE_USER_LIST, 5000, this);
    }

    public void handleUsers(byte[] bytes) {
        ArrayList<User> users = User.getUserList(bytes);
        Log.d(TAG, "user list size: " + (users == null ? "0" : users.size()));
        ArrayList<String> userNames = new ArrayList<String>();
        for (User user : users) {
            userNames.add(user.getUserInfo().getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, userNames);

    }

    public void downloadEcgList() {
        if (!isConnected) {
            showToast("please connect device");
            return;
        }

        btBinder.interfaceReadFile(MeasurementConstant.FILE_NAME_ECG_LIST, MeasurementConstant.CMD_TYPE_ECG_LIST, 5000, this);
    }

    public void downloadTempList() {
        if (!isConnected) {
            showToast("please connect device");
            return;
        }

        btBinder.interfaceReadFile(MeasurementConstant.FILE_NAME_TEMP_LIST, MeasurementConstant.CMD_TYPE_TEMP, 5000, this);
    }

    public void downloadECGInnerdata(String innerDataFilename) {
        if (!isConnected) {
            showToast("please connect device");
            return;
        }

        btBinder.interfaceReadFile(innerDataFilename, MeasurementConstant.CMD_TYPE_ECG_NUM, 5000, this);
    }

    public void downloadSPO2() {
        if (!isConnected) {
            showToast("please connect device");
            return;
        }
        btBinder.interfaceReadFile(MeasurementConstant.FILE_NAME_SPO2_LIST, MeasurementConstant.CMD_TYPE_SPO2, 5000, this);
    }

    public void downloadBP() {
        if (!isConnected) {
            showToast("please connect device");
            return;
        }
        btBinder.interfaceReadFile(MeasurementConstant.FILE_NAME_BPCAL, MeasurementConstant.CMD_TYPE_BPCAL, 5000, this);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void handleEcgList(byte[] bytes) {
        ArrayList<ECGItem> ecgItems = ECGItem.getEcgItemList(bytes);
        Log.d(TAG, "ecgitem list size: " + (ecgItems == null ? "0" : ecgItems.size()));
        ecgLatestItem = ecgItems.get(ecgItems.size() - 1);
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String ecginnerDataFilename = dateFormat.format(ecgLatestItem.getDate());


        Date d=ecgLatestItem.getDate();
        TimeZone tz=TimeZone.getTimeZone("UTC");

        DateFormat df=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        df.setTimeZone(tz);
        ecgDateValue=df.format(d);
        int result=ecgLatestItem.getImgResult();
        if(result == 0)
        {
            //regular
            ecgResultValue="0";
        }
        else
        {
            ecgResultValue="1";
        }
        downloadECGInnerdata(ecginnerDataFilename);
    }

    public void handleEcgDetails(byte[] bytes) {

        ecginneritem = new ECGInnerItem(bytes);
        int [] data=ecginneritem.getECGData();
        String d= Arrays.toString(data);
        ecgDataValue=d;
        int qtresult = ecginneritem.getQT();
        String sQT = new String(String.valueOf(qtresult));
        ecgQTvalue = sQT;
        int qtcresult = ecginneritem.getQTc();
        String sQTc = new String(String.valueOf(qtcresult));
        ecgQTCvalue = sQTc;
        int qrsresult = ecginneritem.getQRS();
        String sQRS = new String(String.valueOf(qrsresult));
        ecgQrsvalue = sQRS;
        int hrresult = ecginneritem.getHR();
        String sHR = new String(String.valueOf(hrresult));
        ecgHRvalue = sHR;
        int stresult = ecginneritem.getST();
        String sST = new String(String.valueOf(stresult));
        ecgstvalue = sST;
        int timelengthresult = ecginneritem.getQRS();
        String sTL = new String(String.valueOf(timelengthresult));
        ecgTimeLengthtvalue = sTL;
        Log.d(TAG, "Got Inner data");
    }

    public void handleTempList(byte[] bytes) {
        TempItem tempItem = TempItem.getlatestTempItem(bytes);
        float tempresult = tempItem.getResult();
        tempresult=(tempresult*9)/5+32;
        DecimalFormat df = new DecimalFormat("##.#");
        String formattedtemp = df.format(tempresult);
        tempValue = formattedtemp;
    }

    public void handleSPO2List(byte[] bytes) {
        SPO2Item spo2Item = SPO2Item.getlatestSPO2Item(bytes);
        byte oxygenresult = spo2Item.getOxygen();
        String s = new String(String.valueOf(oxygenresult));
        SPO2oxygenValue = s;

        int prresult = spo2Item.getPr();
        DecimalFormat dfpr = new DecimalFormat("##");
        String formattedpr = dfpr.format(prresult);
        SPO2PRvalue = formattedpr;

        float piresult = spo2Item.getPi();
        DecimalFormat dfpi = new DecimalFormat("##.#");
        String formattedpi = dfpi.format(piresult);
        SPO2PIvalue = formattedpi;

        Log.d(TAG, "Got SPO2 Item");
    }

    public void handleBPList(byte[] bytes) {
        BPCalItem bpItem = BPCalItem.getlatestBPItem(bytes);
        Log.d(TAG, "Got BP Item");




//        Date d=ecgLatestItem.getDate();
//        TimeZone tz=TimeZone.getTimeZone("UTC");
//        DateFormat df=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
//        df.setTimeZone(tz);
//        ecgDateValue=df.format(d);

        Date d=bpItem.getCalDate();
        TimeZone tz=TimeZone.getTimeZone("UTC");
        DateFormat df=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        df.setTimeZone(tz);
        bpDateValue=df.format(d);


        short sysresult = bpItem.getSys();
        String sSys = new String(String.valueOf(sysresult));
        bpSysValue = sSys;

        byte diaresult = bpItem.getDiaSys();
        String sDia = new String(String.valueOf(diaresult));
        bpDiaValue = sDia;


        byte prresult = bpItem.getDiaSys();
        String sPR = new String(String.valueOf(diaresult));
        bpPRValue = sPR;

        Double sys=Double.parseDouble(bpSysValue);
        Double dia=Double.parseDouble(bpDiaValue);
        Double sysdia= ((2*dia)+sys)/3;
        sysdia=Math.round(sysdia * 100.0) / 100.0;
        String Map=Double.toString(sysdia);
        bpMAPValue=Map;


    }

    private void Submit() throws IOException, JSONException {


        JSONObject obj = new JSONObject();

        obj.put("patient_NationalID",nationalID);
        obj.put("visitID",ID);
        obj.put("dateTime1","2021-02-26T16:45:07.017");
        obj.put("dateTime2","2021-02-26T16:45:07.017");
      //obj.put("datetimeECG","2021-02-26T16:45:07.017");
      obj.put("datetimeBP","2021-06-25T16:45:07.017");
        obj.put("datetimeECG",ecgDateValue);
//        obj.put("datetimeBP",bpDateValue);

      //obj.put("datetimeBP",bpDateValue);
        obj.put("hr",HR.getText().toString());
        obj.put("qrs",QRS.getText().toString());
        obj.put("bpm",BPM.getText().toString());
        obj.put("qt",QT.getText().toString());
        obj.put("qtc",QTC.getText().toString());
        obj.put("height",HEIGHT.getText().toString());
        obj.put("weight",WEIGHT.getText().toString());
        obj.put("spO2",SPO2.getText().toString());
        obj.put("pr",PR.getText().toString());
        obj.put("pi",PI.getText().toString());
        obj.put("sys",SYS.getText().toString());
        obj.put("dia",DIA.getText().toString());
        obj.put("temp",TEMP.getText().toString());
        obj.put("map",MAP.getText().toString());
        //temporary
        obj.put("ma",ecgResultValue);
        obj.put("ecg",ecgDataValue);

         requestQueue = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject objres = new JSONObject(response);



                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Server Error", Toast.LENGTH_LONG).show();


                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();


            }
        }){


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
//                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer " + tokenValue);
                return params;
            }
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                try {
                    responseString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                // can get more details such as response.headers
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return obj== null ? null : obj.toString().getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    //Log.v("Unsupported Encoding while trying to get the bytes", data);
                    return null;
                }
            }

        };
        requestQueue.add(stringRequest);
        Log.d("oboject", String.valueOf(obj));
        Log.i("oboject", String.valueOf(obj));
        showToast("Data Uploaded");

    }


    public void onErrorResponse(VolleyError error) {

        // As of f605da3 the following should work
        NetworkResponse response = error.networkResponse;
        if (error instanceof ServerError && response != null) {
            try {
                String res = new String(response.data,
                        HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                // Now you can use any deserializer to make sense of data
                JSONObject obj = new JSONObject(res);
            } catch (UnsupportedEncodingException e1) {
                // Couldn't properly decode data to string
                e1.printStackTrace();
            } catch (JSONException e2) {
                // returned data is not JSONObject?
                e2.printStackTrace();
            }
        }
    }
    public void readData(String fileName, byte fileType, byte[] fileBuf) throws JSONException {
        switch (fileType) {
            case MeasurementConstant.CMD_TYPE_USER_LIST:
                handleUsers(fileBuf);
                downloadEcgList();
                break;
            case MeasurementConstant.CMD_TYPE_ECG_LIST:
                handleEcgList(fileBuf);
                //downloadECGInnerdata("20200616103756");
                break;
            case MeasurementConstant.CMD_TYPE_ECG_NUM:
                handleEcgDetails(fileBuf);
                downloadTempList();
                break;
            case MeasurementConstant.CMD_TYPE_TEMP:
                handleTempList(fileBuf);
                downloadSPO2();
                break;
            case MeasurementConstant.CMD_TYPE_SPO2:
                handleSPO2List(fileBuf);
                downloadBP();
                break;
            case MeasurementConstant.CMD_TYPE_BPCAL:
                handleBPList(fileBuf);
                int ecgLength = ecginneritem.ecgData.length;
                int ecgResult = ecgLatestItem.imgResult;
                break;

            default:
                break;
        }
        setText();
    }


    @Override
    public void onConnectSuccess() {
        Log.d(TAG, "Connect Success");
        showToast("Connect Success");
        isConnected = true;
        btBinder.interfaceGetInfo(2000, MainActivity.this);
    }

    @Override
    public void onConnectFailed(byte errorCode) {
        Log.d(TAG, "Connect Failed" + errorCode);
        showToast("Connect Failed");
        unbindService(connection);
    }

    @Override
    public void onGetInfoSuccess(String checkmeInfo) {
        showSimpleProgressDialog(this, "Connecting...", "will take few seconds", false);
        Log.d(TAG, "Device Connected" + " " + checkmeInfo);
        showToast("Device Connected");
        readCheckmeInfo(checkmeInfo);
        downloadUser();
    }

    @Override
    public void onGetInfoFailed(byte errCode) {
        Log.d(TAG, "get info failed" + " " + errCode);
        showToast("get info failed");
    }


    public void readCheckmeInfo(String info) {
        CheckmeDevice device = CheckmeDevice.decodeCheckmeDevice(info);

    }
    private void setText(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                HR.setText(ecgHRvalue);
                QT.setText(ecgQTvalue);
                QTC.setText(ecgQTCvalue);
                QRS.setText(ecgQrsvalue);
                BPM.setText(bpPRValue);
                SPO2.setText(SPO2oxygenValue);
                PI.setText(SPO2PIvalue);
                PR.setText(bpPRValue);
                SYS.setText(bpSysValue);
                DIA.setText(bpDiaValue);
                MAP.setText(bpMAPValue);
                WEIGHT.setText(weight);
                HEIGHT.setText(height);
                TEMP.setText(tempValue);
                mainHR.setText(ecgHRvalue);
                mainQT.setText(ecgQTvalue);
                mainQTc.setText(ecgQTCvalue);
                mainQRS.setText(ecgQrsvalue);
                mainPRBP.setText(bpPRValue+"\n/min");
                mainSPO2.setText(SPO2oxygenValue+"\n%");
                mainPRSPO2.setText(bpPRValue+"\n/min");
                mainSYS.setText(bpSysValue);
                mainDia.setText(bpDiaValue);
                mainSysDia.setText(bpSysValue+"/"+bpDiaValue);
                mainMAP.setText(bpMAPValue);
                mainPI.setText(SPO2PIvalue);
                mainTemp.setText(tempValue);
                mainHeight.setText(height);
                mainWeight.setText(weight);
                //mainDateTime1.setText(bpDateValue.toString());
                //mainDateTime2.setText(ecgDateValue.toString());
//                textRER.setText(ecgResultValue);
                // Need to add all other variables
            }
        });
    }
    public void connectDevice() {
        if (mBtAdapter.isDiscovering()) {
            mBtAdapter.cancelDiscovery();
        }

        if (mDevice.getBondState() != BluetoothDevice.BOND_BONDED) {
            Log.d(TAG, "not bonded");
            try {
                ClsUtils.cancelPairingUserInput(mDevice.getClass(), mDevice);
                ClsUtils.createBond(mDevice.getClass(), mDevice);
            } catch (Exception e) {
                e.printStackTrace();
                Log.d(TAG, e.toString());
            }
        } else {
            Log.d(TAG, "bonded, try connect");
            Intent mIntent = new Intent();
            mIntent.setAction("com.viatom.checkmelib.bluetooth.BTUtils");
            mIntent.setPackage(getPackageName());
            bindService(mIntent, connection, Service.BIND_AUTO_CREATE);
        }


    }

    public void searchBT() {
        showSimpleProgressDialog(this, "Connecting...", "Getting Information From "+DEVICE_NAME, false);
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBtAdapter.isDiscovering()) {
            mBtAdapter.cancelDiscovery();
        }

        mBtAdapter.startDiscovery();
        Log.d(TAG, "start discovery");
    }

    public void iniReceiver() {
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        filter.addAction(BluetoothDevice.ACTION_PAIRING_REQUEST);
        filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        this.registerReceiver(mReceiver, filter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CALL_PHONE_REQUEST_CODE) {
            if (checkPermission()) {
                iniReceiver();
            }
        }
    }

    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        CALL_PHONE_REQUEST_CODE);
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    public void showToast(String s) {
        runOnUiThread(() -> Toast.makeText(this, s, Toast.LENGTH_SHORT).show());
    }

    public void showToast(int stringId) {
        runOnUiThread(() -> Toast.makeText(this, stringId, Toast.LENGTH_SHORT).show());
    }

    private void retrieveJSON() throws IOException {


        showSimpleProgressDialog(this, "Loading...", "Getting patients information", false);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLstring,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            goodModelArrayList = new ArrayList<>();

                            for (int i = 0; i < jsonArray.length(); i++) {

                                GoodModel playerModel = new GoodModel();
                                JSONObject dataobj = jsonArray.getJSONObject(i);

                                playerModel.setIsActive(dataobj.getBoolean("isActive"));
                                if (playerModel.getIsActive() == true) {
                                    playerModel.setPatientName(dataobj.getString("patientName"));
                                    playerModel.setPatientNationalID(dataobj.getString("patient_NationalID"));
                                    playerModel.setId(dataobj.getInt("id"));
                                    playerModel.setStartDateTime(dataobj.getString("startDateTime"));
                                    goodModelArrayList.add(playerModel);
                                }
                            }
                            String s1, s2;
                            for (int i = 0; i < goodModelArrayList.size(); i++) {
                                s1 = goodModelArrayList.get(i).getPatientName().toString();
                                s2 = goodModelArrayList.get(i).getId().toString();
                                names.add(s1 + "       " + s2);
                            }


                            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(MainActivity.this, simple_spinner_item, names);
                            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                            spinner.setAdapter(spinnerArrayAdapter);
                            spinner.setSelection(0);
                            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    // On selecting a spinner item
                                    String item = parent.getItemAtPosition(position).toString();
                                    nationalID = goodModelArrayList.get(position).getPatientNationalID();
                                    ID = goodModelArrayList.get(position).getId();
                                }
                                public void onNothingSelected(AdapterView<?> arg0) {
                                }
                            });
                            removeSimpleProgressDialog();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            try {
                                LoginActivity.state=0;
                                goToLogin();
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer " + tokenValue);
                return params;
            }

        };

        // request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void retrieveDevices() throws IOException {


        showSimpleProgressDialog(this, "Loading...", "Getting devices information", false);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLDevices,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            deviceModelArrayList = new ArrayList<>();

                            for (int i = 0; i < jsonArray.length(); i++) {

                                Device playerModel = new Device();
                                JSONObject dataobj = jsonArray.getJSONObject(i);


                                    playerModel.setName(dataobj.getString("name"));
                                    playerModel.setType(dataobj.getString("type"));
                                    playerModel.setId(dataobj.getInt("id"));
                                    deviceModelArrayList.add(playerModel);

                            }
                            String s1;
                            for (int i = 0; i < deviceModelArrayList.size(); i++) {
                                s1 = deviceModelArrayList.get(i).getName().toString();
                                DeviceNames.add(s1 );
                            }


                            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(MainActivity.this, simple_spinner_item, DeviceNames);
                            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                            spinnerDevice.setAdapter(spinnerArrayAdapter);
                            spinnerDevice.setSelection(0);
                            spinnerDevice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    // On selecting a spinner item
                                    String item = parent.getItemAtPosition(position).toString();
                                    DEVICE_NAME = deviceModelArrayList.get(position).getName();
                                    Toast.makeText(getApplicationContext(), DEVICE_NAME, Toast.LENGTH_LONG).show();

                                    if (checkPermission()) {
                                        iniReceiver();
                                    }
                                }
                                public void onNothingSelected(AdapterView<?> arg0) {
                                    Toast.makeText(getApplicationContext(), "Nothing Selected", Toast.LENGTH_LONG).show();
                                }
                            });
                            removeSimpleProgressDialog();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            try {
                                LoginActivity.state=0;
                                goToLogin();
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer " + tokenValue);
                return params;
            }

        };

        // request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


    public static void removeSimpleProgressDialog() {
        try {
            if (mProgressDialog != null) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                    mProgressDialog = null;
                }
            }
        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();

        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void goToLogin() throws IOException
    {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }
    public static void showSimpleProgressDialog(Context context, String title,
                                                String msg, boolean isCancelable) {
        try {
            if (mProgressDialog == null) {
                mProgressDialog = ProgressDialog.show(context, title, msg);
                mProgressDialog.setCancelable(isCancelable);
            }

            if (!mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }

        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();
        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}