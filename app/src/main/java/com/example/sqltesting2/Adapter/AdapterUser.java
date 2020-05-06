package com.example.sqltesting2.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.sqltesting2.Pojo.User;
import com.example.sqltesting2.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AdapterUser extends RecyclerView.Adapter<AdapterUser.UserViewHolder>  {
    private List<User> userList = new ArrayList<>();
    private OnUserClickListener onUserClickListener;
    private final String TAG = this.getClass().getSimpleName();


    public AdapterUser(OnUserClickListener onUserClickListener) {
        this.onUserClickListener = onUserClickListener;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.bind(user);
    }

    public void setItems(Collection<User> users) {
        userList.addAll(users);
        notifyDataSetChanged();
    }

    public void clearItems() {
        userList.clear();
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return userList.size();
    }


    class UserViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private TextView nickTextView;

        public UserViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.txt_user_name);
            nickTextView = itemView.findViewById(R.id.txt_user_nick_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    User user = userList.get(getLayoutPosition());
                    onUserClickListener.onUserClick(user);
                }
            });
        }

        public void bind(User user) {
            Log.d(TAG, "bind: Name " + user.getName());
            String name = user.getName();
            Log.d(TAG, "bind: name user " + name);
            nameTextView.setText(name);
            Log.d(TAG, "bind: NICKNAME " + user.getNickname());
            String nick = user.getNickname();
            Log.d(TAG, "bind: nickname user " + nick);
            nickTextView.setText(nick);
        }
    }

    public interface OnUserClickListener {
        void onUserClick(User user);
    }
}
