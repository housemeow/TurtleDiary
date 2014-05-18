package com.td.turtlediary;

import com.td.models.Pet;
import com.td.models.TurtleDiaryDB;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class PetActivity extends ActionBarActivity {
	TurtleDiaryDB turtleDiary = new TurtleDiaryDB();
	Pet nowPet;

	enum PetActivityState {
		FirstAdd, Add, View, Edit
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pet);

		nowPet = (Pet) getIntent().getSerializableExtra("Pet");
		if (turtleDiary.GetPetCount() == 0) {
			changeState(PetActivityState.FirstAdd);
		} else if (nowPet == null) {
			changeState(PetActivityState.Add);
		} else {
			changeState(PetActivityState.View);
		}

		Button addButton = (Button) findViewById(R.id.petActivityAddButton);
		Button editButton = (Button) findViewById(R.id.petActivityEditButton);
		Button restoreButton = (Button) findViewById(R.id.petActivityRestoreButton);
		Button nextButton = (Button) findViewById(R.id.petActivityNextButton);

		addButton.setOnClickListener(getAddButtonOnClickListener());
		editButton.setOnClickListener(getEditButtonOnClickListener());
		restoreButton.setOnClickListener(getRestoreButtonOnClickListener());
		nextButton.setOnClickListener(getNextButtonOnClickListener());
	}

	private void changeState(PetActivityState state) {

		changeButtonState(state);
		changeInputWidgetState(state);

	}

	private void changeInputWidgetState(PetActivityState state) {
		EditText petNameEditText = (EditText) findViewById(R.id.petActivityPetNameEditText);
		Spinner petTypeSpinner = (Spinner) findViewById(R.id.petActivityPetTypeSpinner);
		RadioGroup genderRadioGroup = (RadioGroup) findViewById(R.id.petActivityGenderRadioGroup);
		Spinner environmentSpinner = (Spinner) findViewById(R.id.petActivityEnvironmentSpinner);
		EditText birthdayEditText = (EditText) findViewById(R.id.petActivityBirthdayEditText);
		switch (state) {
		case FirstAdd:
			//name
			petNameEditText.setText("");
			petNameEditText.setEnabled(true);
			//type
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_dropdown_item_1line,
					turtleDiary.GetTypes());
			petTypeSpinner.setAdapter(adapter);
			petTypeSpinner.setEnabled(true);
			//gender
			genderRadioGroup.setEnabled(true);
			//environment
			adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_dropdown_item_1line,
					turtleDiary.GetEnvironments());		
			environmentSpinner.setAdapter(adapter);
			//birthday
			birthdayEditText.setEnabled(true);
			
			break;
		case Add:
			break;
		case Edit:

			break;
		case View:

			break;
		}
	}

	private void changeButtonState(PetActivityState state) {
		Button addButton = (Button) findViewById(R.id.petActivityAddButton);
		Button editButton = (Button) findViewById(R.id.petActivityEditButton);
		Button restoreButton = (Button) findViewById(R.id.petActivityRestoreButton);
		Button nextButton = (Button) findViewById(R.id.petActivityNextButton);
		
		switch (state) {
		case FirstAdd:
			nextButton.setVisibility(View.VISIBLE);
			addButton.setVisibility(View.INVISIBLE);
			editButton.setVisibility(View.INVISIBLE);
			restoreButton.setVisibility(View.INVISIBLE);
			break;
		case Add:
			nextButton.setVisibility(View.INVISIBLE);
			addButton.setVisibility(View.VISIBLE);
			editButton.setVisibility(View.INVISIBLE);
			restoreButton.setVisibility(View.INVISIBLE);
			break;
		case Edit:
			nextButton.setVisibility(View.INVISIBLE);
			addButton.setVisibility(View.INVISIBLE);
			editButton.setVisibility(View.VISIBLE);
			restoreButton.setVisibility(View.VISIBLE);
			break;
		case View:
			nextButton.setVisibility(View.INVISIBLE);
			addButton.setVisibility(View.INVISIBLE);
			editButton.setVisibility(View.INVISIBLE);
			restoreButton.setVisibility(View.VISIBLE);
			break;
		}
	}

	private OnClickListener getAddButtonOnClickListener() {
		return new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Pet pet = new Pet();
				turtleDiary.AddPet(pet);
				Intent intent = new Intent();
				intent.setClass(PetActivity.this, HomePageActivity.class);
				startActivity(intent);
			}
		};
	}

	private OnClickListener getNextButtonOnClickListener() {
		return new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Pet pet = new Pet();
				turtleDiary.AddPet(pet);
				Intent intent = new Intent();
				intent.setClass(PetActivity.this, HomePageActivity.class);
				startActivity(intent);
			}
		};
	}

	private OnClickListener getRestoreButtonOnClickListener() {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				changeState(PetActivityState.View);
			}
		};
	}

	private OnClickListener getEditButtonOnClickListener() {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				Pet pet = new Pet();
				changeState(PetActivityState.View);
				turtleDiary.ChangePet(pet);
				nowPet = pet;
			}
		};
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pet, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
