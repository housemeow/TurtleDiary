package com.td.turtlediary;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import com.td.models.Environment;
import com.td.models.Pet;
import com.td.models.TurtleDiaryDatabaseHelper;
import com.td.models.Type;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class PetActivity extends Activity {
	static final int ID_DATEPICKER = 0;

	TurtleDiaryDatabaseHelper turtleDiaryHelper = new TurtleDiaryDatabaseHelper(
			this);

	enum PetActivityState {
		FirstAdd, Add, View, Edit
	};

	Pet nowPet;
	PetActivityState nowState;
	private MenuItem menuItem;

	Button addButton;
	Button editButton;
	Button restoreButton;
	Button nextButton;
	EditText petNameEditText;
	Spinner petTypeSpinner;
	RadioGroup genderRadioGroup;
	Spinner environmentSpinner;
	Button birthdayButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pet);
		initializeWidgets();
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
	}

	private void initializeWidgets() {
		addButton = (Button) findViewById(R.id.petActivityAddButton);
		editButton = (Button) findViewById(R.id.petActivityEditButton);
		restoreButton = (Button) findViewById(R.id.petActivityRestoreButton);
		nextButton = (Button) findViewById(R.id.petActivityNextButton);
		addButton.setOnClickListener(getAddButtonOnClickListener());
		editButton.setOnClickListener(getEditButtonOnClickListener());
		restoreButton.setOnClickListener(getRestoreButtonOnClickListener());
		nextButton.setOnClickListener(getNextButtonOnClickListener());
		petNameEditText = (EditText) findViewById(R.id.petActivityPetNameEditText);
		petTypeSpinner = (Spinner) findViewById(R.id.petActivityPetTypeSpinner);
		genderRadioGroup = (RadioGroup) findViewById(R.id.petActivityGenderRadioGroup);
		environmentSpinner = (Spinner) findViewById(R.id.petActivityEnvironmentSpinner);
		birthdayButton = (Button) findViewById(R.id.petActivityBirthdayButton);
		birthdayButton.setOnClickListener(getBirthdayEditTextClickListener());

		// Type
		List<Type> types = turtleDiaryHelper.getTypes();
		ArrayAdapter<Type> typeAdapter = new ArrayAdapter<Type>(this,
				android.R.layout.simple_dropdown_item_1line, types);
		petTypeSpinner.setAdapter(typeAdapter);
		// Environment
		Environment environment = (Environment) getIntent()
				.getSerializableExtra("environment");
		List<Environment> environments = turtleDiaryHelper.getEnvironments();
		if (environment != null) {
			environments.add(environment);
		}
		ArrayAdapter<Environment> environmentAdapter = new ArrayAdapter<Environment>(
				this, android.R.layout.simple_dropdown_item_1line, environments);
		environmentSpinner.setAdapter(environmentAdapter);
	}

	private OnClickListener getBirthdayEditTextClickListener() {
		return new OnClickListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				showDialog(ID_DATEPICKER);
			}
		};
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case ID_DATEPICKER:
			Calendar c = Calendar.getInstance();
			if (nowPet != null) {
				c.setTime(Date.valueOf(nowPet.getBirthday()));
			}
			int myYear = c.get(Calendar.YEAR);
			int myMonth = c.get(Calendar.MONTH);
			int myDay = c.get(Calendar.DAY_OF_MONTH);
			return new DatePickerDialog(this, myDateSetListener, myYear,
					myMonth, myDay);
		default:
			return null;

		}
	}

	private DatePickerDialog.OnDateSetListener myDateSetListener = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			birthdayButton.setText(year + "-" + (monthOfYear + 1) + "-"
					+ dayOfMonth);
		}
	};

	private void changeState(PetActivityState state) {
		nowState = state;
		changeButtonState(state);
		changeInputWidgetState(state);
	}

	private void setWidgetsEnabled(boolean enabled) {
		petNameEditText.setEnabled(enabled);
		petTypeSpinner.setEnabled(enabled);
		for (int i = 0; i < genderRadioGroup.getChildCount(); i++) {
			RadioButton radioButton = (RadioButton) genderRadioGroup
					.getChildAt(i);
			radioButton.setEnabled(enabled);
		}
		environmentSpinner.setEnabled(enabled);
		birthdayButton.setEnabled(enabled);
	}

	private void changeInputWidgetState(PetActivityState state) {
		switch (state) {
		case FirstAdd:
		case Add: {
			// birthday
			Calendar c = Calendar.getInstance();
			String dateString = c.get(Calendar.YEAR) + "-"
					+ (c.get(Calendar.MONTH) + 1) + "-"
					+ c.get(Calendar.DAY_OF_MONTH);
			birthdayButton.setText(dateString);
			break;
		}
		case Edit:
			setWidgetsEnabled(true);
			break;
		case View: {
			setWidgetsEnabled(false);
			// name
			petNameEditText.setText(nowPet.getName());
			// type
			List<Type> types = turtleDiaryHelper.getTypes();
			for (Type t : types) {
				if (nowPet.getTid() == t.getTid()) {
					petTypeSpinner.setSelection(types.indexOf(t));
				}
			}
			// gender
			for (int i = 0; i < genderRadioGroup.getChildCount(); i++) {
				RadioButton radioButton = (RadioButton) genderRadioGroup
						.getChildAt(i);
				String gender = radioButton.getText().toString();
				radioButton.setChecked(gender.equals(nowPet.getGender()));
			}
			// environment
			List<Environment> environments = turtleDiaryHelper
					.getEnvironments();
			for (Environment e : environments) {
				if (nowPet.getEid() == e.getEid()) {
					environmentSpinner.setSelection(environments.indexOf(e));
				}
			}
			// birthday
			Date date = Date.valueOf(nowPet.getBirthday());
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			birthdayButton.setText(c.get(Calendar.YEAR) + "-"
					+ (c.get(Calendar.MONTH)+1) + "-"
					+ c.get(Calendar.DAY_OF_MONTH));
			break;
		}
		}
	}

	private void changeButtonState(PetActivityState state) {
		setButtonsVisible(View.INVISIBLE);
		switch (state) {
		case FirstAdd:
			nextButton.setVisibility(View.VISIBLE);
			break;
		case Add:
			addButton.setVisibility(View.VISIBLE);
			break;
		case Edit:
			editButton.setVisibility(View.VISIBLE);
			restoreButton.setVisibility(View.VISIBLE);
			break;
		case View:
			break;
		}
		if (menuItem != null) {
			menuItem.setVisible(nowState == PetActivityState.View);
		}
	}

	private void setButtonsVisible(int visibility) {
		nextButton.setVisibility(visibility);
		addButton.setVisibility(visibility);
		editButton.setVisibility(visibility);
		restoreButton.setVisibility(visibility);
	}

	private OnClickListener getAddButtonOnClickListener() {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				Pet pet = getPetFromWidget();
				turtleDiaryHelper.addPet(pet);
				finish();
			}
		};
	}

	private OnClickListener getNextButtonOnClickListener() {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				Pet pet = getPetFromWidget();
				turtleDiaryHelper.addPet(pet);
				startActivity(new Intent(PetActivity.this,
						HomePageActivity.class));
				EnvironmentActivity.finishByOtherActivity();
				finish();
			}
		};
	}

	private String getGenderFromRadioGroup() {
		int selectedId = genderRadioGroup.getCheckedRadioButtonId();
		RadioButton selectedRadioButton = (RadioButton) findViewById(selectedId);
		return selectedRadioButton.getText().toString();
	}

	private int getTidFromTypeSpinner() {
		return ((Type) petTypeSpinner.getSelectedItem()).getTid();
	}

	private int getEidFromEnvironmentSpinnerOrIntent() {
		Environment environment;
		if (nowState == PetActivityState.FirstAdd) {
			environment = (Environment) getIntent().getSerializableExtra(
					"environment");
			turtleDiaryHelper.addEnvironment(environment);
		} else {
			environment = (Environment) environmentSpinner.getSelectedItem();
		}
		return environment.getEid();
	}

	private Pet getPetFromWidget() {
		Pet pet = new Pet();
		pet.setName(petNameEditText.getText().toString());
		pet.setBirthday(getBirthdayFromBirthdayButton());
		pet.setGender(getGenderFromRadioGroup());
		pet.setTid(getTidFromTypeSpinner());
		pet.setEid(getEidFromEnvironmentSpinnerOrIntent());
		return pet;
	}

	private OnClickListener getEditButtonOnClickListener() {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {

				nowPet.setBirthday(getBirthdayFromBirthdayButton());
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

	private String getBirthdayFromBirthdayButton() {
		String dateString = birthdayButton.getText().toString();
		return dateString;
	}

	private OnClickListener getRestoreButtonOnClickListener() {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				changeState(PetActivityState.View);
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
				changeState(PetActivityState.Edit);
				return false;
			}
		};
	}
}
