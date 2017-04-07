package ca.cmpt276.carbonTracker.UI;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sasha.carbontracker.R;

import ca.cmpt276.carbonTracker.AlternateUI.AddCarActivity;
import ca.cmpt276.carbonTracker.AlternateUI.AddJourneyActivity_Alternate;
import ca.cmpt276.carbonTracker.AlternateUI.AddRouteActivity;
import ca.cmpt276.carbonTracker.AlternateUI.AddUtilityActivity;
import ca.cmpt276.carbonTracker.AlternateUI.MainMenuActivity_Alternate;
import ca.cmpt276.carbonTracker.Internal_Logic.*;

/**
 * The EditCarActivity is similar to the AddCarActivity, however (as the title of the activity states)
 * the user is able to edit an existing car rather than add a new one to the carCollection list.
 *
 * @author Team Teal
 */

public class EditCarActivity extends AppCompatActivity {

    CarbonModel modelInstance;
    String selectedMake;
    String selectedModel;
    String selectedYear;
    int selectedVariation;
    String selectedNickname;
    int selectedIndex;
    private ActionMenuView amvMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_car);
        modelInstance = CarbonModel.getInstance();

        Intent callingIntent = getIntent();
        selectedIndex = callingIntent.getIntExtra("Index", 0);

        setupSelectMakeSpinner();
        setupSelectModelSpinner();
        setupSelectYearSpinner();
        setupSelectSpecsSpinner();
        setupEnterNicknameEditText();
        setupEditCarButton();
        setupCancelButton();
        setupActionBar();
    }

    private void setupSelectMakeSpinner() {
        final Spinner selectMake = (Spinner) findViewById(R.id.spinnerSelectMakeEdit);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item,
                modelInstance.getCarData().getMakeList());
        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        selectMake.setAdapter(spinnerArrayAdapter);

        selectMake.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setupSelectModelSpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setupSelectModelSpinner() {
        Spinner selectModel = (Spinner) findViewById(R.id.spinnerSelectModelEdit);
        Spinner selectMake = (Spinner) findViewById(R.id.spinnerSelectMakeEdit);

        selectedMake = selectMake.getSelectedItem().toString();

        modelInstance.getCarData().updateModelList(selectedMake);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item,
                modelInstance.getCarData().getModelList());
        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        selectModel.setAdapter(spinnerArrayAdapter);

        selectModel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setupSelectYearSpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setupSelectYearSpinner() {
        Spinner selectModel = (Spinner) findViewById(R.id.spinnerSelectModelEdit);
        Spinner selectYear = (Spinner) findViewById(R.id.spinnerSelectYearEdit);

        selectedModel = selectModel.getSelectedItem().toString();

        modelInstance.getCarData().updateYearList(selectedMake, selectedModel);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item,
                modelInstance.getCarData().getYearList());
        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        selectYear.setAdapter(spinnerArrayAdapter);

        selectYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setupSelectSpecsSpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setupSelectSpecsSpinner() {
        Spinner selectYear = (Spinner) findViewById(R.id.spinnerSelectYearEdit);
        Spinner selectSpecs = (Spinner) findViewById(R.id.spinnerSelectSpecsEdit);

        selectedYear = selectYear.getSelectedItem().toString();

        modelInstance.getCarData().updateSpecsList(selectedMake, selectedModel, selectedYear);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item,
                modelInstance.getCarData().getSpecsList());
        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        selectSpecs.setAdapter(spinnerArrayAdapter);

        selectSpecs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedVariation = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setupEnterNicknameEditText() {
        EditText enterNickname = (EditText) findViewById(R.id.editTextEnterNicknameEdit);
        enterNickname.setText(modelInstance.getSelectedCar().getNickname());
        enterNickname.setImeOptions(EditorInfo.IME_ACTION_DONE);
        enterNickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    private void setupEditCarButton() {
        Button editCar = (Button) findViewById(R.id.buttonEditCar);
        final EditText enterNickname = (EditText) findViewById(R.id.editTextEnterNicknameEdit);
        editCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedNickname = enterNickname.getText().toString();
                if (selectedNickname.equals("")) {
                    Toast.makeText(EditCarActivity.this,
                            "Please enter a nickname for your car.",
                            Toast.LENGTH_LONG).show();
                } else {
                    modelInstance.getCarCollection().hideCar(modelInstance.getSelectedCar());
                    Car editedCar = modelInstance.getCarData().findCar(selectedMake, selectedModel,
                            Integer.parseInt(selectedYear), selectedVariation);

                    editedCar.setNickname(selectedNickname);

                    //model.getCarCollection().editCar(car, selectedIndex);
                    //model.setSelectedCar(car);
                    modelInstance.getSelectedCar().editCar(editedCar);

                    SaveData.saveCars(EditCarActivity.this);
                    startActivity(new Intent(EditCarActivity.this, SelectTransportationActivity.class));
                    finish();
                }
            }
        });
    }

    private void setupCancelButton() {
        Button cancel = (Button) findViewById(R.id.buttonCancelEdit);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditCarActivity.this, SelectTransportationActivity.class));
                finish();
            }
        });
    }

    private void setupActionBar() {
        // Inflate your custom layout
        Toolbar toolBar = (Toolbar) findViewById(R.id.mainMenu_toolBar);
        amvMenu = (ActionMenuView) toolBar.findViewById(R.id.amvMenu);
        amvMenu.setOnMenuItemClickListener(new ActionMenuView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                return onOptionsItemSelected(menuItem);
            }
        });
        setSupportActionBar(toolBar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_actions_mainmenu, amvMenu.getMenu());
        MenuItem itemSwitch = amvMenu.getMenu().findItem(R.id.toolbar_switch);
        itemSwitch.setActionView(R.layout.switch_layout);

        final SwitchCompat unitToggle = (SwitchCompat) amvMenu.getMenu().findItem(R.id.toolbar_switch).
                getActionView().findViewById(R.id.switchForActionBar);
        if (CarbonModel.getInstance().getTreeUnit().getTreeUnitStatus()) {
            unitToggle.setChecked(true);
        }
        unitToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    CarbonModel.getInstance().getTreeUnit().setTreeUnitStatus(true);
                    SaveData.saveTreeUnit(EditCarActivity.this);
                } else {
                    CarbonModel.getInstance().getTreeUnit().setTreeUnitStatus(false);
                    SaveData.saveTreeUnit(EditCarActivity.this);
                }
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id) {
            case R.id.action_settings: {
                return true;
            }
            case R.id.action_addVehicle: {
                Intent intent = new Intent(EditCarActivity.this, AddCarActivity.class);
                intent.putExtra("Caller", "EditCar");
                startActivity(intent);
                finish();
                return true;
            }
            case R.id.action_addRoute: {
                Intent intent = new Intent(EditCarActivity.this, AddRouteActivity.class);
                intent.putExtra("Caller", "EditCar");
                startActivity(intent);
                finish();
                return true;
            }
            case R.id.action_addUtility: {
                Intent intent = new Intent(EditCarActivity.this, AddUtilityActivity.class);
                intent.putExtra("Caller", "EditCar");
                startActivity(intent);
                finish();
                return true;
            }
            case R.id.action_addJourney: {
                Intent intent = new Intent(EditCarActivity.this, AddJourneyActivity_Alternate.class);
                intent.putExtra("Caller", "EditCar");
                startActivity(intent);
                finish();
                return true;
            }
            case android.R.id.home: {
                Intent intent = new Intent(EditCarActivity.this, MainMenuActivity_Alternate.class);
                startActivity(intent);
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, EditCarActivity.class);
    }
}