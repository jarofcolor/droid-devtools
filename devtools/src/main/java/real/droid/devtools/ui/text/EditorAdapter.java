package real.droid.devtools.ui.text;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

import java.util.ArrayList;

public class EditorAdapter extends BaseAdapter {

    private EditorFragment editorFragment;
    private ArrayList<String> lines = new ArrayList<>();


    public EditorAdapter(EditorFragment editorFragment) {
        this.editorFragment = editorFragment;
    }

    public void addLine(String line) {
        lines.add(line);
        notifyDataSetChanged();
    }

    public void addLines(ArrayList<String> lines) {
        this.lines.addAll(lines);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return lines.size();
    }

    @Override
    public CharSequence getItem(int position) {
        return lines.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        EditText editText;
        if (convertView != null) {
            editText = (EditText) convertView;
        } else {
            editText = new EditText(editorFragment.getActivity());
            editText.setBackgroundColor(Color.TRANSPARENT);
            editText.setGravity(Gravity.CENTER_VERTICAL);
        }
        editText.setText(getItem(position));
        return editText;
    }
}
