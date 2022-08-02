package Lucas.integrador;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

public class HomeScreen extends AppCompatActivity {
    Button playGame, quit;
    TextView tQ;
    MediaPlayer player;
    CountDownTimer countDownTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        //o método abaixo irá inicializar as visualizações e a musica.
        player = MediaPlayer.create(HomeScreen.this, R.raw.abertura);
        player.start();
        initViews();

        //Botão para iniciar o jogo, chama o MainGameActivity
        playGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeScreen.this, MainGameActivity.class);
                startActivity(intent);
                player.release();
               //player = MediaPlayer.create(HomeScreen.this, R.raw.boasorte);
               // player.start();
                finish();

            }
        });

        //Botão para sair
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.release();
                countDownTimer = new CountDownTimer(3000, 3000) {
                    @Override
                    public void onTick(long l) {
                        player = MediaPlayer.create(HomeScreen.this, R.raw.vaiparar);
                        player.start();
                    }

                    @Override
                    public void onFinish() {

                        player.release();
                        finish();
                    }
                }.start();
            }
        });
    }

    private void initViews() {
        //inicia as views do botão para iniciar e sair do jogo.
        playGame =(Button) findViewById(R.id.playGame);
        quit = (Button) findViewById(R.id.quit);

        //insere a Fonte.
        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/shablagooital.ttf");
        playGame.setTypeface(typeface);
        quit.setTypeface(typeface);
    }
}

