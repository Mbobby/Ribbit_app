package com.oris.emmanuelmong.ribbit;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by emmanuelmong on 1/26/15.
 */
public class FriendsFragment extends ListFragment{
    protected List<ParseUser> mFriends;
    protected ParseRelation<ParseUser> mFriendsRelation;
    protected ParseUser mCurrentUser;
    private static String TAG = FriendsFragment.class.getSimpleName();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_friends, container, false);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setProgressBarIndeterminate(true);
        mCurrentUser = ParseUser.getCurrentUser();
        mFriendsRelation = mCurrentUser.getRelation(ParseConstants.KEY_FRIENDS_RELATION);
        ParseQuery<ParseUser> query = mFriendsRelation.getQuery();
        query.addAscendingOrder(ParseConstants.KEY_USERNAME);
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> friends, ParseException e) {
                mFriends = friends;

                //Remove progress bar
                getActivity().setProgressBarIndeterminate(false);

                if(e == null)
                {
                    String [] userNames = new String[mFriends.size()];
                    int i = 0;
                    for(ParseUser friend: mFriends)
                    {
                        userNames[i] = friend.getUsername();
                        i++;
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                            getListView().getContext(),
                            android.R.layout.simple_list_item_1,
                            userNames);
                    setListAdapter(adapter);
                }
                //Not successful
                else
                {
                    Log.e(TAG, e.getMessage());
                    AlertDialog.Builder builder = new AlertDialog.Builder(getListView().getContext());
                    builder.setMessage(e.getMessage())
                            .setTitle(R.string.error_title)
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
    }
}
