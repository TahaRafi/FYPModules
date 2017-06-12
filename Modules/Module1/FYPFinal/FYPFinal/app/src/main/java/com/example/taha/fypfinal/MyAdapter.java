package com.example.taha.fypfinal;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by taha on 4/13/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
   ArrayList<FeedItem> feedItems;
    Context context;
    TextView name;


    // public  CardView cardView;
    MyAdapter( Context context,ArrayList<FeedItem> feedItems)
    {
         this.feedItems=feedItems;
        this.context=context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.custom_row_news_item,parent,false);
        MyViewHolder holder=new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
     final FeedItem current=feedItems.get(position);
        holder.Title.setText(current.getTitles());
        holder.Description.setText(current.getdescription());
        holder.Date.setText(current.getpubDate());
        Log.d("DATE",current.getpubDate());
        Picasso.with(context).load(current.getthumbnailUrl()).into(holder.Thumbnail);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
                 public void onClick(View v) {
                Intent intent=new Intent(context,NewsDetail.class);
                intent.putExtra("Link",current.getlink());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return feedItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Title,Description,Date,link;
        ImageView Thumbnail;
        public CardView cardView;
        public MyViewHolder(View itemView) {
            super(itemView);
            Title=(TextView)itemView.findViewById(R.id.title_text);
            Description= (TextView) itemView.findViewById(R.id.desciption_text);
            Date= (TextView) itemView.findViewById(R.id.date_text);
            Thumbnail= (ImageView) itemView.findViewById(R.id.thumb_img);
            cardView = (CardView)  itemView.findViewById(R.id.cv);
        }
    }
}















































