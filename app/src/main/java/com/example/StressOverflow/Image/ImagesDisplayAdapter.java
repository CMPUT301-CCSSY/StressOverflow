package com.example.StressOverflow.Image;

import static androidx.appcompat.content.res.AppCompatResources.getDrawable;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.StressOverflow.R;

import java.util.ArrayList;

import com.example.StressOverflow.Util;
import com.squareup.picasso.Picasso;

/**
 * Creates grid which displays an item's images
 */
public class ImagesDisplayAdapter extends ArrayAdapter<Image> {
    private final Context context;
    private ArrayList<Image> images;

    /**
     * Display passed Image objects in a grid
     *
     * @param context to display grid in
     * @param images to display in grid
     */
    public ImagesDisplayAdapter(Context context, ArrayList<Image> images) {
        super(context, 0, images);
        this.images = images;
        this.context = context;
    }

    /**
     * Get view at position and display the image
     *
     * @param position The position of the item within the adapter's data set of the item whose view
     *        we want
     * @param convertView The old view to reuse, if possible
     * @param parent The parent that this view will eventually be attached to
     * @return
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.image_in_grid, parent, false);
        }

        Image image = images.get(position);
        ImageView imageView = view.findViewById(R.id.image);
//        image.displayImage(imageView);

        if (image.getURL() == null) {
            if (image.getBitmap() != null) {
                imageView.setImageBitmap(image.getBitmap());
            } else {
                Util.showShortToast(context, "Error: No URL or Bitmap found");
            }
        } else {
            String url = new String(image.getURL());
            try {
                Picasso.get()
                        .load(url)
                        .error(R.drawable.ic_error_image)
                        .into(imageView);
            } catch (Exception e) {
                Log.d("IMAGES", "Unexpected error displaying URL.", e);
            }
        }

        return view;
    }
}

