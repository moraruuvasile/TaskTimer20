package com.example.tasktimer;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.security.InvalidParameterException;

public class MainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
	public static final int LOADER_ID = 0;
	private static final String TAG = "MainActivityFragment";
	CursorRecyclerViewAdapter mAdapter;

	public MainActivityFragment() {
		Log.d(TAG, "MainActivityFragment: START");
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		Log.d(TAG, "onActivityCreated: START");
		super.onActivityCreated(savedInstanceState);
		getLoaderManager().initLoader(LOADER_ID, null, this); //TODO de pus la onCeateView

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		Log.d(TAG, "onCreateView: VVV");
		View view = inflater.inflate(R.layout.a_fragment_main, container, false);
		RecyclerView recyclerView = view.findViewById(R.id.frg_first_task_list);
		recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

		mAdapter = new CursorRecyclerViewAdapter(null, (CursorRecyclerViewAdapter.OnTaskClickListener) getActivity());
		recyclerView.setAdapter(mAdapter);
		return view;
	}

	public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

//		view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				NavHostFragment.findNavController(FirstFragment.this)
//						.navigate(R.id.action_FirstFragment_to_SecondFragment);
//			}
//		});
	}

	@NonNull
	@Override
	public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
		Log.d(TAG, "onCreateLoader: STARTS with id " + id );
		String[] projection = {TasksContract.Columns._ID, TasksContract.Columns.TASKS_NAME,
				TasksContract.Columns.TASKS_DESCRIPTION, TasksContract.Columns.TASKS_SORTORDER};
		String sortOrder = TasksContract.Columns.TASKS_SORTORDER + "," + TasksContract.Columns.TASKS_NAME + " COLLATE NOCASE";

		switch(id) {
			case LOADER_ID:
				return new CursorLoader(getActivity(),
						TasksContract.CONTENT_URI,
						projection,
						null,
						null,
						sortOrder);
			default:
				throw new InvalidParameterException(TAG + ".onCreateLoader called with invalid loader id" + id);
		}
	}

	@Override
	public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
		Log.d(TAG, "Entering onLoadFinished");
		mAdapter.swapCursor(data);
		int count = mAdapter.getItemCount();
		Log.d(TAG, "onLoadFinished: count is " + count);
	}

	@Override
	public void onLoaderReset(@NonNull Loader<Cursor> loader) {
		Log.d(TAG, "onLoaderReset: starts");
		mAdapter.swapCursor(null);
	}
}
