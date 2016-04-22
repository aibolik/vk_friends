package kz.aibol.app.vkfriends;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class FriendsActivity extends AppCompatActivity {

    private CoordinatorLayout mCoordinatorLayout;
    private ListView friendsList;
    private Bitmap tempBitmap;

    private static ArrayList<UserInfo> data;
    private static ArrayList<Bitmap> imgs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        initToolBar();
        initView();


        Snackbar.make(mCoordinatorLayout, "Authorized successfully", Snackbar.LENGTH_LONG).setAction("Action", null).show();

        new FriendsRequest().execute();
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initView() {
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        friendsList = (ListView) findViewById(R.id.friendsList);

        data = new ArrayList<>();
        imgs = new ArrayList<>();
//        data.add(new UserInfo(1, "Aibol Kussain", null));
//        data.add(new UserInfo(2, "Anuar Mukashev", null));
//        data.add(new UserInfo(3, "Arnur Rakhmadiyev", null));
//        data.add(new UserInfo(4, "Alibek Alibekovich", null));
    }

    private class FriendsRequest extends AsyncTask<Void, Void, String> {

        private final String LOG_TAG = FriendsRequest.class.getSimpleName();

        String friendsJsonStr;

        @Override
        protected String doInBackground(Void... params) {

            Log.d(LOG_TAG, "Starting sync");

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;


            try {

                final String FRIENDS_BASE_URL = "https://api.vk.com/method/friends.get?";
                final String USER_ID_PARAM = "user_id";
                final String ORDER_PARAM = "order";
                final String FIELDS_PARAM = "fields";
                final String NAMECASE_PARAM = "name_case";

                Uri builtUri = Uri.parse(FRIENDS_BASE_URL)
                        .buildUpon()
                        .appendQueryParameter(USER_ID_PARAM, "54621180")
                        .appendQueryParameter(ORDER_PARAM, "hints")
                        .appendQueryParameter(FIELDS_PARAM, "photo_100")
                        .appendQueryParameter(NAMECASE_PARAM, "ins")
                        .build();

                URL url = new URL(builtUri.toString());

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }

                friendsJsonStr = buffer.toString();

            } catch (MalformedURLException e) {
                Log.e(LOG_TAG, "Error ", e);
                e.printStackTrace();
            } catch (ProtocolException e) {
                Log.e(LOG_TAG, "Error ", e);
                e.printStackTrace();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            Log.d("RESULT", friendsJsonStr);

            return friendsJsonStr;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                parseJson(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            FriendsAdapter adapter = new FriendsAdapter(getApplicationContext());
            friendsList.setAdapter(adapter);
        }
    }

    private void parseJson(String jsonStr) throws JSONException {
        JSONObject json = new JSONObject(jsonStr);
        JSONArray allFriends = json.getJSONArray("response");
        for (int i = 0; i < allFriends.length(); i++) {
            JSONObject friend;
            friend = allFriends.getJSONObject(i);
            int id = friend.getInt("user_id");
            String name = friend.getString("first_name");
            String lastname = friend.getString("last_name");
            String photoUrl = friend.getString("photo_100");

            String fullName = name + " " + lastname;

//            new DownloadImageTask().execute(photoUrl);
//            UserInfo newFriend = new UserInfo(id, fullName, tempBitmap);
            UserInfo newFriend = new UserInfo(id, fullName, photoUrl);

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

            viewHolder.txtFullName.setText(data.get(position).getName());
//            viewHolder.imgAvatar.setImageBitmap(data.get(position).getImage());
//            viewHolder.imgAvatar.setImageBitmap(imgs.get(position));
            new DownloadImageTask(viewHolder.imgAvatar).execute(data.get(position).getImageUrl());


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

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
//
        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String imgUrl = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(imgUrl).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
                //mIcon11.setHeight(sizes.height);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

}
