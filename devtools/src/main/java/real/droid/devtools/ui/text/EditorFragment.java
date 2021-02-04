package real.droid.devtools.ui.text;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;

import real.droid.devtools.R;
import real.droid.devtools.base.BaseFragment;
import real.droid.devtools.libx.FileX;
import real.droid.devtools.wigets.Toolbar;
import real.droid.libx.core.NavigatorX;
import real.droid.libx.core.ThreadX;
import real.droid.libx.core.ToastX;

public class EditorFragment extends BaseFragment implements NavigatorX.ICanPop {
    public static String KEY_FILE_PATH = "key_file_path";

    private volatile boolean isFileSaving;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dt_fragment_text_editor, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setOnBackListener(() -> {
            NavigatorX.on(getActivity()).pop();
        });
        Toolbar.MenuItem saveItem = new Toolbar.MenuItem(getResources().getString(R.string.dt_save), getResources().getDrawable(R.mipmap.dt_icon_save));
        toolbar.addOrUpdateMenuItem(saveItem);

        TextView editText = view.findViewById(R.id.edit_text);
        String filePath = getArguments().getString(KEY_FILE_PATH);
        FileX fileX = FileX.on(filePath);
        ThreadX.create().post(() -> {
            String text = fileX.text();
            CharSequence formatText;
            if (filePath.endsWith(".xml")) {
                formatText = new XMLFormatter().format(text);
            } else {
                formatText = text;
            }

            if (!TextUtils.isEmpty(text)) {
                ThreadX.ui().post(() -> {
                    editText.setText(formatText);
                });
            }
        });

        toolbar.setTitle(new File(filePath).getName());

        saveItem.setOnClickListener(v -> {
            if (isFileSaving)
                return;
            isFileSaving = true;
            ThreadX.create().post(() -> {
                fileX.saveText(editText.getText().toString());
                isFileSaving = false;
                ThreadX.ui().post(() -> ToastX.on(getActivity(), R.string.dt_save_success).show());
            });
        });
    }

    @Override
    public boolean canPop() {
        return isFileSaving;
    }
}
