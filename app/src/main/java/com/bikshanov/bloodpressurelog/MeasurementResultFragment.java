package com.bikshanov.bloodpressurelog;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class MeasurementResultFragment extends Fragment {

    private static final String ARG_RESULT_ID = "result_id";
    private static final String DIALOG_DATE = "DialogDate";
    private static final String DIALOG_TIME = "DialogTime";

    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = 1;

    private static final String ARM_LEFT = "left";
    private static final String ARM_RIGHT = "right";

    private boolean flag_new = true;

    private MeasurementResult mMeasurementResult;
    private TextInputEditText mSysPressureEditText;
    private TextInputEditText mDiaPressureEditText;
    private TextInputEditText mPulseEditText;
    private TextInputEditText mCommentEditText;
    private Button mDateButton;
    private Button mTimeButton;
    private CheckBox mArrhythmiaCheckBox;
    private TextInputLayout mSysPressureInputLayout;
    private TextInputLayout mDiaPressureInputLayout;
    private TextInputLayout mPulseInputLayout;
    private RadioButton mRightArmRadioButton;
    private RadioButton mLeftArmRadioButton;

    private Date mDate;
    private Date mTime;

    public static MeasurementResultFragment newInstance(UUID result_id) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_RESULT_ID, result_id);
        MeasurementResultFragment fragment = new MeasurementResultFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

//        mMeasurementResult = new MeasurementResult();


        UUID resultId = (UUID) getArguments().getSerializable(ARG_RESULT_ID);

        if (resultId != null) {
            mMeasurementResult = ResultsStore.get(getActivity()).getMeasurementResult(resultId);
            flag_new = false;
        }
    }

    @Override
    public void onPause() {
        super.onPause();


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_result, menu);
        if (flag_new) {
            menu.findItem(R.id.delete_result).setVisible(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
            case R.id.save_result:
//                saveMeasurement();
                checkValues();
//                getActivity().finish();
                return true;
            case R.id.delete_result:
                removeMeasurement();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void removeMeasurement() {
        AlertDialog.Builder confirmDeleteDialog = new AlertDialog.Builder(getActivity());
        confirmDeleteDialog.setMessage(R.string.delete_dialog_message);
        confirmDeleteDialog.setPositiveButton(R.string.dialog_delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ResultsStore.get(getActivity()).removeResult(mMeasurementResult);
                getActivity().finish();
            }
        });
        confirmDeleteDialog.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        confirmDeleteDialog.show();
    }

    private void checkValues() {
        if (!Helpers.isNumberValid(mSysPressureEditText.getText())) {
            mSysPressureInputLayout.setError(getResources().getString(R.string.wrong_value));
        } else if (!Helpers.isNumberValid(mDiaPressureEditText.getText())) {
            mDiaPressureInputLayout.setError(getResources().getString(R.string.wrong_value));
        } else if (!Helpers.isNumberValid(mPulseEditText.getText())) {
            mPulseInputLayout.setError(getResources().getString(R.string.wrong_value));
        } else {
            mSysPressureInputLayout.setError(null);
            mDiaPressureInputLayout.setError(null);
            mPulseInputLayout.setError(null);

            saveMeasurement();
        }
    }

    private void saveMeasurement() {

        if (mMeasurementResult == null) {
            mMeasurementResult = new MeasurementResult();
        }

        mMeasurementResult.setSysBloodPressure(Integer.parseInt(mSysPressureEditText.getText().toString()));
        mMeasurementResult.setDiaBloodPressure(Integer.parseInt(mDiaPressureEditText.getText().toString()));
        mMeasurementResult.setPulse(Integer.parseInt(mPulseEditText.getText().toString()));

        if (Helpers.isEmpty(mCommentEditText)) {
            mMeasurementResult.setComment("");
        } else {
            mMeasurementResult.setComment(mCommentEditText.getText().toString());
        }

        mMeasurementResult.setDate(dateTime(mDate, mTime));
        mMeasurementResult.setArrhythmia(mArrhythmiaCheckBox.isChecked());

        if (mRightArmRadioButton.isChecked()) {
            mMeasurementResult.setArm(ARM_RIGHT);
        } else if (mLeftArmRadioButton.isChecked()) {
            mMeasurementResult.setArm(ARM_LEFT);
        }

        if (flag_new) {
            ResultsStore.get(getActivity()).addResult(mMeasurementResult);
        } else {
            ResultsStore.get(getActivity()).updateResult(mMeasurementResult);
        }

        getActivity().finish();
    }

    public Date dateTime(Date date, Date time) {
        Calendar aDate = Calendar.getInstance();
        aDate.setTime(date);

        Calendar aTime = Calendar.getInstance();
        aTime.setTime(time);

        Calendar aDateTime = Calendar.getInstance();
        aDateTime.set(Calendar.DAY_OF_MONTH, aDate.get(Calendar.DAY_OF_MONTH));
        aDateTime.set(Calendar.MONTH, aDate.get(Calendar.MONTH));
        aDateTime.set(Calendar.YEAR, aDate.get(Calendar.YEAR));
        aDateTime.set(Calendar.HOUR_OF_DAY, aTime.get(Calendar.HOUR_OF_DAY));
        aDateTime.set(Calendar.MINUTE, aTime.get(Calendar.MINUTE));
        aDateTime.set(Calendar.SECOND, aTime.get(Calendar.SECOND));

        return aDateTime.getTime();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_result, container, false);

        mCommentEditText = (TextInputEditText) v.findViewById(R.id.comment);
        mSysPressureEditText = (TextInputEditText) v.findViewById(R.id.sys_pressure_value);
        mDiaPressureEditText = (TextInputEditText) v.findViewById(R.id.dia_pressure_value);
        mPulseEditText = (TextInputEditText) v.findViewById(R.id.pulse_value);
        mArrhythmiaCheckBox = v.findViewById(R.id.checkBox_arrhythmia);
        mDateButton = (Button) v.findViewById(R.id.button_date);
        mTimeButton = (Button) v.findViewById(R.id.button_time);

        mSysPressureInputLayout = v.findViewById(R.id.sysTextInputLayout);
        mDiaPressureInputLayout = v.findViewById(R.id.diaTextInputLayout);
        mPulseInputLayout = v.findViewById(R.id.pulseTextInputLayout);

        mRightArmRadioButton = v.findViewById(R.id.arm_right);
        mLeftArmRadioButton = v.findViewById(R.id.arm_left);

        if (mMeasurementResult != null) {
            int sys = mMeasurementResult.getSysBloodPressure();
            int dia = mMeasurementResult.getDiaBloodPressure();
            int pulse = mMeasurementResult.getPulse();
            String comment = mMeasurementResult.getComment();

            String arm = mMeasurementResult.getArm().trim();

            mSysPressureEditText.setText(Integer.toString(sys));
            mDiaPressureEditText.setText(Integer.toString(dia));
            mPulseEditText.setText(Integer.toString(pulse));
            mCommentEditText.setText(comment);
            mArrhythmiaCheckBox.setChecked(mMeasurementResult.isArrhythmia());

            if (arm.equals(ARM_RIGHT)) {
                mRightArmRadioButton.setChecked(true);
            } else if (arm.equals(ARM_LEFT)) {
                mLeftArmRadioButton.setChecked(true);
            }

            mDate = mMeasurementResult.getDate();
            mTime = mMeasurementResult.getDate();
            mDateButton.setText(Helpers.formatShortDate(getContext(), mDate));
//            mTimeButton.setText(Helpers.formatTime(mTime));
            mTimeButton.setText(Helpers.formatTime(getContext(), mTime));
        } else {
            mDate = new Date();
            mTime = mDate;
            mDateButton.setText(Helpers.formatShortDate(getContext(), mDate));
            mTimeButton.setText(Helpers.formatTime(getContext(), mTime));
            mRightArmRadioButton.setChecked(true);
        }

        mSysPressureEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (Helpers.isNumberValid(editable)) {
                    mDiaPressureEditText.requestFocus();
                    mSysPressureInputLayout.setError(null);
                }
            }
        });

        mDiaPressureEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (Helpers.isNumberValid(editable)) {
                    mPulseEditText.requestFocus();
                    mDiaPressureInputLayout.setError(null);
                }
            }
        });

        mPulseEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (Helpers.isNumberValid(editable)) {
                    InputMethodManager imm =
                            (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mPulseEditText.getWindowToken(), 0);
                    mPulseEditText.clearFocus();
                    mPulseInputLayout.setError(null);
                }
            }
        });

//        if (sys != 0) {
//            mSysPressureEditText.setText(Integer.toString(sys));
//        }
//        if (dia != 0) {
//            mDiaPressureEditText.setText(Integer.toString(dia));
//        }
//        if (pulse != 0) {
//            mPulseEditText.setText(Integer.toString(pulse));
//        }
//
//        mCommentEditText.setText(comment);
//
//        mArrhythmiaCheckBox.setChecked(mMeasurementResult.isArrhythmia());

//        String sysPressure = Integer.toString(mMeasurementResult.getSysBloodPressure());
//        String diaPressure = Integer.toString(mMeasurementResult.getDiaBloodPressure());
//        String pulse = Integer.toString(mMeasurementResult.getPulse());

//        updateDate();
//        mDateButton.setEnabled(false);
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
//                if (mMeasurementResult != null) {
//                    mDate = mMeasurementResult.getDate();
//                }
                DatePickerFragment dialog = DatePickerFragment.newInstance(mDate);
                dialog.setTargetFragment(MeasurementResultFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });

//        updateTime();

//        mTimeButton.setEnabled(false);
        mTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
//                if (mMeasurementResult != null) {
//                    mTime = mMeasurementResult.getDate();
//                }
                TimePickerFragment dialog = TimePickerFragment.newInstance(mTime);
                dialog.setTargetFragment(MeasurementResultFragment.this, REQUEST_TIME);
                dialog.show(manager, DIALOG_TIME);
            }
        });

//        mArrhythmiaCheckBox.setChecked(mMeasurementResult.isArrhythmia());
//        mArrhythmiaCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//                mMeasurementResult.setArrhythmia(isChecked);
//            }
//        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_DATE) {
            mDate = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
//            mDate = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
//            mMeasurementResult.setDate(mDate);
//            Log.d("DATE", mDate.toString());
            updateDate(mDate);
        }

        if (requestCode == REQUEST_TIME) {
            mTime = (Date) data.getSerializableExtra(TimePickerFragment.EXTRA_TIME);
//            mTime = (Date) data.getSerializableExtra(TimePickerFragment.EXTRA_TIME);
//            Log.d("DATE", mTime.toString());
//            mMeasurementResult.setDate(mTime);
            updateTime(mTime);

        }
    }

    private void updateTime(Date time) {
//        mTimeButton.setText(Helpers.formatTime(mMeasurementResult.getDate()));
//        mTime = mMeasurementResult.getDate();
//        mTimeButton.setText(Helpers.formatTime(time));
        mTimeButton.setText(Helpers.formatTime(getContext(), time));
    }

    private void updateDate(Date date) {
//        mDateButton.setText(Helpers.formatShortDate(mMeasurementResult.getDate()));
//        mDate = mMeasurementResult.getDate();
        mDateButton.setText(Helpers.formatShortDate(getContext(), date));
    }
}
