package fine.koacaiia.recyclerviewandcameraviewexercise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.google.android.material.datepicker.MaterialDatePicker.Builder.datePicker;


public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback {
    RecyclerView recyclerView;
    MnfCargoListAdapter adapter;
    ArrayList<MnfCargoList> list;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    Camera camera;
    SurfaceView surfaceView;
    SurfaceHolder holder;
    String[] permission_list={Manifest.permission.CAMERA};

    Button btnDatePicker;
    Button btnSortByDate;
    String strDateStart;
    String strDateEnd;
    String strDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestPermissions(permission_list,0);
        surfaceView=findViewById(R.id.surfaceView);
        holder=surfaceView.getHolder();
        holder.addCallback(this);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        cameraPreview();

        recyclerView=findViewById(R.id.recyclerView);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        database=FirebaseDatabase.getInstance();
        databaseReference=database.getReference("MnF");
        putData();
        list=new ArrayList<>();
        adapter=new MnfCargoListAdapter(list);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        btnDatePicker=findViewById(R.id.btn_date_picker);
        btnDatePicker.setOnClickListener(v->{
            datepicker();
            dialogDatePicker();
        });
        datepicker();
        dialogDatePicker();

        btnSortByDate=findViewById(R.id.btnSortByDate);
        btnSortByDate.setOnClickListener(v->{
            Toast.makeText(this,"StartDay:"+strDateStart+"/EndDay:"+strDateEnd,Toast.LENGTH_SHORT).show();
        });
    }

    private void datepicker() {
        Calendar calendar= Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH)+1;
        String strMonth;
        if(month<10){
            strMonth="0"+month;
        }else{
            strMonth=String.valueOf(month);
        }
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        String strDay;
        if(day<10){
            strDay="0"+day;
        }else{
            strDay=String.valueOf(day);
        }
        strDateStart=year+"년"+strMonth+"월"+strDay+"일";
        Toast.makeText(this,strDateStart,Toast.LENGTH_SHORT).show();
    }

    private void dialogDatePicker() {
        View view=getLayoutInflater().inflate(R.layout.datepicker_spinner,null);
        DatePicker datePicker=view.findViewById(R.id.datePicker_start);
        TextView txtDate=view.findViewById(R.id.txtSearchDate_start);
        TextView txtDate2=view.findViewById(R.id.txtSearchDate_end);
        int year=Integer.parseInt(strDateStart.substring(0,4));
        int month=Integer.parseInt(strDateStart.substring(6,7));
        int day=Integer.parseInt(strDateStart.substring(9,10));

        strDate=(year+"_"+month+"_"+day);
        txtDate.setText(strDate);
        txtDate2.setText(strDate);
        Log.i("koacaiia","Month Value++:"+month);
        datePicker.init(year,month-1,day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                strDate=(year+"_"+(monthOfYear+1)+"_"+dayOfMonth);
          }
        });

        txtDate.setOnClickListener(v->{
           txtDate.setText(strDate);
           strDateStart=strDate;
        });

        txtDate2.setOnClickListener(v->{
            txtDate2.setText(strDate);
            strDateEnd=strDate;
        });

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setView(view);
        AlertDialog aBuilder=builder.create();
        aBuilder.show();
    }

    private void cameraPreview() {
        camera= Camera.open();
        camera.setDisplayOrientation(90);
        try {
            camera.setPreviewDisplay(holder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        camera.startPreview();
    }

    private void putData() {
        for(int i=1;i<31;i++){
            String dateInt=new SimpleDateFormat("yyyyMM").format(new Date());
            MnfCargoList putList=new MnfCargoList(dateInt+i,"bl:"+i,"count"+i,"remark:"+i,"qty:"+i,"Plt:"+i,"CBM:"+i,"DES:"+i);

            DatabaseReference databaseReference=database.getReference("MnF/"+i);
            databaseReference.setValue(putList);
        }
        getData();
    }

    private void getData() {
        ValueEventListener listener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot data:snapshot.getChildren()){
                     MnfCargoList mList=data.getValue(MnfCargoList.class);
                     list.add(mList);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        databaseReference.addListenerForSingleValueEvent(listener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for(int result:grantResults){
            if(result== PackageManager.PERMISSION_DENIED){
                Toast.makeText(this,"Permission Denied",Toast.LENGTH_SHORT).show();
            }
        }
        cameraPreview();
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        cameraPreview();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }
}