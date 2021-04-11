package fine.koacaiia.recyclerviewandcameraviewexercise;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import android.app.AlertDialog;

import fine.koacaiia.recyclerviewandcameraviewexercise.R;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback {
    RecyclerView recyclerview;
    ArrayList<MnfCargoList> list;
    MnfCargoListAdapter adapter;
    FirebaseDatabase database;
    Camera camera;
    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;
    String[] permission_list={Manifest.permission.CAMERA};
    Button btnDate;
    String date_start,date_today,date_end;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermissions(permission_list,0);

        list=new ArrayList<>();
        recyclerview=findViewById(R.id.recyclerView);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        recyclerview.setLayoutManager(manager);
        database=FirebaseDatabase.getInstance();
        getData();
        adapter=new MnfCargoListAdapter(list);
        recyclerview.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        surfaceView=findViewById(R.id.surfaceView);
        surfaceHolder=surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        preViewProgress();

        context=this.getApplicationContext();

        date_today=new SimpleDateFormat("yyyy녀MM일dd일").format(new Date());
        btnDate=findViewById(R.id.button);
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view=getLayoutInflater().inflate(R.layout.datepicker_spinner,null);
                TextView btnStart=view.findViewById(R.id.txtSearchDate_start);
                btnStart.setText(date_today);
                TextView btnEnd=view.findViewById(R.id.txtSearchDate_end);
                btnEnd.setText(date_today);
                DatePicker datePicker=view.findViewById(R.id.datePicker_start);
                datePicker.init(2021, 4, 1, new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        date_today=year+"년"+monthOfYear+"월"+dayOfMonth+"일";
                    }
                });
                btnStart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btnStart.setText(date_today);
                        date_start=date_today;
                    }
                });
                btnEnd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btnEnd.setText(date_today);
                        date_end=date_today;
                    }
                });
               AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
               builder.setView(view)
                       .setPositiveButton("Sort", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               sortGetData();
                           }
                       })
                       .show();
//                dateDialog();
            }
        });
        btnDate.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                putData();
                return true;
            }
        });

    }

    private void putData() {
        for(int i=1;i<30;i++){
            String date="2021년4월"+i+"일";
            MnfCargoList mList=new MnfCargoList("2021년4월","",date,"Count:"+i,"Des:"+i,"Plt:"+i,"Cbm:"+i,"Qty" +
                    ":"+i,
                    Integer.parseInt("20214"+i)
                    );
            DatabaseReference databaseReference=database.getReference("MnF/"+date);
            databaseReference.setValue(mList);

        }
    }

    private void sortGetData() {
        list.clear();
        Log.i("koacaiia","StartDay+:"+date_start+"/EndDay+:"+date_end);
        DatabaseReference databaseReference=database.getReference("MnF");
        ValueEventListener listener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data:snapshot.getChildren()){
                    MnfCargoList mList=data.getValue(MnfCargoList.class);
                    Log.i("koacaiia","getDate Value+++:"+mList.getDate());
                    list.add(mList);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        Query sortData=databaseReference.orderByChild("date").startAt(date_start).endAt(date_end);
//        Query sortData=databaseReference.orderByChild("date").equalTo(date_start);
        sortData.addListenerForSingleValueEvent(listener);
    }

    private void preViewProgress() {
        camera=Camera.open();
        camera.setDisplayOrientation(90);
        try {
            camera.setPreviewDisplay(surfaceHolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        camera.startPreview();;
    }

    private void getData() {
        DatabaseReference databaseReference=database.getReference("MnF");
        ValueEventListener listener= new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data: snapshot.getChildren()){
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
    public void surfaceCreated(@NonNull SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for(int result:grantResults){
            if(result== PackageManager.PERMISSION_DENIED){
                Toast.makeText(this,"Permission Denied",Toast.LENGTH_SHORT).show();
            }
        }
        preViewProgress();
    }

    public void dateDialog(){
        View view=getLayoutInflater().inflate(R.layout.datepicker_spinner,null);
        TextView date_start=view.findViewById(R.id.txtSearchDate_start);
        date_start.setText(date_today);
        TextView date_end=view.findViewById(R.id.txtSearchDate_end);
        date_end.setText(date_today);
        DatePicker datePicker=view.findViewById(R.id.datePicker_start);
        datePicker.init(2021, 4, 1, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
               date_today=year+"-"+monthOfYear+"-"+dayOfMonth;
            }
        });
        date_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date_start.setText(date_today);
            }
        });
        date_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date_end.setText(date_today);
            }
        });
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setView(view)
                .show();
    }
}