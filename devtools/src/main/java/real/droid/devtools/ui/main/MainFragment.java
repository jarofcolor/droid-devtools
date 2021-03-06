package real.droid.devtools.ui.main;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;

import real.droid.devtools.R;
import real.droid.devtools.base.BaseFragment;
import real.droid.devtools.ui.dir.DirListFragment;
import real.droid.libx.core.FragmentX;
import real.droid.libx.core.NavigatorX;

public class MainFragment extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dt_fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        NavigatorX navigatorX = NavigatorX.on(getActivity());
        getView().findViewById(R.id.tv_file_look).setOnClickListener(v -> {
            //data dir
            String internalDataPath = getActivity().getFilesDir().getParentFile().getAbsolutePath();
            String externalDataPath = getActivity().getExternalFilesDir(null).getParentFile().getAbsolutePath();

            navigatorX.push(FragmentX.on(new DirListFragment()).
                    addArg(DirListFragment.KEY_TITLES, "内部目录;外部目录").
                    addArg(DirListFragment.KEY_DIRS, internalDataPath + ";" + externalDataPath)
                    .fragment());
        });

        getView().findViewById(R.id.tv_sp_look).setOnClickListener(v -> {
            //data dir
            String internalDataPath = getActivity().getFilesDir().getParentFile().getAbsolutePath();

            navigatorX.push(FragmentX.on(new DirListFragment()).
                    addArg(DirListFragment.KEY_TOOL_BAR_TITLE, getResources().getString(R.string.dt_sp_look)).
                    addArg(DirListFragment.KEY_TITLES, "shared_prefs").
                    addArg(DirListFragment.KEY_DIRS, new File(internalDataPath, "shared_prefs").getAbsolutePath())
                    .fragment());
        });
    }
}
