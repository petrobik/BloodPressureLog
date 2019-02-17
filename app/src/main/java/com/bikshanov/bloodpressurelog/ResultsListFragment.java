package com.bikshanov.bloodpressurelog;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ResultsListFragment extends Fragment {


    private final String TAG = "ResultListFragment";
    private RecyclerView mResultsRecyclerView;
    private ResultsAdapter mAdapter;
    private int mAdapterPosition;
    private FloatingActionButton mFloatingActionButton;
    private TextView mEmptyTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_result_list, container, false);
        mResultsRecyclerView = (RecyclerView) view.findViewById(R.id.results_recycler_view);

        mResultsRecyclerView.setHasFixedSize(true);
//        mResultsRecyclerView.setNestedScrollingEnabled(false);
        mResultsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

//        mFloatingActionButton = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);
        mFloatingActionButton = (FloatingActionButton) getActivity().findViewById(R.id.floatingActionButton);

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                MeasurementResult measurementResult = new MeasurementResult();
//                ResultsStore.get(getActivity()).addResult(measurementResult);
//                Intent intent = MeasurementResultActivity.newIntent(getActivity(), measurementResult.getId());
                Intent intent = MeasurementResultActivity.newIntent(getActivity(), null);
                startActivity(intent);
            }
        });

        mEmptyTextView = view.findViewById(R.id.empty_text);

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    public void updateUI() {
        ResultsStore resultsStore = ResultsStore.get(getActivity());
        List<MeasurementResult> measurementResults = resultsStore.getMeasurementResults();

        // Sort list of measurements by date before setting the adapter
//        if (measurementResults.size() > 0) {
//            Collections.sort(measurementResults, new Comparator<MeasurementResult>() {
//                @Override
//                public int compare(MeasurementResult result1, MeasurementResult result2) {
//                    return -result1.getDate().compareTo(result2.getDate());
//                }
//            });
//        }

        if (mAdapter == null) {
            mAdapter = new ResultsAdapter(measurementResults);
            mResultsRecyclerView.setAdapter(mAdapter);
        } else {

            // Sort list of measurements by date before setting the adapter
//        if (measurementResults.size() > 0) {
//            Collections.sort(measurementResults, new Comparator<MeasurementResult>() {
//                @Override
//                public int compare(MeasurementResult result1, MeasurementResult result2) {
//                    return -result1.getDate().compareTo(result2.getDate());
//                }
//            });
//        }

            mAdapter.setResults(measurementResults);

//TODO Разобраться с notifyItemChanged (не обновляется item после добавления жлемета или при редактировании существующего)

            mAdapter.notifyDataSetChanged();

            if (mAdapter.getItemCount() == 0) {
                mResultsRecyclerView.setVisibility(View.GONE);
                mEmptyTextView.setVisibility(View.VISIBLE);
            } else {
                mResultsRecyclerView.setVisibility(View.VISIBLE);
                mEmptyTextView.setVisibility(View.GONE);
            }
//            mAdapter.notifyItemChanged(mAdapterPosition);
        }
    }

    private class ResultsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mPressureTextView;
        private TextView mPulseTextView;
        private TextView mDateTextView;
        private TextView mTimeTextView;
        private View mBorderView;
        private ImageView mArrhythmiaImageView;

        private MeasurementResult mResult;

        public ResultsHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_result, parent, false));
            itemView.setOnClickListener(this);

            mPressureTextView = (TextView) itemView.findViewById(R.id.pressure_value);
            mPulseTextView = (TextView) itemView.findViewById(R.id.pulse_value);
            mDateTextView = (TextView) itemView.findViewById(R.id.date);
            mTimeTextView = (TextView) itemView.findViewById(R.id.time);
            mBorderView = itemView.findViewById(R.id.color_border);
            mArrhythmiaImageView = (ImageView) itemView.findViewById(R.id.arrhythmia);
        }

        public void bind(MeasurementResult result) {
            mResult = result;
            String pressureValue = mResult.getSysBloodPressure() + " / " + mResult.getDiaBloodPressure();
            String pulseValue = Integer.toString(mResult.getPulse());
            mPressureTextView.setText(pressureValue);
            mPulseTextView.setText(pulseValue);
            mDateTextView.setText(Helpers.formatShortDate(getContext(), mResult.getDate()));
//            mTimeTextView.setText(Helpers.formatTime(mResult.getDate()));
            mTimeTextView.setText(Helpers.formatTime(getContext(), mResult.getDate()));
            mBorderView.setBackgroundColor(Helpers.pressureToColor(getActivity(),
                    result.getSysBloodPressure(), result.getDiaBloodPressure()));
            mArrhythmiaImageView.setVisibility(mResult.isArrhythmia() ? View.VISIBLE : View.GONE);
        }

        @Override
        public void onClick(View view) {
            mAdapterPosition = getAdapterPosition();
            Intent intent = MeasurementResultActivity.newIntent(getActivity(), mResult.getId());
            Log.d(TAG, "ID: " + mResult.getId());
            startActivity(intent);
        }
    }

    private class ResultsAdapter extends RecyclerView.Adapter<ResultsHolder>  {

        private List<MeasurementResult> mResults;

        public ResultsAdapter(List<MeasurementResult> results) {
            mResults = results;
        }


        @Override
        public ResultsHolder onCreateViewHolder( ViewGroup parent, int viewType) {

            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new ResultsHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(ResultsHolder resultsHolder, int position) {
            MeasurementResult result = mResults.get(position);
            resultsHolder.bind(result);

        }

        public void setResults(List<MeasurementResult> results) {
            mResults = results;
        }

        @Override
        public int getItemCount() {
            return mResults.size();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }
    }
}


