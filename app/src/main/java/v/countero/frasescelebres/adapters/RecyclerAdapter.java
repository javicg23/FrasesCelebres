package v.countero.frasescelebres.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import v.countero.frasescelebres.R;
import v.countero.frasescelebres.pojos.Quotation;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<Quotation> listQuotation;
    private OnItemClickListener itemClickListener;
    private OnItemLongClickListner itemLongListener;

    public RecyclerAdapter(List<Quotation> listQuotation, OnItemClickListener itemClickListener, OnItemLongClickListner itemLongListener) {
        this.listQuotation = listQuotation;
        this.itemClickListener = itemClickListener;
        this.itemLongListener = itemLongListener;
    }

    @Override
    public int getItemCount() {
        return listQuotation.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quotation_list_row,parent,false);
        RecyclerAdapter.ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvListQuotation.setText(listQuotation.get(position).getQuoteText());
        holder.tvListAuthor.setText(listQuotation.get(position).getQuoteAuthor());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvListQuotation, tvListAuthor;

        public ViewHolder(View view) {
            super(view);
            this.tvListAuthor = view.findViewById(R.id.tvListAuthor);
            this.tvListQuotation = view.findViewById(R.id.tvListQuotation);

            view.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    itemClickListener.onItemClickListener(getAdapterPosition());
                }
            });

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    itemLongListener.onItemLongClickListener(getAdapterPosition());
                    return true;
                }
            });
        }

    }

    public interface OnItemClickListener {
        void onItemClickListener(int position);
    }

    public Quotation getQuotationByPosition(int position) {
        return listQuotation.get(position);
    }

    public interface OnItemLongClickListner {
        void onItemLongClickListener(int position);
    }

    public void removeElementByPosition(int position) {
        listQuotation.remove(position);
        notifyItemRemoved(position);
        //notifyDataSetChanged();
    }
}
