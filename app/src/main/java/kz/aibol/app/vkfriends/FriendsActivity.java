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

import com.squareup.picasso.Picasso;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

        VKRequest request = VKApi.friends().get(VKParameters.from(
                                                VKApiConst.FIELDS, "photo_100,online",
                                                VKApiConst.NAME_CASE, "ins",
                                                "order", "name"));

        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                try {
                    parseJson(response.json);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                FriendsAdapter adapter = new FriendsAdapter(getApplicationContext());
                friendsList.setAdapter(adapter);
            }
        });
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initView() {
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        friendsList = (ListView) findViewById(R.id.friendsList);

        data = new ArrayList<>();
    }

    private void parseJson(JSONObject json) throws JSONException {
        JSONArray allFriends = json.getJSONObject("response").getJSONArray("items");
        for (int i = 0; i < allFriends.length(); i++) {
            JSONObject friend;
            friend = allFriends.getJSONObject(i);
            int id = friend.getInt("id");
            String name = friend.getString("first_name");
            String lastname = friend.getString("last_name");
            String photoUrl = friend.getString("photo_100");
            boolean online = friend.getInt("online") == 1;
            String fullName = name + " " + lastname;
            UserInfo newFriend = new UserInfo(id, fullName, photoUrl, online);

            data.add(newFriend);
        }
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

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.friend_item, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            if(data.get(position).isOnline()) {
                convertView.setBackgroundColor(getResources().getColor(R.color.vk_light_color));
            }
            else {
                convertView.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            }

            viewHolder.txtFullName.setText(data.get(position).getName());
            Picasso.with(mContext).load(data.get(position).getImageUrl()).into(viewHolder.imgAvatar);

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
