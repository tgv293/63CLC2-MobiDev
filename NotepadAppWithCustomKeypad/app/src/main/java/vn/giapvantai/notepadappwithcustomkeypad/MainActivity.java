package vn.giapvantai.notepadappwithcustomkeypad;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements KeypadFragment.OnKeyClickListener {

    private NoteFragment noteFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noteFragment = (NoteFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_note);
        KeypadFragment keyboardFragment = (KeypadFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_keypad);

        keyboardFragment.setOnKeyClickListener(this);
    }

    @Override
    public void onKeyClick(String key) {
        noteFragment.appendText(key);
    }
}