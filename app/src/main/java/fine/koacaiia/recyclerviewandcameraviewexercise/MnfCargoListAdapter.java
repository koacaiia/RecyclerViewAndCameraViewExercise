package fine.koacaiia.recyclerviewandcameraviewexercise;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MnfCargoListAdapter extends RecyclerView.Adapter<MnfCargoListAdapter.ListViewHolder>{
    ArrayList<MnfCargoList> list;

    public MnfCargoListAdapter(ArrayList<MnfCargoList> list) {
        this.list=list;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.mnf_cargo_list,parent,false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        holder.bl.setText(list.get(position).getBl());
        holder.remark.setText(list.get(position).getRemark());
        holder.date.setText(list.get(position).getDate());
        holder.count.setText(list.get(position).getCount());
        holder.des.setText(list.get(position).getDes());
        holder.plt.setText(list.get(position).getPlt());
        holder.cbm.setText(list.get(position).getCbm());
        holder.qty.setText(list.get(position).getQty());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        TextView bl;
        TextView remark;
        TextView date;
        TextView des;
        TextView count;
        TextView plt;
        TextView cbm;
        TextView qty;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            this.bl=itemView.findViewById(R.id.textView_bl);
            this.remark=itemView.findViewById(R.id.textView_remark);
            this.date=itemView.findViewById(R.id.textView_date);
            this.count=itemView.findViewById(R.id.textView_count);
            this.des=itemView.findViewById(R.id.textView_des);
            this.plt=itemView.findViewById(R.id.textView_plt);
            this.cbm=itemView.findViewById(R.id.textView_cbm);
            this.qty=itemView.findViewById(R.id.textView_qty);
        }
    }
}
