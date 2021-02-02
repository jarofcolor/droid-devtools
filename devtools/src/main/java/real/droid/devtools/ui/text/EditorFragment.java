package real.droid.devtools.ui.text;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

import real.droid.devtools.R;
import real.droid.devtools.base.BaseFragment;
import real.droid.devtools.utils.FileUtil;
import real.droid.devtools.wigets.Toolbar;
import real.droid.libx.core.NavigatorX;
import real.droid.libx.core.ThreadX;

public class EditorFragment extends BaseFragment {
    public static String KEY_FILE_PATH = "key_file_path";

    private EditorAdapter adapter;


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
        ListView listView = view.findViewById(R.id.list_view);
        listView.setAdapter(adapter = new EditorAdapter(this));
        String filePath = getArguments().getString(KEY_FILE_PATH);
        ThreadX.create().post(() -> {
            ArrayList<String> lines = FileUtil.readLines(filePath);
            if (lines.size() > 0) {
                ThreadX.ui().post(() -> {
                    adapter.addLines(lines);
                });
            }
        });

        toolbar.setTitle(new File(filePath).getName());
    }
}
