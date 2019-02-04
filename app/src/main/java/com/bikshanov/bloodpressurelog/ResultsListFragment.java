package com.bikshanov.bloodpressurelog;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ResultsListFragment extends Fragment {

    private RecyclerView mResultsRecyclerView;
    private ResultsAdapter mAdapter;
    private int mAdapterPosition;
    private FloatingActionButton mFloatingActionButton;

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
                MeasurementResult measurementResult = new MeasurementResult();
                ResultsStore.get(getActivity()).addResult(measurementResult);
                Intent intent = MeasurementResultActivity.newIntent(getActivity(), measurementResult.getId());
                startActivity(intent);
            }
        });

//        mResultsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                if (dy < 0 && !mFloatingActionButton.isShown()) {
//                    mFloatingActionButton.show();
//                } else if (dy > 0 && mFloatingActionButton.isShown()) {
//                    mFloatingActionButton.hide();
//                }
//            }
//        });

//        mResultsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//
//                int scrollDist = 0;
//                boolean isVisible = true;
//                final float MINIMUM = 25;
//
//                super.onScrolled(recyclerView, dx, dy);
//
//                if (mFloatingActionButton.isShown() && scrollDist > MINIMUM) {
//                    hide();
//                    scrollDist = 0;
//                    isVisible = false;
//                } else if (!mFloatingActionButton.isShown() && scrollDist < -MINIMUM) {
//                    show();
//                    scrollDist = 0;
//                    isVisible = true;
//                }
//
//                if ((isVisible && dy > 0) || (!isVisible && dy < 0)) {
//                    scrollDist += dy;
//                }
//            }
//
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//        });

        updateUI();

        return view;
    }

    public void hide() {
        CoordinatorLayout.LayoutParams layoutParams =
                (CoordinatorLayout.LayoutParams) mFloatingActionButton.getLayoutParams();
        int fabBottomMargin = layoutParams.bottomMargin;
        mFloatingActionButton.animate().translationY(mFloatingActionButton.getHeight() +
        fabBottomMargin).setInterpolator(new AccelerateInterpolator(2)).start();
    }

    public void show() {
        mFloatingActionButton.animate().translationY(0)
                .setInterpolator(new DecelerateInterpolator(2)).start();
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
        if (measurementResults.size() > 0) {
            Collections.sort(measurementResults, new Comparator<MeasurementResult>() {
                @Override
                public int compare(MeasurementResult result1, MeasurementResult result2) {
                    return -result1.getDate().compareTo(result2.getDate());
                }
            });
        }

        if (mAdapter == null) {
            mAdapter = new ResultsAdapter(measurementResults);
            mResultsRecyclerView.setAdapter(mAdapter);
        } else {
//            mAdapter.notifyDataSetChanged();
            mAdapter.notifyItemChanged(mAdapterPosition);
        }

    }

    private class ResultsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mPressureTextView;
        private TextView mPulseTextView;
        private TextView mDateTextView;
        private View mBorderView;
        private ImageView mArrhythmiaImageView;

        private MeasurementResult mResult;

        public ResultsHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_result, parent, false));
            itemView.setOnClickListener(this);

            mPressureTextView = (TextView) itemView.findViewById(R.id.pressure_value);
            mPulseTextView = (TextView) itemView.findViewById(R.id.pulse_value);
            mDateTextView = (TextView) itemView.findViewById(R.id.date);
            mBorderView = itemView.findViewById(R.id.color_border);
            mArrhythmiaImageView = (ImageView) itemView.findViewById(R.id.arrhythmia);
        }

        public void bind(MeasurementResult result) {
            mResult = result;
            String pressureValue = mResult.getSysBloodPressure() + " / " + mResult.getDiaBloodPressure();
            String pulseValue = Integer.toString(mResult.getPulse());
            mPressureTextView.setText(pressureValue);
            mPulseTextView.setText(pulseValue);
            mDateTextView.setText(Helpers.formatFullDate(mResult.getDate()));
            mBorderView.setBackgroundColor(Helpers.pressureToColor(getActivity(), result.getSysBloodPressure()));
            mArrhythmiaImageView.setVisibility(mResult.isArrhythmia() ? View.VISIBLE : View.GONE);
        }

        @Override
        public void onClick(View view) {
            mAdapterPosition = getAdapterPosition();
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
