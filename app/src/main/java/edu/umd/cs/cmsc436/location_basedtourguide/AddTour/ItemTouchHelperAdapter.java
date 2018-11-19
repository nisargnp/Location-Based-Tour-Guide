package edu.umd.cs.cmsc436.location_basedtourguide.AddTour;

public interface ItemTouchHelperAdapter {

    boolean onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}
