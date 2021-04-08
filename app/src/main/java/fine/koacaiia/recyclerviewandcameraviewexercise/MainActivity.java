package fine.koacaiia.recyclerviewandcameraviewexercise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import fine.koacaiia.recyclerviewandcameraviewexercise.R;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    MnfCargoListAdapter adapter;
    ArrayList<MnfCargoList> list;
    FirebaseDatabase database;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}