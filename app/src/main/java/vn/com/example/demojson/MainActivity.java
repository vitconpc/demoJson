package vn.com.example.demojson;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import vn.com.example.demojson.adapter.UserAdapter;
import vn.com.example.demojson.model.User;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerViewUsers;
    private UserAdapter mUserAdapter;
    private List<User> mUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mUsers = new ArrayList<>();
        mUserAdapter = new UserAdapter(MainActivity.this, mUsers);
        mRecyclerViewUsers = findViewById(R.id.recycler_view_list_user);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerViewUsers.setLayoutManager(manager);
        mRecyclerViewUsers.setHasFixedSize(true);
        mRecyclerViewUsers.setAdapter(mUserAdapter);

        new ReadJSON().execute(Constants.LINK_API);
    }

    public class ReadJSON extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            return readContentJson(strings[0]);
        }

        private String readContentJson(String string) {
            String json = null;
            try {
                URL url = new URL(string);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod(Constants.METHOD_GET);
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuffer sb = new StringBuffer();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line).append('\n');
                }
                br.close();
                conn.disconnect();
                json = sb.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return json;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONArray array = new JSONArray(s);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject user = array.getJSONObject(i);
                    int id = user.getInt(Constants.ID);
                    String name = user.getString(Constants.NAME);
                    String fullName = user.getString(Constants.FULLNAME);
                    mUsers.add(new User(id, name, fullName));
                }
                mUserAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
