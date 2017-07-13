package com.example.k_ikemura.sharesns;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * シェア/Line 押下
     *
     * @param view
     */
    public void onLineClick(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String message = Uri.encode("シェアしたい内容");
        intent.setData(Uri.parse("line://msg/text/" + message));
        try {
            startActivity(intent);
        } catch (Exception e) {
            Log.e("MainActivity", e.getMessage());
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * シェア/Twitter 押下
     *
     * @param view
     */
    public void onTwitterClick(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String message = Uri.encode("シェアしたい内容");
        intent.setData(Uri.parse("twitter://post?message=" + message));
        try {
            startActivity(intent);
        } catch (Exception e) {
            Log.e("MainActivity", e.getMessage());
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * シェア/Facebook 押下
     *
     * @param view Facebookボタン
     */
    public void onFaceBookClick(View view) {
        String contents = "https://www.google.com";
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT, contents);
        share.setPackage("com.facebook.katana");
        startActivity(Intent.createChooser(share, "Pick App"));
    }


    // FacebookページID
    String FACEBOOK_PAGE_ID = "GoogleJapan";

    // URL
    String FACEBOOK_URL = "https://www.facebook.com/" + FACEBOOK_PAGE_ID;

    /**
     * Facebookページ　押下
     *
     * @param view
     */
    public void onFaceBookPageClick(View view) {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getFacebookPageUrl())));
        } catch (Exception e) {
            // Facebookアプリがインストールされていない場合は、ブラウザで開く
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(FACEBOOK_URL)));
        }
    }

    /**
     * FacebookページURLを作成して返す
     *
     * @return facebook page url
     */
    private String getFacebookPageUrl() {

        try {
            // Facebookアプリのバージョン取得
            int versionCode = getPackageManager().getPackageInfo("com.facebook.katana", 0).versionCode;

            if (versionCode >= 3002850) {
                // Facebook アプリのバージョン 11.0.0.11.23 (3002850) 以上の場合
                return "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            } else {
                // Facebook アプリが古い場合
                return "fb://page/" + FACEBOOK_PAGE_ID;
            }
        } catch (PackageManager.NameNotFoundException e) {
            // ページURL
            return FACEBOOK_URL;
        }
    }
}
