package kz.aibol.app.vkfriends;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class FriendsActivity extends AppCompatActivity {

    private CoordinatorLayout mCoordinatorLayout;
    private ListView friendsList;

    private static ArrayList<UserInfo> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        initToolBar();
        initView();


        Snackbar.make(mCoordinatorLayout, "Authorized successfully", Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initView() {
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        friendsList = (ListView) findViewById(R.id.friendsList);

        data = new ArrayList<>();
        data.add(new UserInfo(1, "Aibol Kussain", null));
        data.add(new UserInfo(2, "Anuar Mukashev", null));
        data.add(new UserInfo(3, "Arnur Rakhmadiyev", null));
        data.add(new UserInfo(4, "Alibek Alibekovich", null));

        FriendsAdapter adapter = new FriendsAdapter(getApplicationContext());
        friendsList.setAdapter(adapter);
    }

    private class FriendsAdapter extends BaseAdapter {

        private Context mContext;

        public FriendsAdapter(Context context) {
            mContext = context;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return data.get(position).getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder;

            if(convertView == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.friend_item, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }
            else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.txtFullName.setText(data.get(position).getName());


            return convertView;
        }

        class ViewHolder {
            public ImageView imgAvatar;
            public ViewHolder(View view) {
                imgAvatar = (ImageView) view.findViewById(R.id.imgAvatar);
                txtFullName = (TextView) view.findViewById(R.id.txtFullName);
            }

            TextView txtFullName;
        }
    }

}
