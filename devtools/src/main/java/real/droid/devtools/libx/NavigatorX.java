package real.droid.devtools.libx;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NavigatorX {
    interface INavigation {
        <T> void onResult(T result);
    }

    public static class Pair<F, S> {
        public F first;
        public S second;

        public Pair(F first, S second) {
            this.first = first;
            this.second = second;
        }
    }

    private static HashMap<WeakReference<Activity>, NavigatorX> map = new HashMap<>();

    private NavigatorX(WeakReference<Activity> activityRef) {
        this.activityRef = activityRef;
    }

    private WeakReference<Activity> activityRef;
    private int viewId;
    private ArrayList<Pair<Fragment, INavigation>> array = new ArrayList<>();


    public synchronized static NavigatorX on(Activity activity) {
        NavigatorX navigatorX = null;
        for (Map.Entry<WeakReference<Activity>, NavigatorX> entry : map.entrySet()) {
            if (entry.getKey().get() == activity) {
                navigatorX = entry.getValue();
                break;
            }
        }
        if (navigatorX == null) {
            WeakReference<Activity> ref = new WeakReference<>(activity);
            navigatorX = new NavigatorX(ref);

            map.put(ref, navigatorX);
        }

        return navigatorX;
    }

    public NavigatorX container(int resId) {
        if (viewId <= 0) {
            viewId = resId;
        }
        return this;
    }

    public void push(Fragment fragment) {
        this.push(fragment, null);
    }

    public void push(Fragment fragment, INavigation result) {
        Activity activity = activityRef.get();
        if (activity != null) {
            FragmentTransaction transaction = activity.getFragmentManager().
                    beginTransaction();
            boolean isContains = false;
            int index = 0;
            for (Pair<Fragment, INavigation> pair : array) {
                if (pair.first == fragment) {
                    isContains = true;
                    if (result != null) {
                        pair.second = result;
                    }
                    break;
                }
                index++;
            }
            if (!isContains) {
                transaction.add(viewId, fragment);
            }
            ArrayList<Pair<Fragment, INavigation>> temp = new ArrayList<>();
            for (int i = 0; i < array.size(); i++) {
                if (i <= index) {
                    temp.add(array.get(i));
                } else {
                    transaction.remove(array.get(i).first);
                }
            }
            synchronized (this) {
                array = temp;
                if (!isContains) {
                    array.add(new Pair<>(fragment, result));
                }
            }
            transaction.show(fragment).commit();
        }
    }

    public void pop() {
        this.pop(null);
    }

    public <T> void pop(T result) {
        Activity activity = activityRef.get();
        if (activity != null) {
            synchronized (this) {
                if (array.size() > 1) {
                    Pair<Fragment, INavigation> pair = array.get(array.size() - 1);
                    NavigatorX.INavigation navigation = pair.second;
                    if (navigation != null && result != null) {
                        navigation.onResult(result);
                    }
                    activity.getFragmentManager().beginTransaction()
                            .remove(pair.first)
                            .show(array.get(array.size() - 2).first)
                            .commit();
                }
            }
        }
    }

    public synchronized boolean canPop() {
        return array.size() > 1;
    }

    public synchronized void destroy() {
        map.remove(activityRef);
        activityRef.clear();
    }
}
