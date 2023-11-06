package vn.giapvantai.notepadappwithcustomkeypad;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import java.util.*;

public class KeypadFragment extends Fragment {

    private OnKeyClickListener listener;
    private Map<Integer, Integer> keyState = new HashMap<>();
    private Map<Integer, String> keyMapping = new HashMap<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_keypad, container, false);

        // Assuming you have a button for each key on your custom keyboard
        int[] buttonIds = {
                R.id.key_1, R.id.key_2, R.id.key_3,
                R.id.key_4, R.id.key_5, R.id.key_6,
                R.id.key_7, R.id.key_8, R.id.key_9,
                R.id.key_star, R.id.key_0, R.id.key_hash
        };

        // Map each button to a string of characters
        keyMapping.put(R.id.key_1, "1");
        keyMapping.put(R.id.key_2, "abc2");
        keyMapping.put(R.id.key_3, "def3");
        keyMapping.put(R.id.key_4, "ghi4");
        keyMapping.put(R.id.key_5, "jkl5");
        keyMapping.put(R.id.key_6, "mno6");
        keyMapping.put(R.id.key_7, "pqrs7");
        keyMapping.put(R.id.key_8, "tuv8");
        keyMapping.put(R.id.key_9, "wxyz9");
        keyMapping.put(R.id.key_star, "*");
        keyMapping.put(R.id.key_hash, "#");
        keyMapping.put(R.id.key_0, " 0");

        for (int id : buttonIds) {
            Button keyButton = view.findViewById(id);
            keyButton.setOnClickListener(v -> {
                if (listener != null) {
                    int buttonId = v.getId();
                    int state = keyState.getOrDefault(buttonId, 0);
                    String chars = keyMapping.get(buttonId);
                    String text = chars.charAt(state % chars.length()) + "";
                    keyState.put(buttonId, state + 1);
                    listener.onKeyClick(text);
                }
            });
        }

        return view;
    }

    public void setOnKeyClickListener(OnKeyClickListener listener) {
        this.listener = listener;
    }

    public interface OnKeyClickListener {
        void onKeyClick(String key);
    }
}
