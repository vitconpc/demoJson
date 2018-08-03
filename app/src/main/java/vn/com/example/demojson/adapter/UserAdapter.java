package vn.com.example.demojson.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import vn.com.example.demojson.R;
import vn.com.example.demojson.model.User;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private Context mContext;
    private List<User> mUsers;

    public UserAdapter(Context context, List<User> users) {
        this.mContext = context;
        this.mUsers = users;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.custom_item_user, viewGroup, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder userViewHolder, int position) {
        User user = mUsers.get(position);
        userViewHolder.mTextName.setText(user.getmName());
        userViewHolder.mTextFullname.setText(user.getmFullName());
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextName;
        private TextView mTextFullname;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextName = itemView.findViewById(R.id.text_name);
            mTextFullname = itemView.findViewById(R.id.text_full_name);
        }
    }
}
