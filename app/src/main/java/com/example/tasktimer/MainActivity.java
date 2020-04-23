package com.example.tasktimer;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity implements CursorRecyclerViewAdapter.OnTaskClickListener{
	private static final String TAG = "MainActivity";

	private boolean mTwoPane = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_activity_main);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		if(findViewById(R.id.frame_layout) != null) {
			Log.i(TAG, "onCreate: TWO PANE MODE" );
			mTwoPane = true;
		}



		FloatingActionButton fab = findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
						.setAction("Action", null).show();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();


		//noinspection SimplifiableIfStatement
		switch (id) {
			case R.id.menumain_addTask:
				taskEditRequest(null);
				break;
			case R.id.menumain_showDuration:
				break;
			case R.id.menumain_settings:
				break;
			case R.id.menumain_showAbout:
				break;
			case R.id.menumain_generate:
				break;
		}

		return super.onOptionsItemSelected(item);
	}

	private void taskEditRequest(Task task) {
		Log.d(TAG, "taskEditRequest: starts");
		if(mTwoPane) {
			Log.d(TAG, "taskEditRequest: in two-pane mode (tablet)");
			AddEditActivityFragment fragment = new AddEditActivityFragment();

			Bundle arguments = new Bundle();
			arguments.putSerializable(Task.class.getSimpleName(), task);
			fragment.setArguments(arguments);

			FragmentManager fragmentManager = getSupportFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
			fragmentTransaction.replace(R.id.frame_layout, fragment);
			fragmentTransaction.commit();
		} else {
			Log.d(TAG, "taskEditRequest: in single-pane mode (phone)");
			// in single-pane mode, start the detail activity for the selected item Id.
			Intent detailIntent = new Intent(this, AddEditActivity.class);
			if (task != null) { // editing a task
				detailIntent.putExtra(Task.class.getSimpleName(), task);
				startActivity(detailIntent);
			} else { // adding a new task
				startActivity(detailIntent);
			}
		}
	}

	@Override
	public void onEditClick(Task task) {
		taskEditRequest(task);
	}

	@Override
	public void onDeleteClick(Task task) {
		getContentResolver().delete(TasksContract.buildTaskUri(task.getId()), null, null);

	}
}
