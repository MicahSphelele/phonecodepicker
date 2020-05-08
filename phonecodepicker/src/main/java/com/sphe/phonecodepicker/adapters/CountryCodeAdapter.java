package com.sphe.phonecodepicker.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.sphe.phonecodepicker.R;
import com.sphe.phonecodepicker.models.Country;
import com.sphe.phonecodepicker.ui.CountryCodePicker;
import com.sphe.phonecodepicker.utils.CountryUtils;

import java.util.List;

public class CountryCodeAdapter extends RecyclerView.Adapter<CountryCodeAdapter.CountryViewHolder>{

    private List<Country> list;
    private CountryCodePicker mCountryCodePicker;
    private OnCountryClickListener listener;

    public CountryCodeAdapter(List<Country> list, CountryCodePicker countryCodePicker) {
        this.list = list;
        this.mCountryCodePicker = countryCodePicker;
    }

    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CountryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_country,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {
        this.setUpCountryItem(list.get(position),holder);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnCountryClickListener(OnCountryClickListener listener){
        this.listener=listener;
    }
    private void setUpCountryItem(final Country country, CountryViewHolder holder){
        if(country==null){

            holder.divider.setVisibility(View.VISIBLE);
            holder.txtCountryName.setVisibility(View.GONE);
            holder.txtCountryCode.setVisibility(View.GONE);
            holder.imageFlag.setVisibility(View.GONE);
        }else{
            holder.divider.setVisibility(View.GONE);
            holder.txtCountryName.setVisibility(View.VISIBLE);
            holder.txtCountryCode.setVisibility(View.VISIBLE);
            holder.imageFlag.setVisibility(View.VISIBLE);
            Context context = holder.itemView.getContext();
            String name = country.getName();
            String iso = country.getIso().toUpperCase();
            String countryNameAndCode;

            if (mCountryCodePicker.isHideNameCode()) {
                countryNameAndCode = name;
            } else {
                countryNameAndCode = context.getString(R.string.country_name_and_code, name, iso);
            }
            holder.txtCountryName.setText(countryNameAndCode);

            if (mCountryCodePicker.isHidePhoneCode()) {
                holder.txtCountryCode.setVisibility(View.GONE);
            } else {
                holder.txtCountryCode.setText(context.getString(R.string.phone_code, country.getPhoneCode()));
            }

            Typeface typeface = mCountryCodePicker.getTypeFace();
            if (typeface != null) {
                holder.txtCountryCode.setTypeface(typeface);
                holder.txtCountryName.setTypeface(typeface);
            }
            holder.imageFlag.setImageResource(CountryUtils.getFlagDrawableResId(country));
            int color = mCountryCodePicker.getDialogTextColor();
            if (color != mCountryCodePicker.getDefaultContentColor()) {
                holder.txtCountryCode.setTextColor(color);
                holder.txtCountryName.setTextColor(color);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onCountryClick(country);
                }
            });
        }

    }

    static class CountryViewHolder extends RecyclerView.ViewHolder{

        AppCompatImageView imageFlag;
        AppCompatTextView txtCountryName;
        AppCompatTextView txtCountryCode;
        View divider;

         CountryViewHolder(@NonNull View v) {
            super(v);
            imageFlag = v.findViewById(R.id.flag_imv);
            txtCountryName = v.findViewById(R.id.country_name_tv);
            txtCountryCode = v.findViewById(R.id.code_tv);
            divider = v.findViewById(R.id.preference_divider_view);
            v.setTag(this);
        }
    }

    public interface OnCountryClickListener{
        void onCountryClick(Country country);
    }
}
