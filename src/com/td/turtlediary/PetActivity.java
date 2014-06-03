package com.td.turtlediary;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import com.td.models.Environment;
import com.td.models.Pet;
import com.td.models.TurtleDiaryDatabaseHelper;
import com.td.models.Type;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
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
import android.widget.Toast;

public class PetActivity extends Activity {
	static final int ID_DATEPICKER = 0;
	protected static final int RESULT_LOAD_IMAGE = 1;

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
	Button selectPictureButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pet);
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		initializeWidgets();
		int pid = getIntent().getIntExtra("pid", -1);
		if (pid != -1) {
			nowPet = turtleDiaryHelper.getPet(pid);
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
		selectPictureButton = (Button) findViewById(R.id.petActivitySelectPictureButton);
		selectPictureButton
				.setOnClickListener(getSelectPictureButtonOnClickListener());

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

	private OnClickListener getSelectPictureButtonOnClickListener() {
		return new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(intent, RESULT_LOAD_IMAGE);
			}
		};
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK
				&& null != data) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };
			Cursor cursor = getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();

			Bitmap bitmap = BitmapFactory.decodeFile(picturePath);

			WindowManager wm = getWindowManager();
			// Display d = wm.getDefaultDisplay();
			// int biggerSide = d.getWidth() > d.getHeight() ? d.getWidth() : d
			// .getHeight();
			int width = bitmap.getWidth() / 4;
			int height = bitmap.getHeight() / 4;
			// if (width > height) {
			// height = (int) ((height * biggerSide) / (float) width);
			// width = biggerSide;
			// }
			bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
			BitmapDrawable bitmapDrawable = new BitmapDrawable(
					this.getResources(), bitmap);
			selectPictureButton.setBackgroundDrawable(bitmapDrawable);
		}
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
				c.setTime(nowPet.getBirthday());
			}
			int myYear = c.get(Calendar.YEAR);
			int myMonth = c.get(Calendar.MONTH);// 這邊不用加1的原因是他跟onDateSet事件的月份一樣都是以0當作January
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
		selectPictureButton.setEnabled(enabled);
	}

	private String getDateString(Date date) {
		Calendar c = Calendar.getInstance();
		if (date != null) {
			c.setTime(date);
		}
		String dateString = c.get(Calendar.YEAR) + "-"
				+ (c.get(Calendar.MONTH) + 1) + "-"// January is 0
				+ c.get(Calendar.DAY_OF_MONTH);
		return dateString;
	}

	private void changeInputWidgetState(PetActivityState state) {
		switch (state) {
		case FirstAdd:
		case Add: {
			setTitle("新增寵物");
			birthdayButton.setText(getDateString(null));
			break;
		}
		case Edit:
			setTitle("編輯寵物");
			setWidgetsEnabled(true);
			break;
		case View: {
			setTitle("檢視寵物");
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
			Date date = nowPet.getBirthday();
			birthdayButton.setText(getDateString(date));

			if (nowPet.getImage() != null) {
				Drawable image = new BitmapDrawable(
						BitmapFactory.decodeByteArray(nowPet.getImage(), 0,
								nowPet.getImage().length));
				// select.setImageDrawable(image);
				selectPictureButton.setBackgroundDrawable(image);
			}
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
				if (pet != null) {
					turtleDiaryHelper.addPet(pet);
					finish();
				}
			}
		};
	}

	private OnClickListener getNextButtonOnClickListener() {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				Pet pet = getPetFromWidget();
				if (pet != null) {
					turtleDiaryHelper.addPet(pet);
					startActivity(new Intent(PetActivity.this,
							HomePageActivity.class));
					EnvironmentActivity.finishByOtherActivity();
					finish();
				}
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
		if (petNameEditText.getText().toString().equals("")) {
			Toast.makeText(this, "寵物名稱不能為空", Toast.LENGTH_LONG).show();
			return null;
		}
		else {
			pet.setName(petNameEditText.getText().toString());
		}
		pet.setBirthday(getBirthdayFromBirthdayButton());
		pet.setGender(getGenderFromRadioGroup());
		pet.setTid(getTidFromTypeSpinner());
		pet.setEid(getEidFromEnvironmentSpinnerOrIntent());
		pet.setImage(getPictureFromPictureButton());
		return pet;
	}

	private byte[] getPictureFromPictureButton() {
		try {
			Drawable drawable = selectPictureButton.getBackground();
			Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
			byte[] bitmapdata = stream.toByteArray();
			return bitmapdata;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private OnClickListener getEditButtonOnClickListener() {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				int pid = nowPet.getPid();
				nowPet = getPetFromWidget();
				if (nowPet != null) {
					nowPet.setPid(pid);
					turtleDiaryHelper.updatePet(nowPet);
					changeState(PetActivityState.View);
				}
			}
		};
	}

	private Date getBirthdayFromBirthdayButton() {
		// 欲轉換的日期字串
		String dateString = birthdayButton.getText().toString();
		// 設定日期格式
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",
				Locale.getDefault());
		// 進行轉換
		Date date = new Date();
		try {
			date = sdf.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
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
