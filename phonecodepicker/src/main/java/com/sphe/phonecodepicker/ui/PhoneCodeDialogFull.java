package com.sphe.phonecodepicker.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.sphe.phonecodepicker.R;
import com.sphe.phonecodepicker.adapters.CountryCodeAdapter;
import com.sphe.phonecodepicker.models.Country;

import java.util.ArrayList;
import java.util.List;

public class PhoneCodeDialogFull extends BottomSheetDialogFragment implements CountryCodeAdapter.OnCountryClickListener{

    private static final String TAG = "@PhoneCodeDialog";
    private PhoneCodePicker mPhoneCodePicker;
    private AppCompatEditText mEdtSearch;
    private AppCompatTextView mTvNoResult;
    private AppCompatTextView mTvTitle;
    private RecyclerView recyclerView;
    private RelativeLayout mRlyDialog;

    private List<Country> masterCountries;
    private List<Country> mFilteredCountries;
    private InputMethodManager mInputMethodManager;
    private CountryCodeAdapter mCountryCodeAdapter;
    private List<Country> mTempCountries;

    PhoneCodeDialogFull(PhoneCodePicker phoneCodePicker){
        this.mPhoneCodePicker=phoneCodePicker;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) dialogInterface;
                setupFullHeight(bottomSheetDialog);
            }
        });
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_layout_picker, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpUI(view);
        setUpData();
    }

    @Override
    public void onCountryClick(Country country) {
        mPhoneCodePicker.setSelectedCountry(country);
        mInputMethodManager.hideSoftInputFromWindow(mEdtSearch.getWindowToken(), 0);
        this.dismiss();
    }

    @Override
    public void onStart() {
        super.onStart();
//        int width = ViewGroup.LayoutParams.MATCH_PARENT;
//        int height = ViewGroup.LayoutParams.MATCH_PARENT;
//        if(getDialog()!=null){
//            if(getDialog().getWindow()!=null){
//                getDialog().getWindow().setLayout(width,height);
//            }
//        }
        performOSThemeCheck();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        if(getDialog()!=null){
//            if(getDialog().getWindow()!=null)
//            getDialog().getWindow().getAttributes().windowAnimations = R.style.FullScreenDialogStyle;
//        }
    }

    private void setUpUI(View v) {
        mRlyDialog = v.findViewById(R.id.dialog_rly);
        recyclerView = v.findViewById(R.id.country_dialog_lv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mTvTitle = v.findViewById(R.id.title_tv);
        mEdtSearch = v.findViewById(R.id.search_edt);
        mTvNoResult = v.findViewById(R.id.no_result_tv);
    }

    private void setUpData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            recyclerView.setLayoutDirection(mPhoneCodePicker.getLayoutDirection());
        }

        if (mPhoneCodePicker.getTypeFace() != null) {
            Typeface typeface = mPhoneCodePicker.getTypeFace();
            mTvTitle.setTypeface(typeface);
            mEdtSearch.setTypeface(typeface);
            mTvNoResult.setTypeface(typeface);
        }

        if (mPhoneCodePicker.getBackgroundColor() != mPhoneCodePicker.getDefaultBackgroundColor()) {
            mRlyDialog.setBackgroundColor(mPhoneCodePicker.getBackgroundColor());
        }

        if (mPhoneCodePicker.getDialogTextColor() != mPhoneCodePicker.getDefaultContentColor()) {
            int color = mPhoneCodePicker.getDialogTextColor();
            mTvTitle.setTextColor(color);
            mTvNoResult.setTextColor(color);
            mEdtSearch.setTextColor(color);
            mEdtSearch.setHintTextColor(adjustAlpha(color, 0.7f));
        }

        mPhoneCodePicker.refreshCustomMasterList();
        mPhoneCodePicker.refreshPreferredCountries();
        masterCountries = mPhoneCodePicker.getCustomCountries(mPhoneCodePicker);

        mFilteredCountries = getFilteredCountries();
        setUpListViewAdapter(recyclerView);

        mInputMethodManager = (InputMethodManager) mPhoneCodePicker.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        setSearchBar();
    }

    private void setUpListViewAdapter(RecyclerView recyclerView) {
        mCountryCodeAdapter = new CountryCodeAdapter(mFilteredCountries,mPhoneCodePicker);
        if (!mPhoneCodePicker.isSelectionDialogShowSearch()) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) recyclerView.getLayoutParams();
            params.height = ListView.LayoutParams.WRAP_CONTENT;
            recyclerView.setLayoutParams(params);
        }
        mCountryCodeAdapter.setOnCountryClickListener(this);
        recyclerView.setAdapter(mCountryCodeAdapter);
    }

    @SuppressWarnings("SameParameterValue")
    private int adjustAlpha(int color, float factor) {
        int alpha = Math.round(Color.alpha(color) * factor);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }

    private void setSearchBar() {
        if (mPhoneCodePicker.isSelectionDialogShowSearch()) {
            setUpTextWatcher();
        } else {
            mEdtSearch.setVisibility(View.GONE);
        }
    }

    private void performOSThemeCheck(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            if(mPhoneCodePicker.isSupportOSTheme()){
                if(mPhoneCodePicker.isOsThemeDark()){
                    mRlyDialog.setBackgroundColor(mPhoneCodePicker.getResources().getColor(R.color.colorPickerDarkModeBackground,null));
                    mTvTitle.setTextColor(mPhoneCodePicker.getResources().getColor(R.color.colorWhite,null));
                    mEdtSearch.setHintTextColor(mPhoneCodePicker.getResources().getColor(R.color.colorWhite,null));
                    mEdtSearch.setBackgroundResource(R.drawable.edit_txt_border_white);
                    mEdtSearch.setTextColor(mPhoneCodePicker.getResources().getColor(R.color.colorWhite,null));
                }else{
                    Log.d(TAG,"OS NOT IN DARK MODE");
                }
            }else{
                Log.d(TAG,"OS DARK MODE CHECK NOT SUPPORT");
            }
        }
    }

    private List<Country> getFilteredCountries() {
        return getFilteredCountries("");
    }

    private List<Country> getFilteredCountries(String query) {
        if (mTempCountries == null) {
            mTempCountries = new ArrayList<>();
        } else {
            mTempCountries.clear();
        }

        List<Country> preferredCountries = mPhoneCodePicker.getPreferredCountries();
        if (preferredCountries != null && preferredCountries.size() > 0) {
            for (Country country : preferredCountries) {
                if (country.isEligibleForQuery(query)) {
                    mTempCountries.add(country);
                }
            }

            if (mTempCountries.size() > 0) { //means at least one preferred country is added.
                mTempCountries.add(null); // this will add separator for preference countries.
            }
        }

        for (Country country : masterCountries) {
            if (country.isEligibleForQuery(query)) {
                mTempCountries.add(country);
            }
        }
        return mTempCountries;
    }

    /**
     * Filter country list for given keyWord / query.
     * Lists all countries that contains @param query in country's name, name code or phone code.
     *
     * @param query : text to match against country name, name code or phone code
     */
    private void applySearchQuery(String query) {
        mTvNoResult.setVisibility(View.GONE);
        query = query.toLowerCase();

        if (query.length() > 0 && query.charAt(0) == '+') {
            query = query.substring(1);
        }

        mFilteredCountries = getFilteredCountries(query);

        if (mFilteredCountries.size() == 0) {
            mTvNoResult.setVisibility(View.VISIBLE);
        }
        mCountryCodeAdapter.notifyDataSetChanged();
    }

    /**
     * add textChangeListener, to apply new query each time editText get text changed.
     */
    private void setUpTextWatcher() {
        if (mEdtSearch == null) return;

        mEdtSearch.addTextChangedListener(new TextWatcher() {

            @Override public void afterTextChanged(Editable s) {
            }

            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                applySearchQuery(s.toString());
            }
        });

        if (mPhoneCodePicker.isKeyboardAutoPopOnSearch()) {
            if (mInputMethodManager != null) {
                mInputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            }
        }
    }

    private void setupFullHeight(BottomSheetDialog bottomSheetDialog) {
        FrameLayout bottomSheet = (FrameLayout) bottomSheetDialog.findViewById(R.id.design_bottom_sheet);
        if(bottomSheet!=null){
            BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
            ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();

            int windowHeight = getWindowHeight();
            if (layoutParams != null) {
                layoutParams.height = windowHeight;
            }
            bottomSheet.setLayoutParams(layoutParams);
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }

    private int getWindowHeight() {
        // Calculate window height for fullscreen use
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if((getContext())!=null){
            ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        }

        return displayMetrics.heightPixels;
    }
}
