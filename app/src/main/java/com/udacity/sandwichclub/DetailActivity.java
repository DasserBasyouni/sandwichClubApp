package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @BindView(R.id.origin_tv) TextView origin_tv;
    @BindView(R.id.description_tv) TextView description_tv;
    @BindView(R.id.ingredients_tv) TextView ingredients_tv;
    @BindView(R.id.also_known_tv) TextView also_known_tv;
    @BindView(R.id.image_iv) ImageView ingredientsIv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = 0;
        if (intent != null) {
            position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        }

        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.get()
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        String noDetails = getResources().getString(R.string.no_available_details);
        String placeOfOrigin = sandwich.getPlaceOfOrigin();
        String description = sandwich.getDescription();
        String ingredients = getSensibleText(sandwich.getIngredients());
        String alsoKnownAs = getSensibleText(sandwich.getAlsoKnownAs());

        if(placeOfOrigin.isEmpty()) origin_tv.setText(noDetails);
        else origin_tv.setText(placeOfOrigin);

        if(description.isEmpty()) description_tv.setText(noDetails);
        else description_tv.setText(description);

        if(ingredients.isEmpty()) ingredients_tv.setText(noDetails);
        else ingredients_tv.setText(ingredients);

        if(alsoKnownAs.isEmpty()) also_known_tv.setText(noDetails);
        else also_known_tv.setText(alsoKnownAs);
    }

    private String getSensibleText(List<String> ingredients) {
        StringBuilder sb = new StringBuilder();
        for (String s : ingredients)
        {
            sb.append(s);
            sb.append(", ");
        }
        if(sb.length() > 2){
            sb.setLength(sb.length()-2);
            return sb.toString();
        }else {
            return getResources().getString(R.string.no_available_details);
        }
    }
}
