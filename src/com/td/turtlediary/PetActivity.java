package com.td.turtlediary;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.td.models.Environment;
import com.td.models.Pet;
import com.td.models.TurtleDiaryDatabaseHelper;
import com.td.models.Type;

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
	TurtleDiaryDatabaseHelper turtleDiaryHelper = new TurtleDiaryDatabaseHelper(
			this);
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

		nowPet = (Pet) getIntent().getSerializableExtra("pet");
		if (nowPet != null) {
			nowPet = turtleDiaryHelper.getPet(nowPet.getPid());
		}
		if (turtleDiaryHelper.getEnvironmentsCount() == 0) {
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
			petTypeSpinner.setEnabled(true);
			// gender
			for (int i = 0; i < genderRadioGroup.getChildCount(); i++) {
				genderRadioGroup.getChildAt(i).setEnabled(true);
			}
			// type
			List<Type> types = turtleDiaryHelper.getTypes();
			ArrayAdapter<Type> typeAdapter = new ArrayAdapter<Type>(this,
					android.R.layout.simple_dropdown_item_1line, types);
			petTypeSpinner.setAdapter(typeAdapter);

			// environment
			Environment environment = (Environment) getIntent()
					.getSerializableExtra("environment");
			ArrayList<Environment> environments = new ArrayList<Environment>();
			environments.add(environment);

			ArrayAdapter<Environment> environmentAdapter = new ArrayAdapter<Environment>(
					this, android.R.layout.simple_dropdown_item_1line,
					environments);
			environmentSpinner.setAdapter(environmentAdapter);
			// birthday
			birthdayEditText.setEnabled(true);
			break;
		}
		case Add: {
			// type
			List<Type> types = turtleDiaryHelper.getTypes();
			ArrayAdapter<Type> typeAdapter = new ArrayAdapter<Type>(this,
					android.R.layout.simple_dropdown_item_1line, types);
			petTypeSpinner.setAdapter(typeAdapter);
			// environment
			ArrayAdapter<Environment> environmentAdapter = new ArrayAdapter<Environment>(
					this, android.R.layout.simple_dropdown_item_1line,
					turtleDiaryHelper.getEnvironments());
			environmentSpinner.setAdapter(environmentAdapter);

			break;
		}
		case Edit:
			petNameEditText.setEnabled(true);
			petTypeSpinner.setEnabled(true);
			for (int i = 0; i < genderRadioGroup.getChildCount(); i++) {
				genderRadioGroup.getChildAt(i).setEnabled(true);
			}
			environmentSpinner.setEnabled(true);
			birthdayEditText.setEnabled(true);
			break;
		case View: {
			// name
			petNameEditText.setText(nowPet.getName());
			petNameEditText.setEnabled(false);

			// type
			List<Type> types = turtleDiaryHelper.getTypes();
			ArrayAdapter<Type> typeAdapter = new ArrayAdapter<Type>(this,
					android.R.layout.simple_dropdown_item_1line, types);
			petTypeSpinner.setAdapter(typeAdapter);
			petTypeSpinner.setEnabled(false);
			Type type = turtleDiaryHelper.getType(nowPet.getTid());
			int index = 0;
			for (Type t : types) {
				if (t.equals(type)) {
					break;
				}
				index++;
			}

			petTypeSpinner.setSelection(index);
			// gender
			for (int i = 0; i < genderRadioGroup.getChildCount(); i++) {
				RadioButton radioButton = (RadioButton) genderRadioGroup
						.getChildAt(i);
				genderRadioGroup.getChildAt(i).setEnabled(false);
				String gender = radioButton.getText().toString();
				radioButton.setChecked(gender.equals(nowPet.getGender()));
			}

			// environment
			List<Environment> environments = turtleDiaryHelper
					.getEnvironments();
			ArrayAdapter<Environment> environmentAdapter = new ArrayAdapter<Environment>(
					this, android.R.layout.simple_dropdown_item_1line,
					environments);
			environmentSpinner.setAdapter(environmentAdapter);
			environmentSpinner.setEnabled(false);
			Environment environment = turtleDiaryHelper.getEnvironment(nowPet
					.getEid());

			index = 0;
			for (Environment e : environments) {
				if (e.equals(environment)) {
					break;
				}
				index++;
			}
			environmentSpinner.setSelection(index);
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
				pet.setBirthday(new Date());
				Environment environment = (Environment) environmentSpinner
						.getSelectedItem();
				pet.setEid(environment.getEid());

				int selectedId = genderRadioGroup.getCheckedRadioButtonId();
				RadioButton selectedRadioButton = (RadioButton) findViewById(selectedId);
				pet.setGender(selectedRadioButton.getText().toString());
				pet.setName(petNameEditText.getText().toString());

				Type type = (Type) petTypeSpinner.getSelectedItem();
				pet.setTid(type.getTid());

				turtleDiaryHelper.addPet(pet);
				finish();
			}
		};
	}

	private OnClickListener getNextButtonOnClickListener() {
		return new OnClickListener() {

			@Override
			public void onClick(View v) {
				Pet pet = new Pet();
				pet.setName(petNameEditText.getText().toString());
				pet.setBirthday(new Date());

				int selectedId = genderRadioGroup.getCheckedRadioButtonId();
				RadioButton selectedRadioButton = (RadioButton) findViewById(selectedId);
				pet.setGender(selectedRadioButton.getText().toString());

				Type type = (Type) petTypeSpinner.getSelectedItem();

				Environment environment = (Environment) getIntent()
						.getSerializableExtra("environment");
				turtleDiaryHelper.addEnvironment(environment);
				pet.setEid(environment.getEid());
				pet.setTid(type.getTid());
				turtleDiaryHelper.addPet(pet);
				Intent intent = new Intent();

				intent.setClass(PetActivity.this, HomePageActivity.class);
				EnvironmentActivity.finishByOtherActivity();
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
				nowPet.setBirthday(new Date());
				Environment environment = (Environment) environmentSpinner
						.getSelectedItem();
				nowPet.setEid(environment.getEid());

				int selectedId = genderRadioGroup.getCheckedRadioButtonId();
				RadioButton selectedRadioButton = (RadioButton) findViewById(selectedId);
				nowPet.setGender(selectedRadioButton.getText().toString());
				nowPet.setName(petNameEditText.getText().toString());

				Type type = (Type) petTypeSpinner.getSelectedItem();
				nowPet.setTid(type.getTid());

				turtleDiaryHelper.updatePet(nowPet);

				changeState(PetActivityState.View);
				turtleDiaryHelper.updatePet(nowPet);
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
}
