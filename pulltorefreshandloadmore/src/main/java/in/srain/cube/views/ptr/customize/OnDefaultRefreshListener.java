package in.srain.cube.views.ptr.customize;

import android.support.v4.view.ViewCompat;
import android.view.View;

import in.srain.cube.views.ptr.PtrFrameLayout;

public abstract class OnDefaultRefreshListener implements OnRefreshListener {

    public static boolean canChildScrollUp(View view) {
        return ViewCompat.canScrollVertically(view,-1);
    }

    /**
     * Default implement for check can perform pull to refresh
     *
     * @param frame
     * @param content
     * @param header
     * @return
     */
    public static boolean checkContentCanBePulledDown(PtrFrameLayout frame, View content, View header) {
        return !canChildScrollUp(content);
    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return checkContentCanBePulledDown(frame, content, header);
    }
}