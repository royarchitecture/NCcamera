package io.zirui.nccamera.listener;

import java.util.List;
import io.zirui.nccamera.model.Shot;

public interface GalleryItemClickListener {
    void onClick(int position, List<Shot> shots);
}
