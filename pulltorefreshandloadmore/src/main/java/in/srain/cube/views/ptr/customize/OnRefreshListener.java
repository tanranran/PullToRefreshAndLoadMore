package in.srain.cube.views.ptr.customize;

import android.view.View;

import in.srain.cube.views.ptr.PtrFrameLayout;

public interface OnRefreshListener {

    /**
     * Check can do refresh or not. For example the content is empty or the first child is in view.
     * <p/>
     * {@link OnDefaultRefreshListener#checkContentCanBePulledDown}
     */
    public boolean checkCanDoRefresh(final PtrFrameLayout frame, final View content, final View header);

    /**
     * When refresh begin
     *
     * @param frame
     */
    public void onRefreshBegin(final PtrFrameLayout frame);
}