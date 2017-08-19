package xyz.cybersapien.recyclerele;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static android.support.v7.widget.RecyclerView.Adapter;
import static android.support.v7.widget.RecyclerView.AdapterDataObserver;
import static android.support.v7.widget.RecyclerView.ViewHolder;

/**
 * Created by cybersapien on 3/7/2017.
 * This is a small wrapper class for the RecyclerView Adapter to make things easier on the RecyclerView
 */

public class RecyclerELEAdapter extends Adapter {

    /* Wrapped Adapter */
    private final Adapter<ViewHolder> wrapped;
    private RecyclerView recyclerView;


    /* Views for different states*/
    private View loadingView;
    private View emptyView;
    private View errorView;

    /* Static values for the views we use in the RecyclerView */
    public static final int VIEW_NORMAL = 100;
    public static final int VIEW_EMPTY = 101;
    public static final int VIEW_LOADING = 102;
    public static final int VIEW_ERROR = 103;

    /**
     * Make sure that the Views are only one of the defined values
     * Don't want the good devs having a mess with wrong code!
     */
    @IntDef({VIEW_NORMAL, VIEW_EMPTY, VIEW_LOADING, VIEW_ERROR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface CurrentSetView {
    }

    /* Internally used values for Different View States */
    public static final int VIEW_TYPE_EMPTY = 201;
    public static final int VIEW_TYPE_LOADING = 202;
    public static final int VIEW_TYPE_ERROR = 203;

    @CurrentSetView
    private int currentView = VIEW_NORMAL;

    private int spanCount = 1;

    public RecyclerELEAdapter(@NonNull Adapter<ViewHolder> wrapped, @Nullable View emptyView, @Nullable View loadingView, @Nullable View errorView) {
        super();
        this.wrapped = wrapped;
        this.emptyView = emptyView;
        this.loadingView = loadingView;
        this.errorView = errorView;

        this.wrapped.registerAdapterDataObserver(new AdapterDataObserver() {
            @Override
            public void onChanged() {
                notifyDataSetChanged();
            }

            @Override
            public void onItemRangeChanged(int positionStart, int itemCount) {
                notifyItemRangeChanged(positionStart, itemCount);
            }

            @Override
            public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
                notifyItemRangeChanged(positionStart, itemCount, payload);
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                notifyItemRangeInserted(positionStart, itemCount);
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                notifyItemRangeRemoved(positionStart, itemCount);
            }

            @Override
            public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                notifyItemMoved(fromPosition, toPosition);
            }
        });
    }

    @CurrentSetView
    public int getCurrentView() {
        return currentView;
    }

    public void setCurrentView(@CurrentSetView int currentView) {
        this.currentView = currentView;
        wrapped.notifyDataSetChanged();

        if (recyclerView != null) {
            RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
            if (manager instanceof GridLayoutManager) {
                switch (currentView) {
                    case VIEW_NORMAL:
                        ((GridLayoutManager) manager).setSpanCount(spanCount);
                        break;
                    default:
                        ((GridLayoutManager) manager).setSpanCount(1);
                }
            }
        }

        notifyDataSetChanged();
    }

    public Adapter<ViewHolder> getWrappedAdapter() {
        return wrapped;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (currentView) {
            case VIEW_EMPTY:
                return new ViewHolder(emptyView) {
                };
            case VIEW_LOADING:
                return new ViewHolder(loadingView) {
                };
            case VIEW_ERROR:
                return new ViewHolder(errorView) {
                };
            case VIEW_NORMAL:
                return wrapped.onCreateViewHolder(parent, viewType);
            default:
                throw new IllegalArgumentException("No View State found matching currentView: " + currentView);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        switch (currentView) {
            case VIEW_NORMAL:
                wrapped.onBindViewHolder(holder, position);
                break;
            case VIEW_EMPTY:
                onBindEmptyViewHolder(holder, position);
                break;
            case VIEW_LOADING:
                onBindLoadingViewHolder(holder, position);
                break;
            case VIEW_ERROR:
                onBindErrorViewHolder(holder, position);
                break;
            default:
                throw new IllegalArgumentException("No View State found matching currentView: " + currentView);
        }
    }

    /**
     * Override this Method to do something when the Empty View is bound
     * This is empty by default
     */
    public void onBindEmptyViewHolder(ViewHolder holder, int position) {
    }

    /**
     * Override this Method to perform actions when the Loading View is bound
     * This is empty by default
     */
    public void onBindLoadingViewHolder(ViewHolder holder, int position) {
    }

    /**
     * Override this Method to perform actions when the Error View is bound
     * This is empty by default
     */
    public void onBindErrorViewHolder(ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        switch (currentView) {
            case VIEW_NORMAL:
                return wrapped.getItemCount();
            case VIEW_LOADING:
            case VIEW_EMPTY:
            case VIEW_ERROR:
                return 1;
            default:
                throw new IllegalArgumentException("No View State found matching currentView: " + currentView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (currentView) {
            case VIEW_NORMAL:
                return wrapped.getItemViewType(position);
            case VIEW_LOADING:
                return VIEW_TYPE_LOADING;
            case VIEW_EMPTY:
                return VIEW_TYPE_EMPTY;
            case VIEW_ERROR:
                return VIEW_TYPE_ERROR;
            default:
                throw new IllegalArgumentException("No View State found matching currentView: " + currentView);
        }
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        wrapped.setHasStableIds(hasStableIds);
    }

    @Override
    public long getItemId(int position) {
        return wrapped.getItemId(position);
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        wrapped.onViewRecycled(holder);
    }

    @Override
    public boolean onFailedToRecycleView(ViewHolder holder) {
        return wrapped.onFailedToRecycleView(holder);
    }

    @Override
    public void onViewAttachedToWindow(ViewHolder holder) {
        wrapped.onViewAttachedToWindow(holder);
    }

    @Override
    public void onViewDetachedFromWindow(ViewHolder holder) {
        wrapped.onViewDetachedFromWindow(holder);
    }

    @Override
    public void registerAdapterDataObserver(AdapterDataObserver observer) {
        wrapped.registerAdapterDataObserver(observer);
    }

    @Override
    public void unregisterAdapterDataObserver(AdapterDataObserver observer) {
        wrapped.unregisterAdapterDataObserver(observer);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        wrapped.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            spanCount = ((GridLayoutManager) manager).getSpanCount();
        }
        this.recyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        wrapped.onDetachedFromRecyclerView(recyclerView);
        this.recyclerView = null;
    }

}
