package ieminisham.dcu.redbrick.httpwww.scarab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class linkArrayAdaptor extends ArrayAdapter<String> {
private final Context context;
private final String[] values;

public linkArrayAdaptor(Context context, String[] values) {
        super(context, R.layout.row_layout, values);
        this.context = context;
        this.values = values;
        }

@Override
public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.row_layout, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.rowTextView);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.logo);
        textView.setText(values[position]);

        // Change icon based on name
        String s = values[position];
        s = s.toLowerCase();

        if (s.contains("jpg") || s.contains("png")) {
        imageView.setImageResource(R.drawable.image);
        } else if (s.contains("/wiki")) {
        imageView.setImageResource(R.drawable.webpage);
        } else {
        imageView.setImageResource(R.drawable.unknown);
        }

        return rowView;
        }
        }
