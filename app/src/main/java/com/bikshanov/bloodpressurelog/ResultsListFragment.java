package com.bikshanov.bloodpressurelog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ResultsListFragment extends Fragment {

    private RecyclerView mResultsRecyclerView;
    private ResultsAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_result_list, container, false);
        mResultsRecyclerView= (RecyclerView) view.findViewById(R.id.results_recycler_view);
        mResultsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    public void updateUI() {
        Results results = Results.get(getActivity());
        List<MeasurementResult> measurementResults = results.getMeasurementResults();

        if (mAdapter == null) {
            mAdapter = new ResultsAdapter(measurementResults);
            mResultsRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }

    }

    private class ResultsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mPressureTextView;
        private TextView mPulseTextView;
        private TextView mDateTextView;
        private View mBorderView;

        private MeasurementResult mResult;

        public ResultsHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_result, parent, false));
            itemView.setOnClickListener(this);

            mPressureTextView = (TextView) itemView.findViewById(R.id.pressure_value);
            mPulseTextView = (TextView) itemView.findViewById(R.id.pulse_value);
            mDateTextView = (TextView) itemView.findViewById(R.id.date);
            mBorderView = itemView.findViewById(R.id.color_border);
        }

        public void bind(MeasurementResult result) {
            mResult = result;
            String pressureValue = mResult.getSysBloodPressure() + " / " + mResult.getDiaBloodPressure();
            String pulseValue = Integer.toString(mResult.getPulse());
            mPressureTextView.setText(pressureValue);
            mPulseTextView.setText(pulseValue);
            mDateTextView.setText(Helpers.formatFullDate(mResult.getDate()));
            mBorderView.setBackgroundColor(Helpers.pressureToColor(result.getSysBloodPressure()));
        }

        @Override
        public void onClick(View view) {
            Intent intent = MeasurementResultActivity.newIntent(getActivity(), mResult.getId());
            startActivity(intent);
        }
    }

    private class ResultsAdapter extends RecyclerView.Adapter<ResultsHolder> {

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

        @Override
        public int getItemCount() {
            return mResults.size();
        }
    }
}
