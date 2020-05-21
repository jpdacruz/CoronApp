package com.jpdacruz.coronapp.ui.fragments.paises;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.jpdacruz.coronapp.R;
import com.jpdacruz.coronapp.db.clases.CountryEntity;

import java.util.List;

public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.ViewHolder>
                                implements View.OnClickListener{

    private List<CountryEntity> countryEntityList;
    private Context ctx;
    private View.OnClickListener listener;

    public CountriesAdapter(List<CountryEntity> countryEntityList, Context context) {
        this.countryEntityList = countryEntityList;
        this.ctx = context;
    }

    @NonNull
    @Override
    public CountriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_countries,parent, false);

        view.setOnClickListener(this);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountriesAdapter.ViewHolder holder, int position) {

        CountryEntity countryEntity = countryEntityList.get(position);
        holder.mPais.setText(countryEntity.getCountry());
        holder.mTotalDeath.setText(String.format("Fallecidos: %s", countryEntity.getDeaths()));

        /**
         * customizando la imagen que se le pasa al glide
         */
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.override(240,160).transform(new CenterCrop(), new RoundedCorners(16));
        Glide.with(ctx)
                .load(countryEntity
                .getFlag())
                .apply(requestOptions)
                .into(holder.imageViewFlag);
    }

    @Override
    public int getItemCount() {

        if (countryEntityList !=null)

            return countryEntityList.size();

        else return 0;
    }

    /**
     *listener de seleccion de pais
     * @param listener
     */
    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    /**
     * metodo para asignar listado paises a List de paises del adapter
     * notifica los cambios del DataSet
     * @param countries
     */
    public void setData(List<CountryEntity> countries){

        this.countryEntityList = countries;
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {

        if(listener != null){

            listener.onClick(v);
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mPais, mTotalDeath;
        private ImageView imageViewFlag;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            mPais = itemView.findViewById(R.id.textViewPais);
            mTotalDeath = itemView.findViewById(R.id.textViewCountriesConfirmadosNumber);
            imageViewFlag = itemView.findViewById(R.id.imageViewFlag);
        }
    }
}
