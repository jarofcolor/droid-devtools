package real.droid.devtools.libx;

import android.app.Fragment;

import real.droid.libx.core.BundleX;

public class FragmentX {
    public static FragmentX on(Class<? extends Fragment> clazz) throws InstantiationException, IllegalAccessException {
        return new FragmentX(clazz.newInstance());
    }

    public static FragmentX on(Fragment fragment) {
        return new FragmentX(fragment);
    }

    private final Fragment fragment;
    private final BundleX bundleX = new BundleX();

    private FragmentX(Fragment fragment) {
        this.fragment = fragment;
    }

    public FragmentX bundle(BundleX bundleX) {
        this.fragment.setArguments(bundleX.getBundle());
        return this;
    }

    public Fragment fragment() {
        return fragment;
    }
}
