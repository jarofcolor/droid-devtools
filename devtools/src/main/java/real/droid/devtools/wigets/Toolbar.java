package real.droid.devtools.wigets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import real.droid.devtools.R;

public class Toolbar extends FrameLayout {
    public interface OnBackListener {
        void onBack();
    }

    private boolean isHideBack;
    private String title;

    private TextView mTitle;
    private ImageView mBack;

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

        mBack.setOnClickListener(v -> {
            if (onBackListener != null) {
                onBackListener.onBack();
            }
        });
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


