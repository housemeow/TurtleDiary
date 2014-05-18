package com.td.turtlediary;

import java.util.Date;

import com.td.models.Pet;
import com.td.models.TurtleDiaryDB;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class PetActivity extends Activity {
	TurtleDiaryDB turtleDiary = new TurtleDiaryDB();
	Pet nowPet;
	PetActivityState nowState;
	private MenuItem menuItem;

	enum PetActivityState {
		FirstAdd, Add, View, Edit
	};

	Button addButton;
	Button editButton;
	Button restoreButton;
	Button nextButton;
	EditText petNameEditText;
	Spinner petTypeSpinner;
	RadioGroup genderRadioGroup;
	Spinner environmentSpinner;
	EditText birthdayEditText;

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

		addButton = (Button) findViewById(R.id.petActivityAddButton);
		editButton = (Button) findViewById(R.id.petActivityEditButton);
		restoreButton = (Button) findViewById(R.id.petActivityRestoreButton);
		nextButton = (Button) findViewById(R.id.petActivityNextButton);

		addButton.setOnClickListener(getAddButtonOnClickListener());
		editButton.setOnClickListener(getEditButtonOnClickListener());
		restoreButton.setOnClickListener(getRestoreButtonOnClickListener());
		nextButton.setOnClickListener(getNextButtonOnClickListener());
	}

	private void changeState(PetActivityState state) {
		nowState = state;
		changeButtonState(state);
		changeInputWidgetState(state);
	}

	private void changeInputWidgetState(PetActivityState state) {
		petNameEditText = (EditText) findViewById(R.id.petActivityPetNameEditText);
		petTypeSpinner = (Spinner) findViewById(R.id.petActivityPetTypeSpinner);
		genderRadioGroup = (RadioGroup) findViewById(R.id.petActivityGenderRadioGroup);
		environmentSpinner = (Spinner) findViewById(R.id.petActivityEnvironmentSpinner);
		birthdayEditText = (EditText) findViewById(R.id.petActivityBirthdayEditText);
		switch (state) {
		case FirstAdd: {
			// name
			petNameEditText.setText("");
			petNameEditText.setEnabled(true);
			// type
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_dropdown_item_1line,
					turtleDiary.GetTypes());
			petTypeSpinner.setAdapter(adapter);
			petTypeSpinner.setEnabled(true);
			// gender
			genderRadioGroup.setEnabled(true);
			// environment
			adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_dropdown_item_1line,
					turtleDiary.GetEnvironments());
			environmentSpinner.setAdapter(adapter);
			// birthday
			birthdayEditText.setEnabled(true);
			break;
		}
		case Add:
			break;
		case Edit:
			petNameEditText.setEnabled(true);
			petTypeSpinner.setEnabled(true);
			genderRadioGroup.setEnabled(true);
			environmentSpinner.setEnabled(true);
			birthdayEditText.setEnabled(true);
			break;
		case View: {
			// name
			petNameEditText.setText(nowPet.getName());
			petNameEditText.setEnabled(false);
			// type
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_dropdown_item_1line,
					turtleDiary.GetTypes());
			petTypeSpinner.setAdapter(adapter);
			petTypeSpinner.setEnabled(false);
			// gender
			genderRadioGroup.setEnabled(false);
			// environment
			adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_dropdown_item_1line,
					turtleDiary.GetEnvironments());
			environmentSpinner.setAdapter(adapter);
			environmentSpinner.setEnabled(true);
			// birthday
			if (nowPet.getBirthday() != null) {
				birthdayEditText.setText(nowPet.getBirthday().toString());
			}
			birthdayEditText.setEnabled(false);
			break;
		}
		}
	}

	private void changeButtonState(PetActivityState state) {
		addButton = (Button) findViewById(R.id.petActivityAddButton);
		editButton = (Button) findViewById(R.id.petActivityEditButton);
		restoreButton = (Button) findViewById(R.id.petActivityRestoreButton);
		nextButton = (Button) findViewById(R.id.petActivityNextButton);

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
			restoreButton.setVisibility(View.INVISIBLE);
			break;
		}
		if (menuItem != null) {
			menuItem.setVisible(nowState == PetActivityState.View);
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
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				finish();
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
				nowPet.setName(petNameEditText.getText().toString());
				Date date = new Date(birthdayEditText.getText().toString());
				nowPet.setBirthday(date);
				
				int selectedId = genderRadioGroup.getCheckedRadioButtonId();
	                
				RadioButton selectedRadioButton = (RadioButton) findViewById(selectedId);
	                
	            nowPet.setGender(selectedRadioButton.getText().toString());
				
				changeState(PetActivityState.View);
				turtleDiary.ChangePet(nowPet);
			}
		};
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pet, menu);

		menuItem = menu.findItem(R.id.petActivityEditActionButton);
		menuItem.setVisible(nowState == PetActivityState.View);
		menuItem.setOnMenuItemClickListener(getEditMenuItemClickListener());
		return true;
	}

	private OnMenuItemClickListener getEditMenuItemClickListener() {
		return new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				nowPet.setName(petNameEditText.getText().toString());
				changeState(PetActivityState.Edit);
				return false;
			}
		};
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
