package fine.koacaiia.recyclerviewandcameraviewexercise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;


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
        for(int i=0;i<10;i++){
            MnfCargoList putList=new MnfCargoList("date:"+i,"bl:"+i,"count"+i,"remark:"+i,"qty:"+i,"Plt:"+i,"CBM:"+i,"DES:"+i);

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