package fine.koacaiia.recyclerviewandcameraviewexercise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import fine.koacaiia.recyclerviewandcameraviewexercise.R;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerview;
    ArrayList<MnfCargoList> list;
    MnfCargoListAdapter adapter;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list=new ArrayList<>();
        recyclerview=findViewById(R.id.recyclerView);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        recyclerview.setLayoutManager(manager);
        database=FirebaseDatabase.getInstance();
        getData();
        adapter=new MnfCargoListAdapter(list);
        recyclerview.setAdapter(adapter);
        adapter.notifyDataSetChanged();

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
}