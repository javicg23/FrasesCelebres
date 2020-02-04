package v.countero.frasescelebres.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import v.countero.frasescelebres.R;
import v.countero.frasescelebres.pojos.Quotation;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<Quotation> listQuotation;

    public RecyclerAdapter(List<Quotation> list) {
        this.listQuotation = list;
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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvListQuotation, tvListAuthor;

        public ViewHolder(View view) {
            super(view);
            this.tvListAuthor = view.findViewById(R.id.tvListAuthor);
            this.tvListQuotation = view.findViewById(R.id.tvListQuotation);
        }
    }
}
