package com.hemanthkandula.app.myhome;


import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {

    private List<Movie> moviesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, genre,rank,name;
        CardView card;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            genre = (TextView) view.findViewById(R.id.genre);
            rank = (TextView) view.findViewById(R.id.rank);
            name = (TextView) view.findViewById(R.id.name);
            card = (CardView) view.findViewById(R.id.card_view);
        }
    }


    public MyRecyclerViewAdapter(List<Movie> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Movie movie = moviesList.get(position);
        holder.title.setText(movie.getTitle());
        holder.genre.setText(movie.getGenre());
        holder.rank.setText(movie.getRank());
        holder.name.setText(movie.getName());
        String s = movie.getRank() + "";
        if(s.equals("3")){
            holder.card.setCardBackgroundColor(Color.parseColor("#738290"));
        }
        else if(s.equals("2")){
            holder.card.setCardBackgroundColor(Color.parseColor("#B3EFB2"));
        }
        else if(s.equals("1")){
            holder.card.setCardBackgroundColor(Color.parseColor("#D4E79E"));
        }

        //   1   #D4E79E
        //   1   #B3EFB2
        //   1   #738290

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public void setFilter(List<Movie> movieList) {
        moviesList = new ArrayList<>();
        moviesList.addAll(movieList);
        notifyDataSetChanged();
    }
    public Movie getpos(int position){
        return moviesList.get(position);
    }

}