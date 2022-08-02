package Lucas.integrador;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import Lucas.integrador.MainGameActivity;

public class GameWon extends Activity {
    MediaPlayer player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_won);
    }

    // Este é um esperando click no botão
    // navegará desta para MainGameActivity
    public void PlayAgain(View view) {
        Intent intent = new Intent(GameWon.this, MainGameActivity.class);
        startActivity(intent);
         finish();
    }
}
