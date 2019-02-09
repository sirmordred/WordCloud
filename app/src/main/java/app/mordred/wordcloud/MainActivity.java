package app.mordred.wordcloud;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.mordred.wordcloud.WordCloud;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imgView = findViewById(R.id.imageView);

        Map<String, Integer> nMap = new HashMap<>();

        nMap.put("oguzhan", 2);
        nMap.put("mordred", 2);
        nMap.put("is", 4);
        nMap.put("on",2);
        nMap.put("the", 3);
        nMap.put("salda lake",5);

        WordCloud wd = new WordCloud(nMap, 250, 250,Color.BLACK,Color.WHITE);
        wd.setWordColorOpacityAuto(true);
        wd.setPaddingX(20);
        wd.setPaddingY(20);

        Bitmap generatedWordCloudBmp = wd.generate();

        imgView.setImageBitmap(generatedWordCloudBmp);

    }
}
