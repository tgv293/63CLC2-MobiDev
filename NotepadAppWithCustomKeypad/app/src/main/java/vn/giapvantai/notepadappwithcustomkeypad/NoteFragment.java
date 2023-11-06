package vn.giapvantai.notepadappwithcustomkeypad;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class NoteFragment extends Fragment {

    private TextView noteTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, container, false);

        noteTextView = view.findViewById(R.id.note_text_view);

        return view;
    }

    public void appendText(String text) {
        noteTextView.append(text);
    }
}
