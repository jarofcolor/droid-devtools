package real.droid.devtools.wigets;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import real.droid.devtools.R;

public class Toolbar extends FrameLayout {
    public static class MenuItem {
        private String text;
        private Drawable drawable;

        public MenuItem(String text, Drawable drawable) {
            this.text = text;
            this.drawable = drawable;
        }

        public void setDrawable(Drawable drawable) {
            this.drawable = drawable;
            update();
        }

        public void setText(String text) {
            this.text = text;
            update();
        }

        private TextView textView;
        private Toolbar toolbar;

        void update() {

            Resources resources = toolbar.getContext().getResources();
            if (textView == null) {
                textView = new TextView(toolbar.getContext());
                textView.setOnClickListener(v -> {
                    if (onClickListener != null)
                        onClickListener.onClick(v);
                });
                textView.setOnLongClickListener(v -> {
                    if (onLongClickListener != null)
                        return onLongClickListener.onLongClick(v);
                    return false;
                });
                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,resources.getDimension(R.dimen.dt_sp_10));
            }
            textView.setText(text);
            int size = (int) resources.getDimension(R.dimen.dt_dp_20);
            drawable.setBounds(0, 0, size, size);
            textView.setCompoundDrawables(null, drawable, null, null);
        }

        public void setOnClickListener(OnClickListener onClickListener) {
            this.onClickListener = onClickListener;
        }

        public void setOnLongClickListener(OnLongClickListener onLongClickListener) {
            this.onLongClickListener = onLongClickListener;
        }

        private View.OnClickListener onClickListener;
        private View.OnLongClickListener onLongClickListener;
    }

    public interface OnBackListener {
        void onBack();
    }

    private boolean isHideBack;
    private String title;

    private TextView mTitle;
    private ImageView mBack;

    private ArrayList<MenuItem> menuItems = new ArrayList<>();
    private LinearLayout menuView;

    private OnBackListener onBackListener;

    public Toolbar(Context context) {
        this(context, null);
    }

    public Toolbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Toolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.Toolbar);
        title = a.getString(R.styleable.Toolbar_title);
        isHideBack = a.getBoolean(R.styleable.Toolbar_hideBack, false);
        a.recycle();

        initView(context);

        setTitle(title);
        setHideBack(isHideBack);
    }

    private void initView(Context context) {
        View view = View.inflate(context, R.layout.dt_toolbar, null);
        mTitle = view.findViewById(R.id.tv_toolbar_title);
        mBack = view.findViewById(R.id.iv_toolbar_back);
        addView(view);

        menuView = view.findViewById(R.id.ll_right_menu);
        mBack.setOnClickListener(v -> {
            if (onBackListener != null) {
                onBackListener.onBack();
            }
        });
    }

    public void addOrUpdateMenuItem(MenuItem item) {
        if (item == null)
            return;
        item.toolbar = this;
        item.update();
        if (menuItems.contains(item)) {
            return;
        }
        menuItems.add(item);
        menuView.addView(item.textView);
    }

    public void removeItem(MenuItem item) {
        if (item == null || item.textView == null)
            return;
        View view = (View) item.textView.getParent();
        if (view != null && view == menuView) {
            menuView.removeView(item.textView);
            menuItems.remove(item);
        }
    }

    public void setTitle(String title) {
        this.title = title;
        mTitle.setText(title);
    }

    public void setHideBack(boolean hideBack) {
        isHideBack = hideBack;
        mBack.setVisibility(hideBack ? View.GONE : View.VISIBLE);
    }

    public String getTitle() {
        return title;
    }

    public boolean isHideBack() {
        return isHideBack;
    }

    public void setOnBackListener(OnBackListener onBackListener) {
        this.onBackListener = onBackListener;
    }
}


