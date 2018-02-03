package io.zirui.nccamera.view.image_gallery;

import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

import com.bumptech.glide.ListPreloader;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.MediaStoreSignature;
import com.bumptech.glide.util.Preconditions;

import java.util.Collections;
import java.util.List;

import io.zirui.nccamera.R;
import io.zirui.nccamera.model.Shot;

public class ImageGalleryAdapter extends RecyclerView.Adapter
        implements ListPreloader.PreloadSizeProvider<Shot>,
        ListPreloader.PreloadModelProvider<Shot>{

    public List<Shot> data;

    private final int screenWidth;
    private final RequestBuilder<Drawable> requestBuilder;
    private int[] actualDimensions;

    private OnClickImageListener onClickImageListener;

    public ImageGalleryAdapter(Context context, List<Shot> data, RequestManager requestManager, OnClickImageListener onClickImageListener){
        this.data = data;
        requestBuilder = requestManager.asDrawable();

        setHasStableIds(true);

        screenWidth = getScreenWidth(context);

        this.onClickImageListener = onClickImageListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shot_card, parent, false);
        view.getLayoutParams().width = screenWidth;

        if (actualDimensions == null){
            view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    if (actualDimensions == null){
                        actualDimensions = new int[] {
                                view.getWidth(), view.getHeight()
                        };
                    }
                    view.getViewTreeObserver().removeOnPreDrawListener(this);
                    return true;
                }
            });
        }
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final Shot shot = data.get(position);
        final ImageViewHolder imageViewHolder = (ImageViewHolder) holder;
        Key signature =
                new MediaStoreSignature(shot.mimeType, shot.dateModified, shot.orientation);

        RequestOptions req = new RequestOptions();
        req.diskCacheStrategy(DiskCacheStrategy.ALL);
        req.signature(signature);
        requestBuilder
                .clone()
                .apply(req)
                .load(shot.uri)
                .thumbnail(0.5f)
                .into(imageViewHolder.imageView);
//        Glide.with(imageViewHolder.imageView.getContext())
//                .load(new File(Uri.parse(shot.path).getPath()))
//                .thumbnail(0.5f)
//                .apply(req)
//                .into(imageViewHolder.imageView);
        imageViewHolder.clickableCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickImageListener.onClick(holder.getAdapterPosition(), shot, data);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).rowId;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @NonNull
    @Override
    public List<Shot> getPreloadItems(int position) {
        return Collections.singletonList(data.get(position));
    }

    @Nullable
    @Override
    public RequestBuilder<?> getPreloadRequestBuilder(@NonNull Shot item) {
        MediaStoreSignature signature =
                new MediaStoreSignature(item.mimeType, item.dateModified, item.orientation);
        RequestOptions req = new RequestOptions();
        req.diskCacheStrategy(DiskCacheStrategy.ALL);
        req.signature(signature);
        return requestBuilder
                .clone()
                .apply(req)
                .load(item.uri)
                .thumbnail(0.5f);
    }

    @Nullable
    @Override
    public int[] getPreloadSize(@NonNull Shot item, int adapterPosition, int perItemPosition) {
        return actualDimensions;
    }

    // Display#getSize(Point)
    @SuppressWarnings("deprecation")
    private static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = Preconditions.checkNotNull(wm).getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    public void prepend(Shot shot){
        this.data.add(0, shot);
        notifyDataSetChanged();
    }

    public void refreshAll(List<Shot> shots){
        this.data.clear();
        this.data = shots;
        notifyDataSetChanged();
    }

    public interface OnClickImageListener{
        void onClick(int position, Shot shot, List<Shot> shots);
    }
}
